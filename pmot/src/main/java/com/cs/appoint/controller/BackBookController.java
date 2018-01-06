package com.cs.appoint.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cs.appoint.entity.BookInfo;
import com.cs.appoint.service.BookInfoCommonService;
import com.cs.appoint.service.BookInfoService;
import com.cs.argument.entity.CarType;
import com.cs.argument.entity.IdType;
import com.cs.argument.entity.Station;
import com.cs.argument.service.CarTypeService;
import com.cs.argument.service.IdTypeService;
import com.cs.argument.service.StationService;
import com.cs.common.utils.DateUtil;
import com.cs.common.utils.IpUtil;
import com.cs.mvc.web.BaseController;
import com.cs.system.entity.User;

@Controller
@RequestMapping(value = "/backend/book")
public class BackBookController extends BaseController{
	
	private static Logger logger = LoggerFactory.getLogger(BackBookController.class);
	
	@Autowired
	private StationService stationService;
	@Autowired
	private CarTypeService carTypeService;
	@Autowired 
	private BookInfoService bookInfoService;
	@Autowired
	private IdTypeService idTypeService;
	@Autowired
	private BookInfoCommonService bookInfoCommonService;

	/**
	 * 进入绿色通道预约填写信息页面
	 * @param request
	 * @param bookInfo
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/greenBookUI", method = RequestMethod.GET)
	public String greenBookUI(HttpServletRequest request, Model model){
		User user = getBackendUser(request);
		if(StringUtils.isBlank(user.getStationId())){
			model.addAttribute("errorMessage", "该用户未绑定检测站信息，不能预约！");
		}
		return "/backend/book/green_inputUI";
	}
	
	/**
	 * 绿色通道预约
	 * @param request
	 * @param bookInfo
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/greenBook", method = RequestMethod.POST)
	public String greenBook(HttpServletRequest request,@ModelAttribute("bookInfo") BookInfo bookInfo, Model model){
		try {
			User user = getBackendUser(request);
			if(StringUtils.isNotBlank(user.getStationId()) && StringUtils.isNotBlank(bookInfo.getCarTypeId())){
				
				Station station = stationService.selectByPrimaryKey(user.getStationId());
				CarType ct = carTypeService.selectByPrimaryKey(bookInfo.getCarTypeId());
				IdType idType = idTypeService.selectByPrimaryKey(bookInfo.getIdTypeId());
				if(null == station || null == ct || null == idType){
					errorMessage = "未找到对应的实体！";
					model.addAttribute("errorMessage", errorMessage);
					return greenBookUI(request,model);
				}
				bookInfo.setBookDate(DateUtil.getDayStr(new Date()));
				bookInfo.setStationId(station.getId());
				//转大写
				bookInfo.setIdNumber(bookInfo.getIdNumber().trim().toUpperCase());
				bookInfo.setFrameNumber(bookInfo.getFrameNumber().trim().toUpperCase());
				if(StringUtils.isNotBlank(bookInfo.getPlatNumber())){
					bookInfo.setPlatNumber(bookInfo.getPlatNumber().trim().toUpperCase());
				}
				errorMessage =  bookInfoCommonService.unifiedValidate(bookInfo,station,"G");
				if(StringUtils.isNotBlank(errorMessage)){
					model.addAttribute("errorMessage", errorMessage);   
					return greenBookUI(request,model);
				}
				bookInfo.setStationName(station.getName());
				bookInfo.setCarTypeName(ct.getName());
				bookInfo.setIdTypeName(idType.getName());
				bookInfo.setUserName(user.getName());
				bookInfo.setUserId(user.getId());
				String requestIP = IpUtil.getClientIp(request);
				bookInfo.setRequestIp(requestIP);
				
				errorMessage = bookInfoService.insertBookInfo(bookInfo,"G");
				if(StringUtils.isNotBlank(errorMessage)){
					model.addAttribute("errorMessage", errorMessage);
                	return greenBookUI(request,model);
				}else
				{
					model.addAttribute("bookInfo", bookInfo);
					return "/backend/book/green_bookOk";
				}
			}else{
				errorMessage = "参数不全";
				model.addAttribute("errorMessage", errorMessage);
				return greenBookUI(request,  model);
			}
		} catch (Exception e) {
			e.printStackTrace();
			errorMessage = "系统繁忙:" +e.getMessage();
			logger.error("车牌号:"+bookInfo.getPlatNumber()+",车架号:"+bookInfo.getFrameNumber()
					+"的用户预约控制层出现异常："+"\n"+e.getMessage()+"\n"+ e.getStackTrace().toString());
			
			model.addAttribute("errorMessage", errorMessage);
			return greenBookUI(request,  model);
		}
	} 
	
	
}
