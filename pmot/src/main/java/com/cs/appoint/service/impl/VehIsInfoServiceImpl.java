package com.cs.appoint.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cs.appoint.dao.BookInfoDao;
import com.cs.appoint.dao.VehIsInfDao;
import com.cs.appoint.entity.BookInfo;
import com.cs.appoint.entity.VehIsInfo;
import com.cs.appoint.service.ForwardService;
import com.cs.appoint.service.SupervisionService;
import com.cs.appoint.service.VehIsInfoService;
import com.cs.argument.dao.CarTypeDao;
import com.cs.argument.entity.CarType;
import com.cs.argument.entity.Station;
import com.cs.common.constant.Constants;
import com.cs.common.entityenum.AppointStateEnum;
import com.cs.common.utils.ConvertUtils;
import com.cs.common.utils.DateUtil;
import com.cs.common.utils.DateUtils;
import com.cs.mvc.dao.BaseDao;
import com.cs.mvc.dao.SqlCondition;
import com.cs.mvc.init.InitData;
import com.cs.mvc.service.BaseServiceSupport;
import com.cs.system.service.GlobalConfigService;
import com.cs.webservice.entity.InterfaceInvokeCounter;
import com.cs.webservice.service.InterfaceInvokeCounterService;

@Service
@Transactional
public class VehIsInfoServiceImpl extends BaseServiceSupport<VehIsInfo, String> implements VehIsInfoService {
	@Autowired
	private VehIsInfDao vehIsInfoDao;
	
	@Autowired
	private BookInfoDao bookInfodao;
	
	@Autowired
	private CarTypeDao carTypeDao;
	
	@Autowired
	private SupervisionService supervisionService;
	
	@Autowired
	private GlobalConfigService configService;
	
	@Autowired
	private ForwardService forwardService;
	
	@Autowired
	private InterfaceInvokeCounterService interfaceInvokeCounterService;
	
	@Override
	protected BaseDao<VehIsInfo, String> getBaseDao() throws Exception {
		
		return vehIsInfoDao;
	}

	@Override
	public String getVehIsInfos(Station station) throws Exception {
		String appointmentDate = DateUtil.getDayStr(new Date());
		StringBuffer bookNumbers = new StringBuffer();
		SqlCondition condition=new SqlCondition();
		condition.addSingleCriterion("book_date=", appointmentDate);
		condition.addSingleCriterion("BOOK_STATE=", AppointStateEnum.SUCCESS.getState());
		condition.addSingleCriterion("station_id=", station.getId());
		List<BookInfo> list=bookInfodao.findByCondition(condition);
		if (list != null && list.size() > 0) {
			int count = 0;
			for (BookInfo bookinfo : list) {
				condition=new SqlCondition();
				condition.addSingleCriterion("book_number=",bookinfo.getBookNumber() );
				List<VehIsInfo> vehIsInfoList=vehIsInfoDao.findByCondition(condition);
				if(vehIsInfoList.size()==0){
					count++;
					String ResponseXml = null;
					try {
						CarType type=carTypeDao.selectByPrimaryKey(bookinfo.getCarTypeId());
						ResponseXml = sendXml(bookinfo.getPlatNumber(),
								type.getCode(),
								bookinfo.getFrameNumber(), station.getCode());
					} catch (Exception e) {
						e.printStackTrace();
						continue;
					}
					if (ResponseXml != null
							&& "1".equals(ConvertUtils.getValue("code",
									ConvertUtils.decodeUTF8Xml(ResponseXml)))
							&& StringUtils.isNotBlank(ConvertUtils.getValue("hphm",
									ConvertUtils.decodeUTF8Xml(ResponseXml)))) {// 下载成功
						try {
							VehIsInfo vehIsInfo = (VehIsInfo) ConvertUtils
									.Xml2Bean(ResponseXml, null, VehIsInfo.class,
											new ArrayList<String>());
							vehIsInfo.setBookNumber(bookinfo.getBookNumber());// 定时器读出来的数据自动绑定预约号
							vehIsInfo.setStationId(bookinfo.getStationId());
							vehIsInfo.setDownloadTime(new Date());// 下载时间
							vehIsInfo.setResponseXml(ResponseXml);
							vehIsInfo.setFullHphm(bookinfo.getPlatNumber());
							vehIsInfoDao.insert(vehIsInfo);
							bookNumbers.append(bookinfo.getBookNumber() + ";");// 记录已下载成功的预约记录
						} catch (Exception ex) {
							ex.printStackTrace();
							continue;
						}
					}
				}
			}
			System.out.println("==============" + station.getName()
					+ "             共跑了:" + count);
		}
		return bookNumbers.toString();
	}
	
	/**
	 * 发送请求XML
	 * 
	 * @param platNumber
	 * @param carTypeCode
	 * @param chassisNumber
	 * @param station
	 * @return
	 * @throws Exception
	 */
	public String sendXml(String platNumber, String carTypeCode,
			String chassisNumber, String stionNum ) throws Exception {
		String jyjgbh = stionNum;// 检测站编号
		String jkxlh = Constants.JKXLH;
		String jkid = Constants.GET_VEHICLE_INFO_JKID;// 下载车辆数据的接口ID
		String xtlb = Constants.XTLB;// 系统类别：默认为18
		String queryXmlDoc = buildQueryXml(platNumber, carTypeCode,
				chassisNumber, jyjgbh);
		
		//获取最大调用次数
		String maxCountStr = InitData.getGlobalValue("MAX_REQUEST_COUNT");
		int maxCount = 0;
		if(maxCountStr!=null){
			try {
				maxCount = Integer.valueOf(maxCountStr);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//获取调用次数
		InterfaceInvokeCounter interfaceInvokeCounter= interfaceInvokeCounterService.findByInvokeDate(DateUtils.getDate());
		int count = 0;
		if(interfaceInvokeCounter != null){
			count = interfaceInvokeCounter.getInvokeCount();
		}
		
		//调用接口
		String ResponseXml = null;
		//1、判断是否处于工作时间
		String currentTime = DateUtils.getTime();
		if(currentTime.compareTo("17:00:00")>0 || currentTime.compareTo("10:00:00")<0){
			ResponseXml = supervisionService.queryObjectOut(xtlb, jkxlh, jkid,
					queryXmlDoc);
		}else{
			//2、判断是否超过调用限制量
			if( count > maxCount ){
				ResponseXml = forwardService.getCarInfo(queryXmlDoc);
			}else{
				ResponseXml = supervisionService.queryObjectOut(xtlb, jkxlh, jkid,
						queryXmlDoc);
			}
		}
		return ResponseXml;
	}
	
	/**
	 * 组建XML语句
	 * 
	 * @param platNumber
	 * @param carTypeCode
	 * @param chassisNumber
	 * @param jyjgbh
	 * @return
	 */
	private String buildQueryXml(String platNumber, String carTypeCode,
			String chassisNumber, String jyjgbh) {
		String xmlDoc = "<?xml version=\"1.0\" encoding=\"GBK\"?>\n<root>"
				+ "\n<QueryCondition>\n<hphm>" + platNumber + "</hphm>\n<hpzl>"
				+ carTypeCode + "</hpzl>\n<clsbdh>" + chassisNumber
				+ "</clsbdh>\n<jyjgbh>" + jyjgbh
				+ "</jyjgbh>\n</QueryCondition>\n</root>";
		return xmlDoc;
	}

}
