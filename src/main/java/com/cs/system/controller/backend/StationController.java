package com.cs.system.controller.backend;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cs.argument.entity.District;
import com.cs.argument.entity.Station;
import com.cs.argument.service.DistrictService;
import com.cs.argument.service.StationService;
import com.cs.common.entityenum.StationStatus;
import com.cs.common.entityenum.VehicleCharacter;
import com.cs.mvc.dao.SqlCondition;
import com.cs.mvc.web.BaseController;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;

/**
 * @ClassName:    StationInfoController.java
 * @Description:  检测站信息控制器
 * @Author        xuj
 * @date          2016-11-03 下午5:02:25
 */
@Controller
@RequestMapping(value = "/backend/system/station")
public class StationController  extends BaseController{
	
	@Autowired
	private DistrictService districtService;
	
	@Autowired
	private StationService stationService;
	
	private PageInfo<Station> pageView;
	
	/**
	 * @throws Exception
	 * @Description: 查询检测站列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(HttpServletRequest request,Station station, Model model) throws Exception {
		SqlCondition sqlCondition = new SqlCondition();
		if(station != null){
			sqlCondition.addSingleCriterion("code = ", station.getCode());
			sqlCondition.addLikeCriterion("name like ", station.getName());
		}
		sqlCondition.addDescOrderbyColumn("create_date");
		pageView = stationService.findByCondition(sqlCondition,getCurrentPage(request),getCurrentPageSize(request));
		model.addAttribute("pageView", pageView);
		return "backend/system/stationInfo_list";
	}

	/**
	 * @Description: 跳转到添加页面
	 * @param model
	 * @return String
	 * @throws Exception
	 * @date 2016-10-31 下午12:12:33
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addUI(HttpServletRequest request,Model model) throws Exception {
		List<District> districtList = districtService.findAllData();
		List<VehicleCharacter> vehicleCharacterList = VehicleCharacter.getAll();
		model.addAttribute("districtList", districtList);
		model.addAttribute("vehicleCharacterList", vehicleCharacterList);
		return "backend/system/stationInfo_add";
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
	public String add(HttpServletRequest request, Model model, Station station)throws Exception {
		try {
			String code = station.getCode();
			String name = station.getName();
			if(StringUtils.isBlank(code) ||StringUtils.isBlank(name))
			{
				addErrorMessage("代号和名称不能为空！");
				return addUI(request, model);
			} 
			
			if(this.checkCodeExisted(code)){
				addErrorMessage("检测站代号已存在！");
				return addUI(request, model);
			}
			if(this.checkNameExisted(name)){
				addErrorMessage("检测站名称已存在！");
				return addUI(request, model);
			}
			stationService.insert(station);
			addMessage("添加检测站信息【" + station.getName() + "】成功！");
			return list(request,station, model);
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage("添加检测站信息【" + station.getName() + "】失败！");
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
	public String editUI(String stationId, Model model) throws Exception {
		Station stationInfo = stationService.selectByPrimaryKey(stationId);
		List<StationStatus> statusList = StationStatus.getAll();
		List<District> districtList = districtService.findAllData();
		List<VehicleCharacter> vehicleCharacterList = VehicleCharacter.getAll();
		model.addAttribute("districtList", districtList);
		model.addAttribute("statusList", statusList);
		model.addAttribute("vehicleCharacterList", vehicleCharacterList);
		model.addAttribute("stationInfoVO", stationInfo);
		return "backend/system/stationInfo_edit";
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
	public String edit(HttpServletRequest request,Model model,Station station,
			String [] vehicleCharacterIds) throws Exception {
		try {
			// 获取当前要修改的用户
			Station currentstationInfo = stationService.selectByPrimaryKey(station.getId());
			if(null == currentstationInfo)
			{
				model.addAttribute("message","修改检车站信息失败，未找到对应的检车站信息！");
				return list(request,null, model);
			}
			
			String code = station.getCode();
			String name = station.getName();
			if(StringUtils.isBlank(code) ||StringUtils.isBlank(name))
			{
				addErrorMessage("代号和名称不能为空！");
				return addUI(request, model);
			} 
			if(!code.equals(currentstationInfo.getCode()) && this.checkCodeExisted(code)){
				addErrorMessage("检测站代号【"+station.getCode()+"】已存在！");
				return editUI(station.getId(), model);
			}
			if(!name.equals(currentstationInfo.getName()) && this.checkNameExisted(name)){
				addErrorMessage("检测站名称【"+station.getName()+"】已存在！");
				return editUI(station.getId(), model);
			}
			String vehicleCharacters = "";
			if(null != vehicleCharacterIds && vehicleCharacterIds.length > 0){
				for (int i = 0; i < vehicleCharacterIds.length; i++) {
					vehicleCharacters+= vehicleCharacterIds[i]+",";
				}
				vehicleCharacters = vehicleCharacters.substring(0,vehicleCharacters.length() - 1); // 去掉最后一个“,”
			}
			station.setVehicleCharacters(vehicleCharacters);
			stationService.updateByPrimaryKeySelective(station);
			addMessage("修改检测站【"+station.getName()+"】成功！");
			return list(request,station, model);
		}catch (Exception e) {
			 e.printStackTrace();
			 addErrorMessage("修改检测站【"+station.getName()+"】失败！");
			 return editUI(station.getId(), model);
		}
	}
	
	/**
	 * 删除
	 * @param stationId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	@ResponseBody
	public boolean delete(String stationId) throws Exception{
		if (StringUtils.isBlank(stationId) ||  null == stationService.selectByPrimaryKey(stationId)) {
			return false;
		}else {
			stationService.deleteByPrimaryKey(stationId);
			return true;
		}
	}
	
	@RequestMapping(value = "/checkCodeExisted")
	@ResponseBody
	public boolean checkCodeExisted(String code) throws Exception{
		boolean result = false;
		if(StringUtils.isNotBlank(code) && null!=stationService.findByCode(code)){
			result = true;
		}
		return result;
	}
	
	@RequestMapping(value = "/checkNameExisted")
	@ResponseBody
	public boolean checkNameExisted(String name) throws Exception{
		boolean result = false;
		if(StringUtils.isNotBlank(name)){
			name = java.net.URLDecoder.decode(java.net.URLDecoder.decode(name, "UTF-8"), "UTF-8");
			if(null!=stationService.findByName(name)){
				result = true;
			}
		}
		return result;
	}
	/**
	 * @Description: 预约量设置列表
	 * @param model
	 * @return String
	 * @throws Exception
	 * @date 2017-12-19  下午12:12:33
	 */
	@RequestMapping(value = "/appointCountUI", method = RequestMethod.GET)
	public String appointCountUI(HttpServletRequest request,Model model,@ModelAttribute("station") Station station) throws Exception {
		SqlCondition sqlCondition = new SqlCondition();
		if(station != null){
			sqlCondition.addSingleCriterion("code = ", station.getCode());
			sqlCondition.addLikeCriterion("name like ", station.getName());
		}
		sqlCondition.addAscOrderbyColumn("ORDER_NUM");
		sqlCondition.addDescOrderbyColumn("create_date");
		pageView = stationService.findByCondition(sqlCondition,getCurrentPage(request),getCurrentPageSize(request));
		model.addAttribute("pageView", pageView);
		return "backend/system/stationInfo_appointCountlist";
	}
	
	/**
	 * @Description: 封装数据，批量修改最大预约量
	 * @param model
	 * @return String
	 * @throws Exception
	 * @date 2017-12-19 下午12:12:33
	 */
	@RequestMapping(value = "/editAppointCount", method = RequestMethod.POST)
	public String editAppointCount(HttpServletRequest request, Model model,String stationInfos) throws Exception {
		if(StringUtils.isNotBlank(stationInfos)){
			stationInfos = StringEscapeUtils.unescapeXml(stationInfos);
			//转换
			ObjectMapper mapper = new ObjectMapper();
			List<Station> stations = null;
			try {
				stations = mapper.readValue(stationInfos,new TypeReference<List<Station>>() { });
			}catch (Exception e) {
				model.addAttribute("message", "数据转化失败！");
				e.printStackTrace();
			}
			stationService.batchUpdateMaxNumber(stations);
			addMessage("修改成功！");
		}else
		{
			addErrorMessage("请选择监测站或者设置预约量！");
		}
		return appointCountUI(request, model,null);
	}
	
}
