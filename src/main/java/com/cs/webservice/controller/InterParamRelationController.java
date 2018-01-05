package com.cs.webservice.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cs.mvc.dao.SqlCondition;
import com.cs.mvc.web.BaseController;
import com.cs.webservice.entity.InterParamRelation;
import com.cs.webservice.entity.InterfaceInfo;
import com.cs.webservice.entity.InterfaceParameter;
import com.cs.webservice.service.InterParamRelationService;
import com.cs.webservice.service.InterfaceInfoService;
import com.cs.webservice.service.InterfaceParameterService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * @ClassName: InterParamRelationController.java
 * @Description: 接口参数关系控制器
 * @Author xuj
 * @date 2017-11-06 
 */
@Controller
@RequestMapping(value = "/backend/webservice/interParamRelation")
public class InterParamRelationController extends BaseController{
	
	@Autowired
	private InterfaceParameterService interfaceParameterService;
	
	@Autowired
	private InterfaceInfoService interfaceInfoService;
	
	@Autowired
	private InterParamRelationService interParamRelationService;
	
	private List<InterParamRelation> paramRelations;
	
	@RequestMapping(value = "/list", method=RequestMethod.GET)
	public String list(HttpServletRequest request, Model model,
			@ModelAttribute("interParamRelation") InterParamRelation paramRelation)
	{
		InterfaceInfo interfaceInfo = null;
		try {
			interfaceInfo = interfaceInfoService.selectByPrimaryKey(paramRelation.getInterfaceInfoId());
			SqlCondition sqlCondition = new SqlCondition();
			sqlCondition.addSingleCriterion("INTERFACEINFO_ID = ", paramRelation.getInterfaceInfoId()); 
			sqlCondition.addAscOrderbyColumn("ORDER_INDEX");
			paramRelations = interParamRelationService.findByCondition(sqlCondition);
			
			List<InterfaceParameter> interfaceParameters = interfaceParameterService.findAllData();
			model.addAttribute("interfaceParameters", interfaceParameters);
		} catch (Exception e) {
			 e.getStackTrace();
		}
		model.addAttribute("interfaceInfo", interfaceInfo);
		model.addAttribute("paramRelations", paramRelations);
		return "/backend/webservice/interParamRelation_edit";
	}
	
	@RequestMapping(value = "/editParameter", method=RequestMethod.POST)
	public String editParameter(HttpServletRequest request,InterParamRelation paramRelation,
			Model model,String params){
		try {
			// 再次查询接口看是否存在
			InterfaceInfo interfaceInfo = interfaceInfoService.selectByPrimaryKey(paramRelation.getInterfaceInfoId());
			if(null != interfaceInfo){
				params = StringEscapeUtils.unescapeXml(params);
				//转换
				ObjectMapper mapper = new ObjectMapper();
				List<InterParamRelation> relationVOs = null;
				try {
					relationVOs = mapper.readValue(params,new TypeReference<List<InterParamRelation>>() { });
				}catch (Exception e) {
					model.addAttribute("message", "数据转化失败！"); // 
					e.printStackTrace();
				}
				System.out.println(relationVOs);
				int addCount = interParamRelationService.add(interfaceInfo, relationVOs);
				model.addAttribute("message", "修改成功！");
			}
		} catch (Exception e) {
			model.addAttribute("message", "修改失败"+e.getMessage()+"！");
			e.printStackTrace();
		}
		return list(request, model, paramRelation);
	}
}
