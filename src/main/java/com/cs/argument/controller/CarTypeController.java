package com.cs.argument.controller;


import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cs.argument.entity.CarType;
import com.cs.argument.service.CarTypeService;
import com.cs.mvc.dao.SqlCondition;
import com.cs.mvc.web.BaseController;
import com.github.pagehelper.PageInfo;
/**
 * @ClassName:    InsurerInfoController.java
 * @Description:  车辆类型信息控制器
 * @Author         
 * @date           
 */
@Controller
@RequestMapping(value = "/backend/argument/carType")
public class CarTypeController extends BaseController{
	
	private PageInfo<CarType> pageView;
	
	@Autowired
	private CarTypeService carTypeService;
	
	
	/**
	 * @throws Exception
	 * @Description: 查询车辆类型列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(HttpServletRequest request,CarType carType, Model model) throws Exception {
		SqlCondition sqlCondition = new SqlCondition();
		if(null != carType)
		{
			sqlCondition.addSingleCriterion("code = ", carType.getCode());
			sqlCondition.addSingleCriterion("name = ", carType.getName());
		}
		sqlCondition.addAscOrderbyColumn("CODE");
		pageView = carTypeService.findByCondition(sqlCondition,getCurrentPage(request),getCurrentPageSize(request));
		model.addAttribute("pageView", pageView);
		return "backend/argument/carType_list";
	}
	
	/**
	 * @Description: 跳转到添加车辆类型页面
	 * @param model
	 * @return String
	 * @throws Exception
	 * @date 2016-10-31 下午12:12:33
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addUI(HttpServletRequest request,Model model) throws Exception {
		return "backend/argument/carType_add";
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
	public String add(HttpServletRequest request, Model model, CarType carType)
			throws Exception {
		try {
			String code = carType.getCode();
			String name = carType.getName();
			if(StringUtils.isBlank(code) || StringUtils.isBlank(name)){
				addErrorMessage("车辆类型代码和名称不能为空！");
				return addUI(request,model);
			}
			if(this.checkCarTypeCodeIsExisted(code)){
				addErrorMessage("车辆类型代码已存在！");
				return addUI(request,model);
			}
			carTypeService.insert(carType);
			addMessage("添加车辆类型信息【" + carType.getName() + "】成功！");
			return list(request,carType, model);
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage("添加车辆类型信息【" + carType.getName() + "】失败！");
			return addUI(request,model);
		}
	}
	
	/**
	 * @Description: 跳转到修改页面
	 * @param model
	 * @return String
	 * @throws Exception
	 * @date 2016-10-31 下午12:12:33
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String editUI(String carTypeId, Model model) throws Exception {
		CarType carType = carTypeService.selectByPrimaryKey(carTypeId);
		model.addAttribute("carType", carType);
		return "backend/argument/carType_edit";
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
	public String edit(HttpServletRequest request,Model model,CarType carType) throws Exception {
		try {
			CarType currentCarType  = carTypeService.selectByPrimaryKey(carType.getId());
			if(null == currentCarType)
			{
				addErrorMessage("修改车辆类型信息失败，未找到对应的车辆类型信息！");
				return list(request, null, model);
			}
			String code = carType.getCode();
			String name = carType.getName();
			if(StringUtils.isBlank(code) || StringUtils.isBlank(name)){
				addErrorMessage("车辆类型代码和名称不能为空！");
				return "backend/argument/carType_edit";
			}
			if(!code.equals(currentCarType.getCode()) && this.checkCarTypeCodeIsExisted(code)){
				carType.setCode(currentCarType.getCode());
				addErrorMessage("车辆类型代码已存在！");
				return "backend/argument/carType_edit";
			}
			carTypeService.updateByPrimaryKeySelective(carType);
			addMessage("修改车辆类型信息【" + carType.getName() + "】成功！");
			return list(request,carType, model);
		}catch (Exception e) {
			 e.printStackTrace();
			 addErrorMessage("修改车辆类型【"+carType.getName()+"】失败！");
			 return editUI(carType.getId(), model);
		}
	}
	/**
	 * 删除
	 * @param carTypeId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	@ResponseBody
	public boolean delete(String carTypeId) throws Exception{
		if (StringUtils.isBlank(carTypeId) ||  null == carTypeService.selectByPrimaryKey(carTypeId)) {
			return false;
		}else {
			carTypeService.deleteByPrimaryKey(carTypeId);
			return true;
		}
	}
	
	/**
	 * 校验车辆类型代码是否存在
	 * @param code
	 * @return
	 * @throws Exception
	 * @Author        succ
	 * @date 2017-11-23 下午2:55:32
	 */
	@RequestMapping(value="/checkCarTypeCodeIsExisted")
	@ResponseBody
	public boolean checkCarTypeCodeIsExisted(String code) throws Exception{
		boolean result = false;
		if(StringUtils.isNotBlank(code) && null!=carTypeService.findByCode(code)){
			result = true;
		}
		return result;
	}
	
}
