package com.cs.webservice.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cs.appoint.service.VehIsInfoService;
import com.cs.argument.service.StationService;
import com.cs.common.entityenum.InterfaceInvokeType;
import com.cs.common.utils.ConvertUtils;
import com.cs.common.utils.DateUtils;
import com.cs.mvc.web.BaseController;

@Controller
@RequestMapping(value = "/backend/webservice/test")
public class InterfaceTestController extends BaseController{
	
	@Autowired
	private VehIsInfoService vehIsInfoService;
	@Autowired
	private StationService stationService;
	
	@RequestMapping(value="/getVehicleInfo",method=RequestMethod.GET)
	public String getVehicleInfoUI(HttpServletRequest request) throws Exception{
//		request.setAttribute("stations", stationService.findAllData());
		return "/backend/webservice/vehicleInfo";
	}
	
	@RequestMapping(value="/getVehicleInfo",method=RequestMethod.POST)
	public String getVehicleInfo(HttpServletRequest request,String platNumber,String carTypeCode,String frameNum,String stationCode){
		String response = null;
		try {
			String tmp = vehIsInfoService.sendXml(platNumber, carTypeCode, frameNum, stationCode, InterfaceInvokeType.TEST);
			response = ConvertUtils.decodeUTF8Xml(tmp);
		} catch (Exception e) {
			e.printStackTrace();
			response = e.getMessage();
		}
		request.setAttribute("platNumber", platNumber);
		request.setAttribute("carTypeCode", carTypeCode);
		request.setAttribute("frameNum", frameNum);
		request.setAttribute("stationCode", stationCode);
		request.setAttribute("responseXML", response);
		request.setAttribute("requestXML", buildQueryXml(platNumber,carTypeCode,frameNum,stationCode));
		request.setAttribute("requestDate", DateUtils.getDateTime());
		return "/backend/webservice/vehicleInfo";
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
}	
