package com.cs.webservice.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cs.mvc.dao.SqlCondition;
import com.cs.mvc.web.BaseController;
import com.cs.webservice.entity.InterfaceLog;
import com.cs.webservice.service.InterfaceLogService;
import com.github.pagehelper.PageInfo;

/**
 * @ClassName: InterfaceLogController.java
 * @Description: 接口日志控制器
 * @Author xuj
 * @date 2017-12-08 
 */
@Controller
@RequestMapping(value = "/backend/webservice/interfaceLog")
public class InterfaceLogController extends BaseController{
	
	@Autowired
	private InterfaceLogService interfaceLogService;
	
	private PageInfo<InterfaceLog> pageView;
	
	/**
	 * @throws Exception
	 * @Description: 查询接口列表
	 */
	@RequestMapping(value = "/list", method=RequestMethod.GET)
	public String list(HttpServletRequest request,@ModelAttribute("interfaceLog")
	InterfaceLog interfaceLog , Model model){
		SqlCondition sqlCondition = new SqlCondition();
		try {
			if(null != interfaceLog){
				sqlCondition.addSingleCriterion("jkid = ", interfaceLog.getJkid());
				sqlCondition.addSingleCriterion("ip = ", interfaceLog.getIp());
				sqlCondition.addLikeCriterion("request_Xml like ", interfaceLog.getRequestXml());//专门用于 like语句
				sqlCondition.addLikeCriterion("response_Xml like ", interfaceLog.getResponseXml());//专门用于 like语句
			}
			sqlCondition.addAscOrderbyColumn("jkid");
			sqlCondition.addDescOrderbyColumn("REQUEST_DATE");
			sqlCondition.addAscOrderbyColumn("ip");
			pageView = interfaceLogService.findByCondition(sqlCondition,getCurrentPage(request),12);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		model.addAttribute("pageView", pageView);
		return "/backend/webservice/interfaceLog_list";
	}
	
	@RequestMapping(value = "/view", method=RequestMethod.GET)
	public String view(HttpServletRequest request,@ModelAttribute("interfaceLog")
	InterfaceLog interfaceLog , Model model){
		try {
			interfaceLog = interfaceLogService.selectByPrimaryKey(interfaceLog.getId());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		model.addAttribute("interfaceLog", interfaceLog);
		return "/backend/webservice/interfaceLog_view";
	}
	
}
