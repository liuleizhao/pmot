package com.cs.appoint.timer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.cs.appoint.dao.BookInfoDao;
import com.cs.appoint.dao.VehIsInfDao;
import com.cs.appoint.entity.BookInfo;
import com.cs.appoint.entity.VehIsInfo;
import com.cs.appoint.service.VehIsInfoService;
import com.cs.argument.dao.CarTypeDao;
import com.cs.argument.dao.StationDao;
import com.cs.argument.entity.CarType;
import com.cs.argument.entity.Station;
import com.cs.common.constant.Constants;
import com.cs.common.entityenum.InterfaceInvokeType;
import com.cs.common.utils.ConvertUtils;
import com.cs.common.utils.DateUtils;
import com.cs.common.utils.RedisUtil;
import com.cs.mvc.init.InitData;

public class DownloadVehIsInfoTimer {
	private static long interval = 0L;
	private static final int errorThreshold = 3;//错误阈值（车辆信息错误次数超过该值则认定为错误信息）
	
	@Autowired
	private CarTypeDao carTypeDao;
	@Autowired
	private StationDao stationDao;
	@Autowired
	private VehIsInfDao vehIsInfoDao;
	@Autowired
	private BookInfoDao bookInfoDao;
	@Autowired
	private VehIsInfoService vehIsInfoService;
	
	public void download() throws Exception{
		if(new Date().getTime() < interval){
			System.out.println("还在暂停阶段…………");
			return;
		}
		//1、找出预约时间为（当天、明天、后天）、没有下载车辆信息、预约状态为预约中的记录
		List<String> dateStrList = new ArrayList<String>();
		Date currentDate = new Date();
		dateStrList.add(DateUtils.formatDate(currentDate));
		dateStrList.add(DateUtils.formatDate(DateUtils.addDays(currentDate, 1)));
//		dateStrList.add(DateUtils.formatDate(DateUtils.addDays(currentDate, 2)));
		List<BookInfo> bookInfoList = bookInfoDao.findByBookDateListWithoutVehInfo(dateStrList);
		if(CollectionUtils.isEmpty(bookInfoList)){//没有新的预约数据…………
			return;
		}
		
		//2、每次从中取30条
		int increment = 10;
		for(int i=0; i<bookInfoList.size(); i+=increment){
			List<BookInfo> successList = new ArrayList<BookInfo>();//成功记录
			List<BookInfo> failList = new ArrayList<BookInfo>();//失败记录
			int endIndex = i + increment;
			if(endIndex > bookInfoList.size()){
				endIndex = bookInfoList.size();
			}
			List<BookInfo> newList = bookInfoList.subList(i, endIndex);
			for(BookInfo bookInfo : newList){
				if(RedisUtil.getValue(Constants.ERROR_VEHINFO_RECORD+bookInfo.getId()) != null ){
//					System.out.println("错误的车辆信息："+bookInfo.getId()+"-"+bookInfo.getPlatNumber());
//					RedisUtil.delByKey(Constants.ERROR_VEHINFO_RECORD+bookInfo.getId());
					continue;
				}
				String carTypeCode = InitData.getGlobalValue(Constants.CAR_TYPE_ID_PREFIX+bookInfo.getCarTypeId());
				String stationCode = InitData.getGlobalValue(Constants.STATION_ID_PREFIX+bookInfo.getStationId());
				Thread.sleep(8000);//暂停8秒钟，防止过快访问监管平台
				String responseXml = vehIsInfoService.sendXml(bookInfo.getPlatNumber(),carTypeCode,bookInfo.getFrameNumber(), stationCode,InterfaceInvokeType.TIMER);
				if (validateResponseXml(responseXml)) {//下载成功
					VehIsInfo vehIsInfo = (VehIsInfo) ConvertUtils.Xml2Bean(responseXml, null, VehIsInfo.class,new ArrayList<String>());
					vehIsInfo.setBookNumber(bookInfo.getBookNumber());// 定时器读出来的数据自动绑定预约号
					vehIsInfo.setStationId(bookInfo.getStationId());
					vehIsInfo.setDownloadTime(new Date());
					vehIsInfo.setResponseXml(responseXml);
					vehIsInfo.setFullHphm(bookInfo.getPlatNumber());
					vehIsInfoDao.insert(vehIsInfo);
					successList.add(bookInfo);
				}else{//下载失败
					if(responseXml.contains("<code>")){
						failList.add(bookInfo);
					}
				}
			}
			if(CollectionUtils.isEmpty(successList) && failList.size()==increment){//如果成功集合为空，说明30条记录全部出错
				String correctPlatNumber = "BM063M";
				String correctCarTypeCode = "02";
				String correctFrameNumber = "LEFCJCDC3AHP42722";
				String correctStationCode = "4400000142";
				
				boolean flag = false;
				for(int j=0; j<5; j++){
					String responseXml = vehIsInfoService.sendXml(correctPlatNumber,correctCarTypeCode,correctFrameNumber,correctStationCode,InterfaceInvokeType.TIMER);
					if(validateResponseXml(responseXml)){
						flag = true;
						break;//如果5次有1次正确,说明监管平台接口仍然有用
					}
				}
				if(!flag){//如果5次之后仍无效，则视为监管平台接口失效
					//如果真实数据也返回错误信息，则停止定时任务执行，间隔一小时
					interval = System.currentTimeMillis()+(60000*60);
					//如果系统出错，则不是数据问题，将错误记录清空
					failList.clear();
					break;
				}
			}
			updateFailCount(failList);
		}
	}
	
	/**
	 * 更新错误次数
	 * @param failList
	 * @Author        succ
	 * @date 2017-11-14 下午6:04:28
	 */
	private void updateFailCount(List<BookInfo> failList){
		for(BookInfo bookInfo : failList){
			int count = 0;
			String countStr = RedisUtil.getValue(Constants.DOWNLOAD_VEHINFO_FAIL_COUNT+bookInfo.getId());
			if(countStr == null){
				count = 1;
			}else{
				count = Integer.parseInt(countStr)+1;
			}
			if(count >= errorThreshold){
				//超过错误阈值，保存到Redis，1小时内不再获取该预约记录的车辆信息
				RedisUtil.setValue(Constants.ERROR_VEHINFO_RECORD+bookInfo.getId(), "1", 60*60);
				RedisUtil.delByKey(Constants.DOWNLOAD_VEHINFO_FAIL_COUNT+bookInfo.getId());
			}else{
				//错误次数保留2个小时
				RedisUtil.setValue(Constants.DOWNLOAD_VEHINFO_FAIL_COUNT+bookInfo.getId(), count+"", 2*60*60);
			}
		}
	}
	
	/**
	 * 校验是否返回正确结果
	 * @param responseXml
	 * @return
	 * @Author        succ
	 * @date 2017-11-14 上午11:24:18
	 */
	private boolean validateResponseXml(String responseXml){
		if (responseXml != null
				&& "1".equals(ConvertUtils.getValue("code",
						ConvertUtils.decodeUTF8Xml(responseXml)))
				&& StringUtils.isNotBlank(ConvertUtils.getValue("hphm",
						ConvertUtils.decodeUTF8Xml(responseXml)))) {
			return true;
		}
		return false;
	}
}
