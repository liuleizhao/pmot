package com.cs.argument.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cs.argument.entity.IdType;
import com.cs.argument.service.IdTypeService;
import com.cs.mvc.dao.SqlCondition;
import com.cs.mvc.web.BaseController;
import com.github.pagehelper.PageInfo;

/**
 * 证件类型控制器
 * @ClassName:    IdTypeController
 * @Description:  
 * @Author        succ
 * @date          2017-11-22  上午10:32:50
 */
@Controller
@RequestMapping("/backend/argument/idType")
public class IdTypeController extends BaseController {
	
	@Autowired
	private IdTypeService idTypeService;
	
	/**
	 * @throws Exception
	 * @Description: 查询证件类型列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(HttpServletRequest request,IdType idType, Model model) throws Exception {
		SqlCondition sqlCondition = new SqlCondition();
		if(null != idType)
		{
			sqlCondition.addSingleCriterion("code = ", idType.getCode());
			sqlCondition.addLikeCriterion("name like ", idType.getName());
		}
		sqlCondition.addAscOrderbyColumn("CODE");
		PageInfo<IdType> pageView = idTypeService.findByCondition(sqlCondition,getCurrentPage(request),getCurrentPageSize(request));
		model.addAttribute("pageView", pageView);
		return "backend/argument/idType_list";
	}
	
	/**
	 * @Description: 添加证件类型页面
	 * @param model
	 * @return String
	 * @throws Exception
	 * @date 2016-10-31 下午12:12:33
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addUI(HttpServletRequest request,Model model) throws Exception {
		return "backend/argument/idType_add";
	}
	
	/**
	 * @Description: 执行添加的方法
	 * @param request
	 * @param ClientUser
	 * @param ids
	 * @param model
	 * @return String
	 * @throws Exception
	 * @date 2016-10-31 下午12:12:33
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(HttpServletRequest request, Model model, IdType idType)
			throws Exception {
		String code = idType.getCode();
		String name = idType.getName();
		if(StringUtils.isBlank(code) || StringUtils.isBlank(name)){
			addErrorMessage("证件类型代码和名称不能为空！");
			return addUI(request, model);
		}
		if(this.checkIdTypeCodeIsExisted(code)){
			addErrorMessage("证件类型代码已存在！");
			return addUI(request, model);
		}
		idTypeService.insert(idType);
		addMessage("添加证件类型信息【" + idType.getName() + "】成功！");
		return list(request,idType, model);
	}
	
	/**
	 * @Description: 跳转到修改页面
	 * @param model
	 * @return String
	 * @throws Exception
	 * @date 2016-10-31 下午12:12:33
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String editUI(String idTypeId, Model model) throws Exception {
		IdType idType = idTypeService.selectByPrimaryKey(idTypeId);
		model.addAttribute("idType", idType);
		return "backend/argument/idType_edit";
	}
	/**
	 * @Description: 执行修改的方法
	 * @param request
	 * @param ClientUser
	 * @param ids
	 * @param model
	 * @return String
	 * @throws Exception
	 * @date 2016-10-31 下午12:12:33
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String edit(HttpServletRequest request,Model model,IdType idType) throws Exception {
		IdType exsitedIdType = idTypeService.selectByPrimaryKey(idType.getId());
		if(null == exsitedIdType)
		{
			model.addAttribute("message","修改证件类型信息失败，未找到对应的证件信息！");
			return list(request, null, model);
		}
		String code = idType.getCode();
		String name = idType.getName();
		if(StringUtils.isBlank(code) || StringUtils.isBlank(name)){
			addErrorMessage("证件类型代码和名称不能为空！");
			return "backend/argument/idType_edit";
		}
		if(!code.equals(exsitedIdType.getCode()) && this.checkIdTypeCodeIsExisted(code)){
			idType.setCode(exsitedIdType.getCode());
			addErrorMessage("证件类型代码【"+code+"】已存在！");
			return "backend/argument/idType_edit";
		}
		idTypeService.updateByPrimaryKeySelective(idType);
		model.addAttribute("message", "修改证件类型信息【" + idType.getName() + "】成功！");
		return list(request,idType, model);
	}
	
	/**
	 * 删除
	 * @param carTypeId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	@ResponseBody
	public boolean delete(String idTypeId) throws Exception{
		if (StringUtils.isBlank(idTypeId) ||  null == idTypeService.selectByPrimaryKey(idTypeId)) {
			return false;
		}else {
			idTypeService.deleteByPrimaryKey(idTypeId);
			return true;
		}
	}
	
	/**
	 * 根据code判断是否存在
	 * @param code
	 * @return
	 * @throws Exception
	 * @Author        succ
	 * @date 2017-11-23 下午12:24:59
	 */
	@RequestMapping(value="/checkIdTypeCodeIsExisted")
	@ResponseBody
	public boolean checkIdTypeCodeIsExisted(String code) throws Exception{
		boolean result = false;
		if(StringUtils.isNotBlank(code) && null!=idTypeService.findByCode(code)){
			result = true;
		}
		return result;
	}
}
