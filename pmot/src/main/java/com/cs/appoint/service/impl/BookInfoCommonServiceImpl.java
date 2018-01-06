package com.cs.appoint.service.impl;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cs.appoint.entity.BookInfo;
import com.cs.appoint.service.BookInfoChangeService;
import com.cs.appoint.service.BookInfoService;
import com.cs.appoint.service.BookInfoCommonService;
import com.cs.argument.entity.Station;
import com.cs.common.entityenum.BookState;

@Service
public class BookInfoCommonServiceImpl implements BookInfoCommonService{
	
	@Autowired
	private BookInfoService bookInfoService;
	@Autowired
	private BookInfoChangeService bookInfoChangeService;
	
	@Override
	public String unifiedValidate(BookInfo bookInfo,Station station,String type) {
		String errorMessage = null;
		try {
			// 验证检测站是否能预约该车辆性质的车
/*            String bookVehiCharc = bookInfo.getVehicleCharacter().getIndex() + "";
            String[] vc =  station.getVehicleCharacters().split(",");  
            if(!Arrays.asList(vc).contains(bookVehiCharc)){
                return "该检测站无法预约您的车，请选择可用的检测站!";
            }*/
			// 判断最大预约量
			int maxNumber = station.getMaxNumber()==null?0:station.getMaxNumber();
			int count = bookInfoService.queryCurrentCount(station.getId(), type);
			if(count >= maxNumber ){
				return "当前检测站预约已达预约上限，如有需要请联系中心人员！";
			}
			//判断是否该车是否在外网已经预约
			if(StringUtils.isNotBlank(bookInfo.getNewflag()) && "0".equals(bookInfo.getNewflag())){
				BookInfo existBookInfo = bookInfoService.checkIsExistsAppoint(bookInfo);
				if(existBookInfo != null){
					//判断是否外网预约
					String bookNumber = existBookInfo.getBookNumber();
					if(!bookNumber.startsWith("D") && !bookNumber.startsWith("G") && !bookNumber.startsWith("N")){
						existBookInfo.setBookState(BookState.YYQX);
						bookInfoService.updateByPrimaryKey(existBookInfo);
						//插入状态更改表
						bookInfoChangeService.insert(existBookInfo);
					}else{
						errorMessage = "号牌种类为：" + existBookInfo.getCarTypeName() + "和号牌号码为：" + existBookInfo.getPlatNumber() + "已预约,预约日期为:"+existBookInfo.getBookDate()+"，请携带相关证件到'"+existBookInfo.getStationName()+"'办理!";
						return errorMessage;
					}
				
				}
			}
            // 验证号牌号码，车架号是否存在
/*			String frame_last4 = bookInfo.getFrameNumber().substring(bookInfo.getFrameNumber().length() - 4);
			//调用接口验证车是否存在
		 	String code = WebUtil.calllWSForVehicleTransfer(bookInfo.getPlatNumber(),
	 			carType.getCode(), frame_last4, bookInfo.getRequestIp());
			if (StringUtils.isNotBlank(code) && !"1".equals(code)) {
				return "号牌号码为：{" + bookInfo.getPlatNumber() + "}、车架号后4位为：{" + frame_last4 + "}不存在，请核实！";
			}*/
		} catch (Exception e) {
			e.printStackTrace();
			return "服务器繁忙，请稍后再试!";
		}
		return null;
	}

}
