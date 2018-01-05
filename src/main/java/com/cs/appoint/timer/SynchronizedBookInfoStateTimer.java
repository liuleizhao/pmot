package com.cs.appoint.timer;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.cs.appoint.entity.BookInfo;
import com.cs.appoint.service.BookInfoService;
import com.cs.argument.entity.Station;
import com.cs.argument.service.StationService;
import com.cs.common.constant.Constants;
import com.cs.common.entityenum.CheckState;
import com.cs.mvc.dao.SqlCondition;
import com.cs.mvc.init.InitData;
import com.cs2.veh.entity.IVehIsFlow;
import com.cs2.veh.service.IVehIsFlowService;

/**
 * 同步预约状态定时器
 * @author Administrator
 *
 */
public class SynchronizedBookInfoStateTimer {
	@Autowired
	private BookInfoService bookInfoService;
	@Autowired
	private IVehIsFlowService ivehIsFlowService;
	@Autowired
	private StationService stationService;
	
	public void download(){
		//1、查询预约状态为完成，并且检测状态 【不是 】已退办、已办结 的预约记录
		List<CheckState> list = new ArrayList<CheckState>();
		list.add(CheckState.YWBJ);
		list.add(CheckState.YWTB);
		List<BookInfo> bookInfos = null;
		try {
			bookInfos = bookInfoService.findCheckStateNotFinished();
			//2、循环遍历，根据车架号从监管平台查询最新的流水信息(流水的创建日期为预约日期)
			if(CollectionUtils.isNotEmpty(bookInfos)){
				for(BookInfo bookInfo : bookInfos){
					SqlCondition sqlCondition =  new SqlCondition();
					if("1".equals(bookInfo.getNewflag())){//新车预约时输入完整车架号
						sqlCondition.addSingleNotNullCriterion("clsbdh = ", bookInfo.getFrameNumber());
					}else{//如果不是新车，使用号牌号码和号牌种类进行查询
						String platNum = bookInfo.getPlatNumber();
						if(platNum != null && platNum.length()>0){
							sqlCondition.addSingleNotNullCriterion("hphm = ", platNum.substring(1));
							sqlCondition.addSingleCriterion("hpzl = ", InitData.getGlobalValue(Constants.CAR_TYPE_ID_PREFIX+bookInfo.getCarTypeId()));
						}
						sqlCondition.addSingleNotNullCriterion("clsbdh LIKE ", "%"+bookInfo.getFrameNumber());
					}
					sqlCondition.addSingleNotNullCriterion("to_char(cjsj,'yyyy-mm-dd') = ", bookInfo.getBookDate());
					sqlCondition.addDescOrderbyColumn("cjsj");
					List<IVehIsFlow> vehIsFlows = ivehIsFlowService.findByCondition(sqlCondition);
					if(CollectionUtils.isNotEmpty(vehIsFlows)){//流水不为空
						IVehIsFlow flow = vehIsFlows.get(0);
						//3、更新预约记录中的检测状态等信息
						bookInfo.setCheckTime(flow.getCjsj());
						bookInfo.setCheckSerialNumber(flow.getLsh());
						Integer operationMark = null;
						if(flow.getJyshbj() != null){
							operationMark = Integer.valueOf(flow.getJyshbj());
						}
						bookInfo.setCheckOperationMark(operationMark);
						CheckState checkState = null;
						if(flow.getLszt() != null){
							checkState = CheckState.findByIndex(flow.getLszt());
						}
						bookInfo.setCheckState(checkState);
						Station station = stationService.findByCode(flow.getJyjgbh());
						bookInfo.setCheckStation(station!=null ? station.getName() : flow.getJyjgbh());
						bookInfoService.updateByPrimaryKeySelective(bookInfo);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
