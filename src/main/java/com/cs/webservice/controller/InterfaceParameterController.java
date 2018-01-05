package com.cs.webservice.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cs.mvc.dao.SqlCondition;
import com.cs.mvc.web.BaseController;
import com.cs.webservice.entity.InterfaceParameter;
import com.cs.webservice.service.InterfaceParameterService;
import com.github.pagehelper.PageInfo;

/**
 * @ClassName: InterfaceParameterController.java
 * @Description: 接口参数控制器
 * @Author xuj
 * @date 2017-11-02 
 */
@Controller
@RequestMapping(value = "/backend/webservice/interfaceParameter")
public class InterfaceParameterController extends BaseController{

	@Autowired
	private InterfaceParameterService interfaceParameterService;
	
	private PageInfo<InterfaceParameter> pageView;
	/**
	 * @throws Exception
	 * @Description: 查询接口参数列表
	 */
	@RequestMapping(value = "/list", method=RequestMethod.GET)
	public String list(HttpServletRequest request,InterfaceParameter interfaceParameter, Model model) throws Exception{
		SqlCondition sqlCondition = new SqlCondition();
		if(null != interfaceParameter){
			sqlCondition.addLikeCriterion("name like ", interfaceParameter.getName());//专门用于 like语句
		}
		sqlCondition.addAscOrderbyColumn("name");
		pageView = interfaceParameterService.findByCondition(sqlCondition,getCurrentPage(request),getCurrentPageSize(request));
		model.addAttribute("pageView", pageView);
		return "/backend/webservice/interfaceParameter_list";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addUI(HttpServletRequest request,Model model){
		return "/backend/webservice/interfaceParameter_add";
	}
	
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(HttpServletRequest request,InterfaceParameter interfaceParameter,Model model) throws Exception{
		try {
			String name = interfaceParameter.getName();
			String type = interfaceParameter.getType();
			if(StringUtils.isBlank(name) || StringUtils.isBlank(type)){
				addErrorMessage("参数名称和参数类型不能为空！");
				return addUI(request,model);
			}
			if(checkNameAndTypeIsExisted(name,type)){
				addErrorMessage("该类型的参数名称已经存在！");
				return addUI(request,model);
			}
			interfaceParameterService.insert(interfaceParameter);
			addMessage("添加接口参数【"+interfaceParameter.getName()+"】成功！");
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage("添加接口参数【"+interfaceParameter.getName()+"】失败！");
			return addUI(request,model);
		}
		return list(request, interfaceParameter, model);
	}
	
	@RequestMapping(value = "/edit", method=RequestMethod.GET)
	public String editUI(HttpServletRequest request,String interfaceParameterId, Model model) throws Exception{
		InterfaceParameter interfaceParameter = interfaceParameterService.selectByPrimaryKey(interfaceParameterId);
		if(interfaceParameter == null){
			addErrorMessage("未找到该参数信息！");
			return list(request, null, model);
		}
		model.addAttribute("interfaceParameter", interfaceParameter);
		return "/backend/webservice/interfaceParameter_edit";
	}
	
	@RequestMapping(value = "/edit", method=RequestMethod.POST)
	public String edit(HttpServletRequest request,InterfaceParameter interfaceParameter, Model model) throws Exception{
		try {
			InterfaceParameter existPar = interfaceParameterService.selectByPrimaryKey(interfaceParameter.getId());
			if(existPar == null){
				addErrorMessage("未找到该参数信息！");
				return list(request, null, model);
			}
			String name = interfaceParameter.getName();
			String type = interfaceParameter.getType();
			String id = interfaceParameter.getId();
			if(StringUtils.isBlank(name) || StringUtils.isBlank(type)){
				addErrorMessage("参数名称和参数类型不能为空！");
				return "/backend/webservice/interfaceParameter_edit";
			}
			
			if(!id.equals(existPar.getId()) && checkNameAndTypeIsExisted(name,type)){
				addErrorMessage("该类型的参数名称已经存在！");
				return "/backend/webservice/interfaceParameter_edit";
			}
			
			interfaceParameterService.updateByPrimaryKey(interfaceParameter);
			addMessage("修改接口参数【"+interfaceParameter.getName()+"】成功！");
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage("出现异常，请稍后再试！");
			return list(request, interfaceParameter, model);
		}
		return list(request, interfaceParameter, model);
	}
	
	/**
	 * 删除
	 * @param interfaceParameterId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	@ResponseBody
	public boolean delete(String interfaceParameterId) throws Exception{
		if (StringUtils.isBlank(interfaceParameterId) ||  null == interfaceParameterService.selectByPrimaryKey(interfaceParameterId)) {
			return false;
		}else {
			interfaceParameterService.deleteByPrimaryKey(interfaceParameterId);
			return true;
		}
	}
	
	public boolean checkNameAndTypeIsExisted(String name,String type) throws Exception{
		boolean result = false;
		if(StringUtils.isNotBlank(name) && StringUtils.isNotBlank(type) && null!=interfaceParameterService.findByNameAndType(name, type)){
			result = true;
		}
		return result;
	}
}
