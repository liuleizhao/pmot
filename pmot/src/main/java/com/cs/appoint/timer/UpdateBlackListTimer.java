package com.cs.appoint.timer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cs.appoint.entity.BookInfo;
import com.cs.appoint.service.BookInfoService;
import com.cs.argument.entity.BlackListRecord;
import com.cs.argument.entity.BreakPromiseRecord;
import com.cs.argument.entity.BusBlackListConfig;
import com.cs.argument.service.BlackListRecordService;
import com.cs.argument.service.BreakPromiseRecordService;
import com.cs.argument.service.BusBlackListConfigService;
import com.cs.common.entityenum.BlacklistState;
import com.cs.common.entityenum.BookOperation;
import com.cs.common.entityenum.BookState;
import com.cs.common.utils.DateUtil;


/**
 * 处理黑名单定时器
 * @author HYL
 *
 */
@Component(value = "updateBlackListTimer") 
public class UpdateBlackListTimer {
	
	@Autowired
	private BusBlackListConfigService blackListConfigService;
	
	@Autowired
	private BookInfoService bookInfoService;
	
	@Autowired
	private BreakPromiseRecordService breakPromiseRecordService;
	
	@Autowired
	private BlackListRecordService blackListRecordService;
	 
	
	public void updateBlackList(){
		//从bookInfo表转失约表
		bookInfoToBreakPromise();
		System.out.println("BookInfo表失约————》失约表...");
		//从失约表转黑名单表
		//breakPromiseToBlackList(BookOperation.CANCEL);
		breakPromiseToBlackList(BookOperation.BREAK_PROMISE);
		System.out.println("失约————》黑名单表...");
		//根据设置的有效时间更新黑名单表状态
		updateBlackListStates();
		System.out.println("更新黑名单表状态...");
	}

	/**
	 * 查看bookInfo 失约，有直接插入失约表
	 * 只查看状态为预约中的
	 */
	public void bookInfoToBreakPromise(){
		try {
			List<BookInfo> bookInfos = bookInfoService.findByBreakPromiseAndBookDate(DateUtil.getDateBefore(1)); //查找预约日期为一天前的
			if(CollectionUtils.isNotEmpty(bookInfos)){
				for(BookInfo bookInfo: bookInfos){
					breakPromiseRecordService.addBreakPromise(bookInfo, BookOperation.BREAK_PROMISE);
					//更新bookInfo表
					bookInfo.setBookState(BookState.SY);
					bookInfoService.updateByPrimaryKey(bookInfo);
				}
			}
		} catch (Exception e) {
			System.out.println("定时器，预约表至失约表插入失败");
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 查找黑名单配置表，是否存在配置
	 * 在失约表找，查询是否存在超过配置的，有直接拉入黑名单表
	 * 已经拉入黑名单的数据，更新状态，防止多次查到
	 */
	public void breakPromiseToBlackList(BookOperation bookOperation){
		try {
			List<BusBlackListConfig> cancelConfigs = blackListConfigService.findByBookOperation(bookOperation);
			if(CollectionUtils.isNotEmpty(cancelConfigs)){
				for(BusBlackListConfig config : cancelConfigs){
					//配置的最大次数
					Integer limitCount = config.getLimitCount();
					Integer effectDays = config.getEffectDays();
					
					//所有符合配置的失约/取消记录
					List<BreakPromiseRecord> records = breakPromiseRecordService.findBybookOperationAndRecordType(bookOperation,config.getRecordType());
					if(CollectionUtils.isNotEmpty(records)){
						for (BreakPromiseRecord record: records) {
							String recordValue = record.getRecordValue(); //记录的值
							//出现记录值的记录
							List<BreakPromiseRecord> valueRecords = breakPromiseRecordService.findByRecordValue(recordValue); //该记录出现的次数
							//判断取消次数是否大于配置次数
							if(CollectionUtils.isNotEmpty(valueRecords) && valueRecords.size() >= limitCount){
								//写入黑名单表
								blackListRecordService.addFromBreakPromise(effectDays,valueRecords);
								//设置失约表中的状态为失效
								for (BreakPromiseRecord breakPromiseRecord : valueRecords) {
									breakPromiseRecordService.updateState(breakPromiseRecord);
								}
								
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("定时器，失约表转黑名单表失败");
		}
		
	}
	
	
	public void updateBlackListStates(){
		try {
			List<BlackListRecord> blackListRecords = blackListRecordService.findByState(BlacklistState.SX);
			if(CollectionUtils.isNotEmpty(blackListRecords)){
				for (BlackListRecord blackListRecord : blackListRecords) {
					if(blackListRecord.getEndDate() != null){
						blackListRecord.setStatus(BlacklistState.CX);
						blackListRecordService.updateByPrimaryKey(blackListRecord);
					}
				}
			}
		} catch (Exception e) {
			System.out.println("定时器，更新黑名单状态出错");
			e.printStackTrace();
		}
		
	}
	
}
