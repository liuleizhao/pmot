package com.cs.appoint.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cs.appoint.dao.BookInfoDao;
import com.cs.appoint.dto.BookInfoAmount;
import com.cs.appoint.entity.BookInfo;
import com.cs.appoint.entity.CompApplyFrom;
import com.cs.appoint.service.BookInfoService;
import com.cs.appoint.service.CompApplyFromService;
import com.cs.appoint.vo.ComApplyBookInfoVO;
import com.cs.argument.entity.CarType;
import com.cs.argument.entity.IdType;
import com.cs.argument.entity.Station;
import com.cs.argument.service.CarTypeService;
import com.cs.argument.service.IdTypeService;
import com.cs.argument.service.StationService;
import com.cs.common.entityenum.BookState;
import com.cs.common.utils.DateUtil;
import com.cs.common.utils.DateUtils;
import com.cs.common.utils.RandomValue;
import com.cs.mvc.dao.BaseDao;
import com.cs.mvc.dao.SqlCondition;
import com.cs.mvc.service.BaseServiceSupport;
import com.cs.system.entity.User;

@Service
@Transactional
public class BookInfoServiceImpl extends BaseServiceSupport<BookInfo, String> implements BookInfoService{

	@Autowired
	private BookInfoDao bookInfoDao;
	
	@Autowired
	private IdTypeService idTypeService;
	
	@Autowired
	private StationService stationService;
	@Autowired
	private CarTypeService carTypeService;
	
	@Autowired
	private CompApplyFromService mApplyFromService;
	
	@Override
	protected BaseDao<BookInfo, String> getBaseDao() throws Exception {
		return bookInfoDao;
	}
	
	public void updateByBookNumber(int bookState, String bookNumber){
		bookInfoDao.updateByBookNumber(bookState,bookNumber);
	}
	
	@Override
	public BookInfo selectByBookNumber(String bookNumber) throws Exception {
		SqlCondition sqlCondition = new SqlCondition();
		sqlCondition.addSingleNotNullCriterion("BOOK_NUMBER =",bookNumber);
		List<BookInfo> bookInfos = bookInfoDao.findByCondition(sqlCondition);
		if(bookInfos != null && bookInfos.size() > 0){
			return bookInfos.get(0);
		}
		return null;
	}

	@Override
	public List<BookInfoAmount> findFinishedBookAmountGroupByStation(
			Date startDate, Date endDate) {
		return bookInfoDao.findFinishedBookAmountGroupByStation(startDate, endDate);
	}
	
	@Override
	public String insertBookInfo(BookInfo bookInfo,String type){
		String mesage = null;
		bookInfo.setBookState(BookState.YYZ);
		bookInfo.setCreateDate(new Date());
		bookInfo.setBookNumber(type+createBookNum(bookInfo.getCreateDate()));
		String verifyCode = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
		bookInfo.setVerifyCode(verifyCode);
		try {
			bookInfoDao.insert(bookInfo);
		} catch (Exception e) {
			mesage = "系统出现异常："+e.getMessage();
		}
		return mesage;
	}
	
	private String createBookNum(Date createDate){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddHH");
		StringBuilder out = new StringBuilder(dateFormat.format(createDate));
		out.append(RandomValue.getRandomValue("pmot"));
		return out.toString();
	}

	@Override
	public String returnNumber(String bookNumber, String verifyCode){
		BookInfo bookInfo = null;
		String message = "";
		try {
			bookInfo = this.selectByBookNumber(bookNumber);
			if(null == bookInfo){
				message = "未找到对应的实体信息！" ;
				return message;
			}
			if(!BookState.YYZ.equals(bookInfo.getBookState())){
				message = "预约信息不在预约中，无法废除！" ;
				return message;
			}
			// 判断时间
			if(!DateUtils.getDate().equals(bookInfo.getBookDate())){
				message =  "预约日期是当天才能废除，预约号："+bookNumber+"的预约日期为："+bookInfo.getBookDate()+"！" ;
				return message;
			}
			if(!bookInfo.getVerifyCode().equals(verifyCode)){
				message = "验证码与预约成功时的验证码不一致，废除失败！";
				return message;
			}
			bookInfo.setBookState(BookState.ZF);
			updateByPrimaryKey(bookInfo);
		} catch (Exception e) {
			e.printStackTrace();
			message = "系统出现异常：" + e.getMessage();
			return message;
		}
		return message;
	}
	
	@Override
	public BookInfo checkIsExistsAppoint(BookInfo bookInfo) throws Exception {
		SqlCondition sqlCondition = new SqlCondition();
		String nowDate = DateUtil.getDayStr(new Date());
		sqlCondition.addSingleNotNullCriterion("PLAT_NUMBER =",bookInfo.getPlatNumber());
		sqlCondition.addSingleCriterion("CAR_TYPE_ID = ",bookInfo.getCarTypeId()); 
		sqlCondition.addSingleCriterion("BOOK_STATE = ",BookState.YYZ);
		sqlCondition.addSingleCriterion("BOOK_DATE >= ",nowDate);
		List<BookInfo> bookInfos = bookInfoDao.findByCondition(sqlCondition);
		if(bookInfos != null && bookInfos.size() > 0){//存在
			return bookInfos.get(0);
		}
		return null;
	}
	
	@Override
	public List<BookInfo> queryCurrentAppoint(String stationId,String type) throws Exception{
		/*SqlCondition sqlCondition = new SqlCondition();
		String nowDate = DateUtil.getDayStr(new Date());
		sqlCondition.addLikeCriterion("book_number like ",type+"%"); 
		sqlCondition.addSingleCriterion("STATION_ID = ",stationId);
		sqlCondition.addSingleCriterion("BOOK_DATE = ",nowDate);
		sqlCondition.addSingleCriterion("to_char(CREATE_DATE,'YYYY-MM-DD') = ",nowDate);
		List<BookInfo> bookInfos = bookInfoDao.findByCondition(sqlCondition);
		if(bookInfos != null && bookInfos.size() > 0){//存在
			return bookInfos;
		}*/
		String nowDate = DateUtil.getDayStr(new Date());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", type+"%");
		map.put("stationId", stationId);
		map.put("bookDate", nowDate);
		return bookInfoDao.queryCurrentAppoint(map);
	}
	
	@Override
	public int queryCurrentCount(String stationId,String type) throws Exception{
		List<BookInfo> bookInfos = this.queryCurrentAppoint(stationId, type);
		if(null != bookInfos && bookInfos.size() > 0){
			return bookInfos.size();
		}
		return 0;
	}
	
	@Override
	public List<BookInfo> findCheckStateNotFinished() {
		return bookInfoDao.findCheckStateNotFinished();
	}
	

	@Override
	public String batchAdd(List<BookInfo> bookInfos) {
		try {
			bookInfoDao.batchAdd(bookInfos);
		} catch (Exception e) {
			return "添加失败";
		}
		return null;
	}

	@Override
	public List<BookInfo> batchAddCompAplyy(BookInfo bookInfo,
			List<ComApplyBookInfoVO> comBooks,User backendUser,CompApplyFrom compApplyFrom) throws Exception {
		List<BookInfo> bookInfoList = new ArrayList<BookInfo>();  //用于批量新增
		List<BookInfo> existBookInfos = new ArrayList<BookInfo>(); //用于返回已预约的信息
			IdType idType = idTypeService.selectByPrimaryKey(bookInfo.getIdTypeId());
			Station station = stationService.selectByPrimaryKey(backendUser.getStationId());
			for(ComApplyBookInfoVO comBook:comBooks){
				BookInfo existBookInfo = null;
				if(comBook.getNewflag().equals("0")){  //旧车
					existBookInfo = this.checkBooking(comBook.getCarTypeId(), comBook.getPlatNumber());
				}
				
				if(null == existBookInfo){
					BookInfo book = new BookInfo();
					book.setBookChannel(bookInfo.getBookChannel());
					book.setBookerName(bookInfo.getBookerName());
					book.setCompApplyFormId(bookInfo.getCompApplyFormId());
					book.setIdNumber(bookInfo.getIdNumber());
					book.setOtherIdNumber(bookInfo.getOtherIdNumber());
					book.setMobile(bookInfo.getMobile());
					book.setRequestIp(bookInfo.getRequestIp());
					
					book.setStationId(station.getId());
					book.setStationName(station.getName());
					book.setIdTypeId(idType.getId());
					book.setIdTypeName(idType.getName());
					book.setUserId(backendUser.getId());
					book.setUserName(backendUser.getName());
					
					book.setBookState(BookState.YYZ);
					book.setCreateDate(new Date());
					book.setBookNumber("D"+createBookNum(book.getCreateDate()));  //D标识大客户
					String verifyCode = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
					book.setVerifyCode(verifyCode);
					
					CarType carType = carTypeService.selectByPrimaryKey(comBook.getCarTypeId());
					book.setCarTypeId(carType.getId());
					book.setCarTypeName(carType.getName());
					book.setBookDate(comBook.getBookDate());
					book.setPlatNumber(comBook.getPlatNumber().toUpperCase());
					book.setFrameNumber(comBook.getFrameNumber().toUpperCase());
					book.setNewflag(comBook.getNewflag());
					
					
					bookInfoList.add(book);
				}else{
					existBookInfos.add(existBookInfo);
				}
			}
			//更新添加预约量
			if(null != bookInfoList && bookInfoList.size() > 0){
				compApplyFrom.setAddedNum(compApplyFrom.getAddedNum()+bookInfoList.size());
				mApplyFromService.updateByPrimaryKey(compApplyFrom);
				this.batchAdd(bookInfoList);
			}
		if(null != existBookInfos && existBookInfos.size() >0){
			
			return existBookInfos;
		}
		return null;
	}

	@Override
	public BookInfo checkBooking(String carTypeId, String platNumber)
			throws Exception {
		String nowDate = DateUtil.getDayStr(new Date());
		SqlCondition sqlCondition = new SqlCondition();
		sqlCondition.addSingleNotNullCriterion("PLAT_NUMBER =",platNumber);
		sqlCondition.addSingleCriterion("CAR_TYPE_ID = ",carTypeId); 
		sqlCondition.addSingleCriterion("BOOK_STATE = ",BookState.YYZ);
		sqlCondition.addSingleCriterion("BOOK_DATE >= ",nowDate);
		List<BookInfo> bookInfos = bookInfoDao.findByCondition(sqlCondition);
		if(bookInfos != null && bookInfos.size() > 0){//存在
			return bookInfos.get(0);
		}
		return null;
	
	}
}
