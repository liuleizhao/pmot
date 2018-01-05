package com.cs.webservice.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cs.common.entityenum.Recordable;
import com.cs.mvc.dao.SqlCondition;
import com.cs.mvc.web.BaseController;
import com.cs.webservice.entity.InterfaceInfo;
import com.cs.webservice.service.InterfaceInfoService;
import com.github.pagehelper.PageInfo;

/**
 * @ClassName: InterfaceInfoController.java
 * @Description: 接口管理控制器
 * @Author xuj
 * @date 2017-11-01 下午5:02:25
 */
@Controller
@RequestMapping(value = "/backend/webservice/interfaceInfo")
public class InterfaceInfoController extends BaseController{
	
	@Autowired
	private InterfaceInfoService interfaceInfoService;
	
	private PageInfo<InterfaceInfo> pageView;
	
	/**
	 * @throws Exception
	 * @Description: 查询接口列表
	 */
	@RequestMapping(value = "/list", method=RequestMethod.GET)
	public String list(HttpServletRequest request,InterfaceInfo interfaceInfo , Model model){
		SqlCondition sqlCondition = new SqlCondition();
		try {
			if(null != interfaceInfo){
				sqlCondition.addSingleCriterion("jkid = ", interfaceInfo.getJkid());
				sqlCondition.addSingleCriterion("name = ", interfaceInfo.getName());
				sqlCondition.addLikeCriterion("method_Name like ", interfaceInfo.getMethodName());//专门用于 like语句
				sqlCondition.addLikeCriterion("CLASS_NAME like ", interfaceInfo.getClassName());//专门用于 like语句
			}
			sqlCondition.addAscOrderbyColumn("jkid");
			pageView = interfaceInfoService.findByCondition(sqlCondition,getCurrentPage(request),getCurrentPageSize(request));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		model.addAttribute("pageView", pageView);
		return "/backend/webservice/interfaceInfo_list";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addUI(HttpServletRequest request,Model model){
		List<Recordable> recordableList = Recordable.getAll();
		model.addAttribute("recordableList", recordableList);
		return "/backend/webservice/interfaceInfo_add";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addUI(HttpServletRequest request,InterfaceInfo interfaceInfo,
			Model model,String [] interfaceParameterIds){
		try {
			String jkId = interfaceInfo.getJkid();
			String name = interfaceInfo.getName();
			String className = interfaceInfo.getClassName();
			String methodName = interfaceInfo.getMethodName();
			if(StringUtils.isBlank(jkId) || StringUtils.isBlank(name)){
				addErrorMessage("接口编号和名称不能为空！");
				return addUI(request,model);
			}
			if(StringUtils.isBlank(className) || StringUtils.isBlank(methodName)){
				addErrorMessage("接口类名和方法名不能为空！");
				return addUI(request,model);
			}
			if(this.checkJkIdIsExisted(jkId)){
				addErrorMessage("接口编号已经存在！");
				return addUI(request,model);
			}
			interfaceInfoService.insert(interfaceInfo);
			model.addAttribute("message","添加接口成功！");
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage("添加接口信息失败！");
			return addUI(request,model);
		}
		model.asMap().remove("interfaceInfo");
		return list(request, null, model);
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String editUI(HttpServletRequest request,Model model,String interfaceInfoId){
		InterfaceInfo interfaceInfo = null;
		List<Recordable> recordableList = Recordable.getAll();
		try {
			interfaceInfo = interfaceInfoService.selectByPrimaryKey(interfaceInfoId);
			if(null == interfaceInfo){
				addErrorMessage("未找到对应的实体");
				return list(request,null, model);
			} 
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage("出现异常，请稍后再试！");
			return list(request,null, model);
		}
		model.addAttribute("interfaceInfo",interfaceInfo);
		model.addAttribute("recordableList", recordableList);
		return "/backend/webservice/interfaceInfo_edit";
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String edit(HttpServletRequest request,Model model,InterfaceInfo interfaceInfo){
		try {
			InterfaceInfo existInterfaceInfo = interfaceInfoService.selectByPrimaryKey(interfaceInfo.getId());
			if(null == existInterfaceInfo){
				addErrorMessage("不存在该接口信息！");
				return list(request,null,model);
			}
			String jkId = interfaceInfo.getJkid();
			String name = interfaceInfo.getName();
			String className = interfaceInfo.getClassName();
			String methodName = interfaceInfo.getMethodName();
			if(StringUtils.isBlank(jkId) || StringUtils.isBlank(name)){
				addErrorMessage("接口编号和名称不能为空！");
				return addUI(request,model);
			}
			if(StringUtils.isBlank(className) || StringUtils.isBlank(methodName)){
				addErrorMessage("接口类名和方法名不能为空！");
				return addUI(request,model);
			}
			if(!jkId.equals(existInterfaceInfo.getJkid()) && this.checkJkIdIsExisted(jkId)){
				addErrorMessage("接口编号【"+jkId+"】已经存在！");
				return addUI(request,model);
			}
			interfaceInfoService.updateByPrimaryKey(interfaceInfo);
			model.addAttribute("message","编辑接口成功！");
		} catch (Exception e) {
			model.addAttribute("interfaceInfo",interfaceInfo);
			model.addAttribute("message", "编辑接口信息失败！");
			return editUI(request,model,null);
		}
		return list(request, interfaceInfo, model);
	}
	
	/**
	 * 删除
	 * @param carTypeId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	@ResponseBody
	public boolean delete(String interfaceInfoId) throws Exception{
		if (StringUtils.isBlank(interfaceInfoId) ||  null == interfaceInfoService.selectByPrimaryKey(interfaceInfoId)) {
			return false;
		}else {
			interfaceInfoService.deleteByPrimaryKey(interfaceInfoId);
			return true;
		}
	}
	
	@RequestMapping(value="/checkJkIdIsExisted")
	@ResponseBody
	public boolean checkJkIdIsExisted(String jkid) throws Exception{
		boolean result = false;
		if(StringUtils.isNotBlank(jkid) && null!=interfaceInfoService.findByJkId(jkid)){
			result = true;
		}
		return result;
	}
}
