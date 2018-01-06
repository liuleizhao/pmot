package com.cs.appoint.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cs.appoint.entity.PlatNumberWhiteList;
import com.cs.appoint.service.PlatNumberWhiteListService;
import com.cs.argument.entity.CarType;
import com.cs.argument.entity.Station;
import com.cs.argument.service.CarTypeService;
import com.cs.argument.service.StationService;
import com.cs.common.constant.CacheConstant;
import com.cs.common.utils.CacheUtil;
import com.cs.mvc.dao.SqlCondition;
import com.cs.mvc.web.BaseController;
import com.cs.system.entity.User;
import com.github.pagehelper.PageInfo;

/**
 * 车牌，车架白名单控制器
 * @ClassName 	PlatNumberWhiteListController
 * @Description
 * @Author 		xj
 * @Date 		2018-01-06	 
 */
@Controller
@RequestMapping("/backend/appoint/platNumberWhiteList")
public class PlatNumberWhiteListController extends BaseController{
	
	@Autowired
	private StationService stationService;
	
	@Autowired
	private CarTypeService carTypeService;
	
	@Autowired
	private PlatNumberWhiteListService platNumberWhiteListService;
	
	private PageInfo<PlatNumberWhiteList> pageView;
	
	/**
	 * @throws Exception
	 * @Description: 查询车辆信息白名单列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(HttpServletRequest request,@ModelAttribute("platNumberWhiteList") PlatNumberWhiteList platNumberWhiteList,Model model){
		SqlCondition sqlCondition = new SqlCondition();
		try {
			User user = (User) CacheUtil.getCacheObject(CacheConstant.BACKEND_USER_CACHE, request);
			if(null != platNumberWhiteList){
				sqlCondition.addSingleCriterion("PLAT_NUMBER = ", platNumberWhiteList.getPlatNumber());
				sqlCondition.addSingleCriterion("FRAME_NUMBER = ", platNumberWhiteList.getFrameNumber());
			}
			// 根据检测站查询
			sqlCondition.addSingleCriterion("station_ID = ", user.getStationId());
			sqlCondition.addDescOrderbyColumn("create_date");
			pageView = platNumberWhiteListService.findByCondition(sqlCondition,getCurrentPage(request),12);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("pageView", pageView);
		return  "backend/appoint/platNumberWhiteList_list";
	}
	
	/**
	 * @throws Exception
	 * @Description: 添加车辆信息白名单UI
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addUI(HttpServletRequest request,Model model){
		User user = getBackendUser(request);
		if(StringUtils.isBlank(user.getStationId())){
			model.addAttribute("errorMessage", "该用户未绑定检测站信息，不能预约！");
		}
		return  "backend/appoint/platNumberWhiteList_add";
	}
	
	/**
	 * @throws Exception
	 * @Description: 执行添加车辆信息白名单
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(HttpServletRequest request,PlatNumberWhiteList platNumberWhiteList,Model model){
		User user = getBackendUser(request);
		if(StringUtils.isNotBlank(platNumberWhiteList.getCarTypeId())){
			try {
				if(StringUtils.isBlank(user.getStationId())){
					model.addAttribute("errorMessage", "该用户未绑定检测站信息，不能预约！");
					return addUI(request,model);
				}
				CarType ct = carTypeService.selectByPrimaryKey(platNumberWhiteList.getCarTypeId());
				Station station = stationService.selectByPrimaryKey(user.getStationId());
				if(null == station || null == ct ){
					errorMessage = "未找到对应的实体！";
					model.addAttribute("errorMessage", errorMessage);
					return addUI(request,model);
				}
				platNumberWhiteList.setStationId(station.getId());
				platNumberWhiteList.setStationName(station.getName());
				platNumberWhiteList.setCarTypeCode(ct.getCode());
				platNumberWhiteList.setCarTypeName(ct.getName());
				platNumberWhiteList.setCreateDate(new Date());
				platNumberWhiteListService.insert(platNumberWhiteList);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else
		{
			errorMessage = "参数不全";
			model.addAttribute("errorMessage", errorMessage);
			return addUI(request,model);
		}
		return list(request,null, model);
	}
	
	
	/**
	 * @throws Exception
	 * @Description: 修改车辆信息白名单UI
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String editUI(HttpServletRequest request,PlatNumberWhiteList platNumberWhiteList,Model model){
		try {
			platNumberWhiteList = platNumberWhiteListService.selectByPrimaryKey(platNumberWhiteList.getId());
			if(null == platNumberWhiteList){
				model.addAttribute("errorMessage", "未找到对应的车辆信息！");
				return list(request, null, model);
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "系统出现异常："+e.getMessage());
			return list(request, null, model);
		}
		model.addAttribute("platNumberWhiteList", platNumberWhiteList);
		return  "backend/appoint/platNumberWhiteList_edit";
	}
	
	/**
	 * @throws Exception
	 * @Description: 执行修改车辆信息白名单
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String edit(HttpServletRequest request,PlatNumberWhiteList platNumberWhiteList,Model model){
		User user = getBackendUser(request);
		try {
			CarType ct = carTypeService.selectByPrimaryKey(platNumberWhiteList.getCarTypeId());
			Station station = stationService.selectByPrimaryKey(user.getStationId());
			if(null == station || null == ct ){
				errorMessage = "未找到对应的实体！";
				model.addAttribute("errorMessage", errorMessage);
				return editUI(request,platNumberWhiteList,model);
			}
			platNumberWhiteList.setStationId(station.getId());
			platNumberWhiteList.setStationName(station.getName());
			platNumberWhiteList.setCarTypeCode(ct.getCode());
			platNumberWhiteList.setCarTypeName(ct.getName());
			platNumberWhiteList.setCreateDate(new Date());
			platNumberWhiteListService.updateByPrimaryKey(platNumberWhiteList);
		} catch (Exception e) {
			e.getStackTrace();
		}
		return list(request,null, model);
	}
}
