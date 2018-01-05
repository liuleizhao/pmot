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
import com.cs.common.utils.ConvertUtils;
import com.cs.common.utils.DateUtils;
import com.cs.common.utils.RedisUtil;
import com.cs.webservice.entity.InterfaceLog;
import com.cs.webservice.service.InterfaceInvokeCounterService;
import com.cs.webservice.service.InterfaceLogService;

public class DownloadVehIsInfoTimer {
	private static long interval = 0L;
	private static final int errorThreshold = 10;//错误阈值（车辆信息错误次数超过该值则认定为错误信息）
	
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
	@Autowired
	private InterfaceLogService interfaceLogService;
	@Autowired
	private InterfaceInvokeCounterService interfaceInvokeCounterService;
	
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
		dateStrList.add(DateUtils.formatDate(DateUtils.addDays(currentDate, 2)));
		List<BookInfo> bookInfoList = bookInfoDao.findByBookDateListWithoutVehInfo(dateStrList);
		if(CollectionUtils.isEmpty(bookInfoList)){//没有新的预约数据…………
			return;
		}
		
		//2、每次从中取30条
		int increment = 30;
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
				CarType carType = carTypeDao.selectByPrimaryKey(bookInfo.getCarTypeId());
				Station station = stationDao.selectByPrimaryKey(bookInfo.getStationId());
				
				Date requestDate = new Date();//请求时间
				String responseXml = vehIsInfoService.sendXml(bookInfo.getPlatNumber(),carType.getCode(),bookInfo.getFrameNumber(), station.getCode());
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
				
				// 接口调用次数+1
				interfaceInvokeCounterService.plusOne();
				//记录日志
				InterfaceLog log = new InterfaceLog();
				Date responseDate = new Date();//响应时间
				log.setJkid("JK01");
				log.setIp("190.205.101.3");
				log.setRequestDate(requestDate);
				log.setRequestXml(buildQueryXml(bookInfo.getPlatNumber(),carType.getCode(),bookInfo.getFrameNumber(), station.getCode()));
				log.setResponseDate(responseDate);
				log.setResponseXml(ConvertUtils.decodeUTF8Xml(responseXml));
				log.setRunTime((new Long((responseDate.getTime() - requestDate.getTime()))).intValue());
				interfaceLogService.insert(log);
			}
			if(CollectionUtils.isEmpty(successList) && failList.size()==increment){//如果成功集合为空，说明30条记录全部出错
				String correctPlatNumber = "粤B993U5";
				String correctCarTypeCode = "02";
				String correctFrameNumber = "LHGCR1622H8020612";
				String correctStationCode = "4400000142";
				
				boolean flag = false;
				for(int j=0; j<5; j++){
					Date requestDate = new Date();
					String responseXml = vehIsInfoService.sendXml(correctPlatNumber,correctCarTypeCode,correctFrameNumber,correctStationCode);
					
					// 接口调用次数+1
					interfaceInvokeCounterService.plusOne();
					//记录日志
					InterfaceLog log = new InterfaceLog();
					Date responseDate = new Date();//响应时间
					log.setJkid("JK01");
					log.setIp("190.205.101.3");
					log.setRequestDate(requestDate);
					log.setRequestXml(buildQueryXml(correctPlatNumber,correctCarTypeCode,correctFrameNumber,correctStationCode));
					log.setResponseDate(responseDate);
					log.setResponseXml(ConvertUtils.decodeUTF8Xml(responseXml));
					log.setRunTime((new Long((responseDate.getTime() - requestDate.getTime()))).intValue());
					interfaceLogService.insert(log);
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
				//超过错误阈值，保存到Redis，30分钟内不再获取该预约记录的车辆信息
				RedisUtil.setValue(Constants.ERROR_VEHINFO_RECORD+bookInfo.getId(), "1", 30*60);
				RedisUtil.delByKey(Constants.DOWNLOAD_VEHINFO_FAIL_COUNT+bookInfo.getId());
			}else{
				RedisUtil.setValue(Constants.DOWNLOAD_VEHINFO_FAIL_COUNT+bookInfo.getId(), count+"", 60*60);
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
	
	private String buildQueryXml(String platNumber, String carTypeCode,
			String chassisNumber, String jyjgbh) {
		String xmlDoc = "<?xml version=\"1.0\" encoding=\"GBK\"?>\n<root>"
				+ "\n<QueryCondition>\n<hphm>" + platNumber + "</hphm>\n<hpzl>"
				+ carTypeCode + "</hpzl>\n<clsbdh>" + chassisNumber
				+ "</clsbdh>\n<jyjgbh>" + jyjgbh
				+ "</jyjgbh>\n</QueryCondition>\n</root>";
		return xmlDoc;
	}
	
	public static void main(String[] args) {
		List<String> dateStrList = new ArrayList<String>();
		Date currentDate = new Date();
		dateStrList.add(DateUtils.formatDate(currentDate));
		dateStrList.add(DateUtils.formatDate(DateUtils.addDays(currentDate, 1)));
		dateStrList.add(DateUtils.formatDate(DateUtils.addDays(currentDate, 2)));
		
		System.out.println(dateStrList);
	}
}
