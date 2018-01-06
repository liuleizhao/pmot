package com.cs.argument.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cs.argument.entity.BusBlackListConfig;
import com.cs.argument.service.BusBlackListConfigService;
import com.cs.common.entityenum.BookOperation;
import com.cs.common.entityenum.RecordType;
import com.cs.mvc.dao.SqlCondition;
import com.cs.mvc.web.BaseController;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping(value = "/backend/blackListConfig")
public class BusBlackListConfigController extends BaseController{

	@Autowired
	private BusBlackListConfigService blackListConfigService;
	private PageInfo<BusBlackListConfig> pageView;
	private String message = "";
	private List<BookOperation> bookOperations = BookOperation.getAll();
	private List<RecordType> recordTypes = RecordType.getAll();
	
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(HttpServletRequest request,@ModelAttribute("busBlackListConfig") 
		BusBlackListConfig busBlackListConfig,Model model){
		try {
			SqlCondition sqlCondition = new SqlCondition();
			sqlCondition.addSingleCriterion("OPERATION_TYPE =",busBlackListConfig.getOperationType());
			sqlCondition.addSingleCriterion("RECORD_TYPE =",busBlackListConfig.getRecordType());
			sqlCondition.addAscOrderbyColumn("OPERATION_TYPE");
			sqlCondition.addAscOrderbyColumn("RECORD_TYPE");
			pageView = blackListConfigService.findByCondition(sqlCondition,getCurrentPage(request),12);
		} catch (Exception e) {
			e.printStackTrace();
			message = "系统繁忙，请稍后再试";
		}
		
		model.addAttribute("bookOperations", bookOperations);
		model.addAttribute("recordTypes", recordTypes);
		model.addAttribute("pageView", pageView);
		model.addAttribute("message", message);
		
		return "backend/argument/busBlackListConfig_list";
	}
	
	@RequestMapping(value = "/addUI", method = RequestMethod.GET)
	public String addUI(HttpServletRequest request,@ModelAttribute("busBlackListConfig") 
		BusBlackListConfig busBlackListConfig,Model model){
		
		model.addAttribute("bookOperations", bookOperations);
		model.addAttribute("recordTypes", recordTypes);
		return "backend/argument/busBlackListConfig_add";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(HttpServletRequest request,@ModelAttribute("busBlackListConfig") 
		BusBlackListConfig busBlackListConfig,Model model){
		try {
			if(busBlackListConfig != null){
				blackListConfigService.insert(busBlackListConfig);
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "系统繁忙，请稍后再试";
		}
		
		model.addAttribute("bookOperations", bookOperations);
		model.addAttribute("recordTypes", recordTypes);
		model.addAttribute("message", message);
		return list(request,busBlackListConfig,model);
	}
	
	@RequestMapping(value = "/editUI", method = RequestMethod.GET)
	public String editUI(HttpServletRequest request,String configId,Model model){
		BusBlackListConfig blackListConfig = null; 
		try {
			if(StringUtils.isNotBlank(configId)){
				blackListConfig =  blackListConfigService.selectByPrimaryKey(configId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "系统繁忙，请稍后再试";
		}
		
		model.addAttribute("bookOperations", bookOperations);
		model.addAttribute("recordTypes", recordTypes);
		model.addAttribute("blackListConfig", blackListConfig);
		model.addAttribute("message", message);
		return "backend/argument/busBlackListConfig_edit";
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String edit(HttpServletRequest request,@ModelAttribute("busBlackListConfig") 
	BusBlackListConfig busBlackListConfig,Model model){
		try {
			if(busBlackListConfig != null){
				blackListConfigService.updateByPrimaryKey(busBlackListConfig);
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "系统繁忙，请稍后再试";
		}
		
		model.addAttribute("bookOperations", bookOperations);
		model.addAttribute("recordTypes", recordTypes);
		model.addAttribute("message", message);
		return list(request,busBlackListConfig,model);
	}
	
	@RequestMapping(value = "/delete", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> delete(String configId){
		Map<String, Object> result = new HashMap<String, Object>();
		errorMessage = "";
		try {
			if(StringUtils.isNotBlank(configId)){
				blackListConfigService.deleteByPrimaryKey(configId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			errorMessage = "系统繁忙，请稍后再试";
		}
		result.put("errorMessage", errorMessage);
		return result;
		
	}
}
