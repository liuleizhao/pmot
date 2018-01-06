package com.cs.appoint.webservice.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cs.appoint.entity.BookInfo;
import com.cs.appoint.entity.BookInfoCompatible;
import com.cs.appoint.entity.VehIsInfo;
import com.cs.appoint.service.BookInfoCompatibleService;
import com.cs.appoint.service.BookInfoService;
import com.cs.appoint.service.VehIsInfoService;
import com.cs.appoint.webservice.BookInfoWebService;
import com.cs.argument.entity.CarType;
import com.cs.argument.entity.Station;
import com.cs.argument.service.CarTypeService;
import com.cs.argument.service.StationService;
import com.cs.common.constant.Constants;
import com.cs.common.entityenum.BookState;
import com.cs.common.entityenum.GlobalConfigKey;
import com.cs.common.exception.InvalidBookInfoException;
import com.cs.common.utils.ConvertUtils;
import com.cs.common.utils.DateUtil;
import com.cs.common.utils.webservice.ResultCode;
import com.cs.common.utils.webservice.WebServiceIpUtil;
import com.cs.common.utils.webservice.WebServiceXmlUtil;
import com.cs.mvc.dao.SqlCondition;
import com.cs.mvc.init.InitData;
import com.cs.system.dao.FreeVerificationBookConfigDao;
import com.cs.system.entity.FreeVerificationBookConfig;
import com.cs.webservice.entity.InterfaceControlGeneral;
import com.cs.webservice.service.InterfaceControlGeneralService;

@Service
@Transactional
public class BookInfoWebServiceImpl implements BookInfoWebService {
	@Autowired
	private BookInfoService bookInfoService;
	@Autowired
	private FreeVerificationBookConfigDao freeVerificationBookConfigDao;
	@Autowired
	private VehIsInfoService vehIsInfoService;
	@Autowired
	private StationService stationService;
	@Autowired
	private CarTypeService carTypeService;
	@Autowired
	private InterfaceControlGeneralService controlGeneralService;
	@Autowired
	private BookInfoCompatibleService bookInfoCompatibleService;
	
	/**
	 * 根据预约号、当前IP对应的检测站、当前日期 查询是否有预约信息
	 * @param bookNumber
	 * @param stationId
	 * @return
	 * @throws Exception
	 * @Author        succ
	 * @date 2017-11-10 下午2:26:11
	 */
	private BookInfo findByBookNumberAndIp(String bookNumber,String verifyCode) throws Exception{
		String ip = WebServiceIpUtil.getRequestIp();
		InterfaceControlGeneral general = controlGeneralService.findByIp(ip);
		if(general == null){
			throw new InvalidBookInfoException("服务器端提示信息：当前IP和接口序列号无接口访问权限");
		}
		String stationId = general.getStationId();
		SqlCondition sqlCondition = new SqlCondition();
		sqlCondition.addSingleNotNullCriterion("BOOK_NUMBER = ", bookNumber);
		sqlCondition.addSingleNotNullCriterion("STATION_ID = ", stationId);
		sqlCondition.addSingleNotNullCriterion("BOOK_DATE = ", DateUtil.getDayStr(new Date()));
		List<BookState> bookStates = new ArrayList<BookState>();
		bookStates.add(BookState.YYZ);
		bookStates.add(BookState.YYWC);
		sqlCondition.addSingleNotNullCriterion("BOOK_STATE in ",bookStates);
		sqlCondition.addDescOrderbyColumn("CREATE_DATE");
		BookInfo bookInfo = bookInfoService.findUniqueByCondition(sqlCondition);
		if(bookInfo == null){
			throw new InvalidBookInfoException("服务器端提示信息：预约号不存在，请查看数据是否已导入专网！");
		}else{
			if(!verifyCode.equals(bookInfo.getVerifyCode())){
				throw new InvalidBookInfoException("服务器端提示信息：验证码错误！");
			}
		}
		return bookInfo;
	}
	
	/**
	 * 校验预约时间
	 * @param bookInfo
	 * @return
	 * @throws Exception
	 * @Author        succ
	 * @date 2017-11-10 下午2:47:05
	 */
	@Override
	public boolean validBookTime(BookInfo bookInfo,Date nowDate) throws Exception{
		String bookNumber = bookInfo.getBookNumber();
		if(bookNumber != null){
			if(bookNumber.startsWith("D") || bookNumber.startsWith("G")){
				return true;
			}
		}
		//查看系统是否设置了预约验证
		String isOpenVerify = InitData.getGlobalValue(GlobalConfigKey.FREE_VERIFY.name());
		if(!"0".equals(isOpenVerify)){//系统开启预约验证
			//查看该检测站是否免预约验证
			FreeVerificationBookConfig freeVerificationConfig 
				= freeVerificationBookConfigDao.findValidConfigById(bookInfo.getStationId());
			if(freeVerificationConfig==null){
				String beginTime = bookInfo.getBookTime().split("-")[0];
				String endTime = bookInfo.getBookTime().split("-")[1];
				String nowTime = DateUtil.getTimeNow(nowDate);
				
				Integer compatibleValue = null;
				//查看该检测站的预约兼容时长
				BookInfoCompatible specialCompatible = bookInfoCompatibleService.findByStationIdAndDate(bookInfo.getStationId(), DateUtil.getDateYYMMDD(nowDate));
				if(specialCompatible != null){
					compatibleValue = specialCompatible.getCompatibleValue();
				}
				//如果检测站没有设置预约兼容，再查看系统的预约兼容时长
				if(compatibleValue == null){
					String timeCompatibility = InitData.getGlobalValue(GlobalConfigKey.BOOK_COMPATIBLE.name());
					if(StringUtils.isNotBlank(timeCompatibility)){
						compatibleValue = Integer.valueOf(timeCompatibility);
					}
				}
				//开始时间和结束时间向后兼容
				if(compatibleValue != null){
					beginTime = DateUtil.addMinutes(beginTime, -compatibleValue);
					endTime = DateUtil.addMinutes(endTime, compatibleValue);
				}
				if(nowTime.compareTo(beginTime)<0 || nowTime.compareTo(endTime)>0){
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public String getVehicleInfo(String bookNumber, String verifyCode) {
		try{
			//1、校验是否为摩托车(摩托车不用预约，预约号和验证码以中划线开头)
			if(bookNumber.startsWith("-") && verifyCode.startsWith("-")){
				String motoPlatNumber = bookNumber.substring(1);//摩托车完整号牌号码
				String motoFrameNumber = verifyCode.substring(1);//摩托车车架号
				return getMotoVehicleInfo(motoPlatNumber,motoFrameNumber);
			}
			//2、根据预约号、当前IP对应的检测站、当前日期 查询是否有预约信息
			BookInfo bookInfo = this.findByBookNumberAndIp(bookNumber,verifyCode);
			//3、校验预约信息是否在相应时间段
			if(this.validBookTime(bookInfo,new Date())){
				//4、更新预约状态为预约完成
				if(!bookInfo.getBookState().equals(BookState.YYWC)){
					bookInfo.setBookState(BookState.YYWC);
					bookInfoService.updateByBookNumber(BookState.YYWC.getIndex(), bookInfo.getBookNumber());
				}
				//5、如果存在预约信息，返回返回号牌号码、车架号、号牌种类等基本车辆信息
				CarType carType = carTypeService.selectByPrimaryKey(bookInfo.getCarTypeId());
				VehIsInfo vehIsInfo = new VehIsInfo();
				if("1".equals(bookInfo.getNewflag())){
					Station station = stationService.selectByPrimaryKey(bookInfo.getStationId());
					String orderNum = ""+station.getOrderNum();
					if(orderNum.length()==1){
						orderNum = "0"+orderNum;
					}
					String tmpFrame = bookInfo.getFrameNumber();
					if(tmpFrame.length()>6){
						tmpFrame = tmpFrame.substring(tmpFrame.length()-6);
					}
					vehIsInfo.setHphm("粤"+orderNum+tmpFrame);
				}else{
					vehIsInfo.setHphm(bookInfo.getPlatNumber());
				}
				vehIsInfo.setHpzl(carType.getCode());
				vehIsInfo.setClsbdh(bookInfo.getFrameNumber());
				String baseinfo = WebServiceXmlUtil.buildBaseInfo(vehIsInfo);//车辆基本信息报文
				//6、获取vesinfo车辆信息如果本地数据库没有，则调取监管平台接口（如果是新车，直接返回基本信息）
				if("1".equals(bookInfo.getNewflag())){
					return WebServiceXmlUtil.buildResponseXml(ResultCode.SUCCESS, "服务器端提示信息：数据读取成功！", baseinfo);
				}
				SqlCondition sqlCondition = new SqlCondition();
				//2017-11-28 新增FULL_HPHM 保存完整车牌号
				sqlCondition.addSingleNotNullCriterion("FULL_HPHM = ", bookInfo.getPlatNumber());
				sqlCondition.addSingleNotNullCriterion("HPZL = ", carType.getCode());
				sqlCondition.addSingleNotNullCriterion("CLSBDH  LIKE ", "%"+bookInfo.getFrameNumber());
				sqlCondition.addSingleNotNullCriterion("STATION_ID = ", bookInfo.getStationId());
				sqlCondition.addSingleNotNullCriterion("BOOK_NUMBER = ", bookInfo.getBookNumber());
//				sqlCondition.addSingleNotNullCriterion("TO_CHAR(DOWNLOAD_TIME,'yyyy-mm-dd') = ", DateUtil.getDayStr(new Date()));
				List<VehIsInfo> vehIsInfoList = vehIsInfoService.findByCondition(sqlCondition);
				
				if(CollectionUtils.isNotEmpty(vehIsInfoList)){
					String detailinfo = WebServiceXmlUtil.getTagValue("body",ConvertUtils.decodeUTF8Xml(vehIsInfoList.get(0).getResponseXml()));//车辆详细信息报文
					vehIsInfo.setClsbdh(vehIsInfoList.get(0).getClsbdh());//从监管平台读取资料成功，修改预约车架号，防止数据有误。
					
					return WebServiceXmlUtil.buildResponseXml(ResultCode.SUCCESS, "服务器端提示信息：数据读取成功！", WebServiceXmlUtil.buildBaseInfo(vehIsInfo)+detailinfo);
				}else{
					Station station = stationService.selectByPrimaryKey(bookInfo.getStationId());
					String outerResponse = vehIsInfoService.sendXml(bookInfo.getPlatNumber(), carType.getCode(), bookInfo.getFrameNumber(), station.getCode());
					String detailinfo = WebServiceXmlUtil.getTagValue("body",ConvertUtils.decodeUTF8Xml(outerResponse));
					String code = WebServiceXmlUtil.getTagValue("code", outerResponse);
					if(!"1".equals(code)){
						//待处理：解析报文，如果没有返回车辆信息，则把基本的车辆信息返回
						return WebServiceXmlUtil.buildResponseXml(ResultCode.SUCCESS, "服务器端提示信息：监管平台无法返回车辆信息，请确认号牌信息无误后，等待5-10秒再试", baseinfo);
					}
					//车辆读取成功后保存到数据库
					VehIsInfo newVehIsInfo = (VehIsInfo) ConvertUtils.Xml2Bean(outerResponse, null, VehIsInfo.class,new ArrayList<String>());
					newVehIsInfo.setBookNumber(bookInfo.getBookNumber());
					newVehIsInfo.setStationId(bookInfo.getStationId());
					newVehIsInfo.setDownloadTime(new Date());
					newVehIsInfo.setResponseXml(outerResponse);
					newVehIsInfo.setFullHphm(bookInfo.getPlatNumber());
					vehIsInfoService.insert(newVehIsInfo);
					
					vehIsInfo.setClsbdh(newVehIsInfo.getClsbdh());//从监管平台读取资料成功，修改预约车架号，防止数据有误。
					return WebServiceXmlUtil.buildResponseXml(ResultCode.SUCCESS, "服务器端提示信息：数据读取成功！", WebServiceXmlUtil.buildBaseInfo(vehIsInfo)+detailinfo);
				}
			}else{
				return WebServiceXmlUtil.buildResponseXml(ResultCode.FAIL, "服务器端提示信息：不在预约时间段内");
			}
		}catch(InvalidBookInfoException e){
			return WebServiceXmlUtil.buildResponseXml(ResultCode.FAIL, e.getMessage());
		}catch(Exception e){
			return WebServiceXmlUtil.buildResponseXml(ResultCode.SYSTEM_ERROR);
		}
	}
	
	/**
	 * 获取摩托车车辆信息
	 * @param motoPlatNumber	号牌号码
	 * @param motoFrameNumber	车架号
	 * @return
	 * @throws Exception
	 */
	private String getMotoVehicleInfo(String motoPlatNumber,String motoFrameNumber) throws Exception{
		String motoBaseinfo = "";//车辆基本信息报文
		VehIsInfo vehIsInfo = new VehIsInfo();
		vehIsInfo.setHphm(motoPlatNumber);
		vehIsInfo.setClsbdh(motoFrameNumber);
		
		SqlCondition motoCondition = new SqlCondition();
		motoCondition.addSingleNotNullCriterion("FULL_HPHM = ", motoPlatNumber);
		motoCondition.addSingleNotNullCriterion("CLSBDH LIKE ", "%"+motoFrameNumber);
		motoCondition.addSingleNotNullCriterion("TO_CHAR(DOWNLOAD_TIME,'yyyy-mm-dd') = ", DateUtil.getDayStr(new Date()));
		List<VehIsInfo> motoVehIsInfoList = vehIsInfoService.findByCondition(motoCondition);
		if(CollectionUtils.isNotEmpty(motoVehIsInfoList)){
			String detailinfo = WebServiceXmlUtil.getTagValue("body",ConvertUtils.decodeUTF8Xml(motoVehIsInfoList.get(0).getResponseXml()));//车辆详细信息报文
			
			vehIsInfo.setHpzl(WebServiceXmlUtil.getTagValue("hpzl", detailinfo));
			motoBaseinfo = WebServiceXmlUtil.buildBaseInfo(vehIsInfo);
			return WebServiceXmlUtil.buildResponseXml(ResultCode.SUCCESS, "服务器端提示信息：数据读取成功！", motoBaseinfo+detailinfo);
		}else{
			String[] motoTypes = {"07","24"};//普通摩托车、警用摩托车
			String ip = WebServiceIpUtil.getRequestIp();
			InterfaceControlGeneral general = controlGeneralService.findByIp(ip);
			if(general == null){
				throw new InvalidBookInfoException("服务器端提示信息：当前IP和接口序列号无接口访问权限");
			}
			Station station = stationService.selectByPrimaryKey(general.getStationId());
			for(int i=0; i<motoTypes.length; i++){
				String outerResponse = vehIsInfoService.sendXml(motoPlatNumber, motoTypes[i], motoFrameNumber, station.getCode());
				String detailinfo = WebServiceXmlUtil.getTagValue("body",ConvertUtils.decodeUTF8Xml(outerResponse));
				String code = WebServiceXmlUtil.getTagValue("code", outerResponse);
				if(!"1".equals(code)){
					if(i == motoTypes.length-1){//如果最后一次仍然获取不到数据，返回摩托车基本信息
						return WebServiceXmlUtil.buildResponseXml(ResultCode.SUCCESS, 
								"服务器端提示信息：监管平台无法返回车辆信息，请确认号牌信息无误后，等待5-10秒再试", motoBaseinfo);
					}else{
						continue;
					}
				}
				//车辆读取成功后保存到数据库
				VehIsInfo newVehIsInfo = (VehIsInfo) ConvertUtils.Xml2Bean(outerResponse, null, VehIsInfo.class,new ArrayList<String>());
				newVehIsInfo.setStationId(station.getId());
				newVehIsInfo.setDownloadTime(new Date());
				newVehIsInfo.setResponseXml(outerResponse);
				newVehIsInfo.setFullHphm(motoPlatNumber);
				vehIsInfoService.insert(newVehIsInfo);
				
				vehIsInfo.setHpzl(WebServiceXmlUtil.getTagValue("hpzl", detailinfo));
				motoBaseinfo = WebServiceXmlUtil.buildBaseInfo(vehIsInfo);
				return WebServiceXmlUtil.buildResponseXml(ResultCode.SUCCESS, "服务器端提示信息：数据读取成功！", motoBaseinfo+detailinfo);
			}
		}
		
		return WebServiceXmlUtil.buildResponseXml(ResultCode.SUCCESS, 
				"服务器端提示信息：监管平台无法返回车辆信息，请确认号牌信息无误后，等待5-10秒再试", motoBaseinfo);
	}
	
	@Override
	public String preGetVehicleInfo(String platNumber, String carType,
			String frameNumber, String verifyCode) {
		try{
			//1、校验是否为摩托车(摩托车不用预约，预约号和验证码以中划线开头)
			if("07".equals(carType) || "24".equals(carType)){
				return getMotoVehicleInfo(platNumber,carType,frameNumber);
			}
			String ip = WebServiceIpUtil.getRequestIp();
			InterfaceControlGeneral general = controlGeneralService.findByIp(ip);
			String carTypeId = InitData.getGlobalValue(Constants.CAR_TYPE_CODE_PREFIX+carType);
			if(carTypeId == null){
				return WebServiceXmlUtil.buildResponseXml(ResultCode.FAIL, "服务器端提示信息：号牌种类有误！");
			}
			SqlCondition sqlCondition = new SqlCondition();
			sqlCondition.addSingleNotNullCriterion("CAR_TYPE_ID = ", carTypeId);
			sqlCondition.addSingleNotNullCriterion("FRAME_NUMBER = ", frameNumber);
			if(frameNumber.length() == 4){ //旧车有号牌号码
				sqlCondition.addSingleNotNullCriterion("PLAT_NUMBER = ", platNumber);
			}
			sqlCondition.addSingleNotNullCriterion("STATION_ID = ", general.getStationId());
			sqlCondition.addSingleNotNullCriterion("BOOK_DATE = ", DateUtil.getDayStr(new Date()));
			List<BookState> bookStates = new ArrayList<BookState>();
			bookStates.add(BookState.YYZ);
			bookStates.add(BookState.YYWC);
			sqlCondition.addSingleNotNullCriterion("BOOK_STATE in ",bookStates);
			sqlCondition.addDescOrderbyColumn("CREATE_DATE");
			List<BookInfo> bookInfos = bookInfoService.findByCondition(sqlCondition);
			if(CollectionUtils.isEmpty(bookInfos)){
				throw new InvalidBookInfoException("服务器端提示信息：预约号不存在，请查看数据是否已导入专网！");
			}
			//预约记录
			BookInfo bookInfo = bookInfos.get(0);
			if(!this.validBookTime(bookInfo,new Date())){
				return WebServiceXmlUtil.buildResponseXml(ResultCode.FAIL, "服务器端提示信息：不在预约时间段内");
			}
			//更新预约状态为完成
			if(!bookInfo.getBookState().equals(BookState.YYWC)){
				bookInfo.setBookState(BookState.YYWC);
				bookInfoService.updateByBookNumber(BookState.YYWC.getIndex(), bookInfo.getBookNumber());
			}
			if(frameNumber.length() > 4){//新车
				return WebServiceXmlUtil.buildResponseXml(ResultCode.SUCCESS, "服务器端提示信息：通过预约验证！");
			}else{
				SqlCondition vehIsInfoCondition = new SqlCondition(); 
				vehIsInfoCondition.addSingleNotNullCriterion("FULL_HPHM = ", platNumber);
				vehIsInfoCondition.addSingleNotNullCriterion("HPZL = ", carType);
				vehIsInfoCondition.addSingleNotNullCriterion("CLSBDH  LIKE ", "%"+frameNumber);
				vehIsInfoCondition.addSingleNotNullCriterion("STATION_ID = ", bookInfo.getStationId());
				vehIsInfoCondition.addSingleNotNullCriterion("BOOK_NUMBER = ", bookInfo.getBookNumber());
				List<VehIsInfo> vehIsInfoList = vehIsInfoService.findByCondition(vehIsInfoCondition);
				if(CollectionUtils.isNotEmpty(vehIsInfoList)){
					String detailinfo = WebServiceXmlUtil.getTagValue("body",ConvertUtils.decodeUTF8Xml(vehIsInfoList.get(0).getResponseXml()));//车辆详细信息报文
					return WebServiceXmlUtil.buildResponseXml(ResultCode.SUCCESS, "服务器端提示信息：数据读取成功！", detailinfo);
				}else{
					String stationCode = InitData.getGlobalValue(Constants.STATION_ID_PREFIX+bookInfo.getStationId());
					String outerResponse = vehIsInfoService.sendXml(bookInfo.getPlatNumber(), carType, bookInfo.getFrameNumber(), stationCode);
					String detailinfo = WebServiceXmlUtil.getTagValue("body",ConvertUtils.decodeUTF8Xml(outerResponse));
					String code = WebServiceXmlUtil.getTagValue("code", outerResponse);
					if(!"1".equals(code)){
						return WebServiceXmlUtil.buildResponseXml(ResultCode.SUCCESS, "服务器端提示信息：监管平台无法返回车辆信息，请确认号牌信息无误后，等待5-10秒再试");
					}
					//车辆读取成功后保存到数据库
					VehIsInfo newVehIsInfo = (VehIsInfo) ConvertUtils.Xml2Bean(outerResponse, null, VehIsInfo.class,new ArrayList<String>());
					newVehIsInfo.setBookNumber(bookInfo.getBookNumber());
					newVehIsInfo.setStationId(bookInfo.getStationId());
					newVehIsInfo.setDownloadTime(new Date());
					newVehIsInfo.setResponseXml(outerResponse);
					newVehIsInfo.setFullHphm(bookInfo.getPlatNumber());
					vehIsInfoService.insert(newVehIsInfo);
					return WebServiceXmlUtil.buildResponseXml(ResultCode.SUCCESS, "服务器端提示信息：数据读取成功！", detailinfo);
				}
			}
		}catch(InvalidBookInfoException e){
			return WebServiceXmlUtil.buildResponseXml(ResultCode.FAIL, e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			return WebServiceXmlUtil.buildResponseXml(ResultCode.SYSTEM_ERROR);
		}
	}
	
	/**
	 * 获取摩托车车辆信息
	 * @param motoPlatNumber	号牌号码
	 * @param motoCarType		号牌种类
	 * @param motoFrameNumber	车架号
	 * @return
	 * @throws Exception
	 */
	private String getMotoVehicleInfo(String motoPlatNumber, String motoCarType,String motoFrameNumber) throws Exception{
		SqlCondition motoCondition = new SqlCondition();
		motoCondition.addSingleNotNullCriterion("FULL_HPHM = ", motoPlatNumber);
		motoCondition.addSingleNotNullCriterion("HPZL = ", motoCarType);
		motoCondition.addSingleNotNullCriterion("CLSBDH LIKE ", "%"+motoFrameNumber);
		motoCondition.addSingleNotNullCriterion("TO_CHAR(DOWNLOAD_TIME,'yyyy-mm-dd') = ", DateUtil.getDayStr(new Date()));
		List<VehIsInfo> motoVehIsInfoList = vehIsInfoService.findByCondition(motoCondition);
		if(CollectionUtils.isNotEmpty(motoVehIsInfoList)){
			String detailinfo = WebServiceXmlUtil.getTagValue("body",ConvertUtils.decodeUTF8Xml(motoVehIsInfoList.get(0).getResponseXml()));//车辆详细信息报文
			return WebServiceXmlUtil.buildResponseXml(ResultCode.SUCCESS, "服务器端提示信息：数据读取成功！", detailinfo);
		}else{
			String ip = WebServiceIpUtil.getRequestIp();
			InterfaceControlGeneral general = controlGeneralService.findByIp(ip);
			String stationId = general.getStationId();
			String stationCode = InitData.getGlobalValue(Constants.STATION_ID_PREFIX+general.getStationId());
			String outerResponse = vehIsInfoService.sendXml(motoPlatNumber, motoCarType, motoFrameNumber, stationCode);
			String code = WebServiceXmlUtil.getTagValue("code", outerResponse);
			if(!"1".equals(code)){
				return WebServiceXmlUtil.buildResponseXml(ResultCode.SUCCESS,
						"服务器端提示信息：监管平台无法返回车辆信息，请确认号牌信息无误后，等待5-10秒再试");
			}else{
				String detailinfo = WebServiceXmlUtil.getTagValue("body",ConvertUtils.decodeUTF8Xml(outerResponse));
				//车辆读取成功后保存到数据库
				VehIsInfo newVehIsInfo = (VehIsInfo) ConvertUtils.Xml2Bean(detailinfo, null, VehIsInfo.class,new ArrayList<String>());
				newVehIsInfo.setStationId(stationId);
				newVehIsInfo.setDownloadTime(new Date());
				newVehIsInfo.setResponseXml(outerResponse);
				newVehIsInfo.setFullHphm(motoPlatNumber);
				vehIsInfoService.insert(newVehIsInfo);
				return WebServiceXmlUtil.buildResponseXml(ResultCode.SUCCESS, "服务器端提示信息：数据读取成功！", detailinfo);
			}
		}
	}
}
