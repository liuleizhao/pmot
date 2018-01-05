package com.cs.webservice.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cs.argument.service.StationService;
import com.cs.common.entityenum.InterfaceControlState;
import com.cs.mvc.dao.SqlCondition;
import com.cs.mvc.web.BaseController;
import com.cs.webservice.entity.InterfaceControlDetail;
import com.cs.webservice.entity.InterfaceControlGeneral;
import com.cs.webservice.entity.InterfaceInfo;
import com.cs.webservice.service.InterfaceControlDetailService;
import com.cs.webservice.service.InterfaceControlGeneralService;
import com.cs.webservice.service.InterfaceInfoService;
import com.github.pagehelper.PageInfo;
/**
 * 接口用户权限
 * @author HYL
 *
 */
@Controller
@RequestMapping(value = "/backend/webservice/interfaceControlGeneral")
public class InterfaceControlGeneralController extends BaseController{

	@Autowired
	private InterfaceControlGeneralService interfaceControlGeneralService;
	
	@Autowired
	private InterfaceControlDetailService interfaceControlDetailService;
	
	@Autowired
	private InterfaceInfoService interfaceInfoService;
	
	@Autowired
	private StationService stationService;
	
	private PageInfo<InterfaceControlGeneral> pageView;
	/**
	 * 列表页
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/list", method=RequestMethod.GET)
	public String list(HttpServletRequest request,InterfaceControlGeneral interfaceControlGeneral , Model model) throws Exception{
		SqlCondition sqlCondition = new SqlCondition();
		if(interfaceControlGeneral != null){
			String ips = interfaceControlGeneral.getIps();
			if(StringUtils.isNotBlank(ips)){
				if(!ips.startsWith("$")){
					ips = "$"+ips;
				}
				if(!ips.endsWith("$")){
					ips = ips+"$";
				}
				sqlCondition.addLikeCriterion("ips like ", ips);
			}
			sqlCondition.addLikeCriterion("station_name like ", interfaceControlGeneral.getStationName());
		}
		pageView = interfaceControlGeneralService.findByCondition(sqlCondition,getCurrentPage(request),12);
		model.addAttribute("pageView", pageView);
		return "/backend/webservice/interctrlgenr_list";
	}
	
	@RequestMapping(value = "/add", method=RequestMethod.GET)
	public String addUI(HttpServletRequest request,InterfaceControlGeneral interfaceControlGeneral , Model model) throws Exception{
		//没有配置过接口权限的所有检测站
		model.addAttribute("stationList", stationService.findNotExistInterfaceControl());
		return "/backend/webservice/interctrlgenr_add";
	}
	
	@RequestMapping(value = "/add", method=RequestMethod.POST)
	public String add(HttpServletRequest request,InterfaceControlGeneral interfaceControlGeneral, Model model) throws Exception{
		try {
			if(interfaceControlGeneral !=  null){
				String stationId = interfaceControlGeneral.getStationId();
				String stationName = interfaceControlGeneral.getStationName();
				String serialNum = interfaceControlGeneral.getSerialNumber();
				String ips = interfaceControlGeneral.getIps();
				if(StringUtils.isBlank(stationId) || StringUtils.isBlank(stationName)){
					addErrorMessage("添加失败，检测站不能为空！");
					return addUI(request,interfaceControlGeneral,model);
				}
				if(StringUtils.isBlank(serialNum) || StringUtils.isBlank(ips)){
					addErrorMessage("添加失败，序列号和IP地址不能为空！");
					return addUI(request,interfaceControlGeneral,model);
				}
				//是否已经配置
				InterfaceControlGeneral existGeneral = interfaceControlGeneralService.findByStationId(stationId);
				if(existGeneral != null){
					addErrorMessage("添加失败，检测站【"+existGeneral.getStationName()+"】已配置");
					return addUI(request,interfaceControlGeneral,model);
				}
				//校验检测站序列号是否唯一
				existGeneral = interfaceControlGeneralService.findBySerialNumber(serialNum);
				if(existGeneral != null){
					addErrorMessage("添加失败，序列号【"+serialNum+"】已被使用");
					return addUI(request,interfaceControlGeneral,model);
				}
				model.asMap().remove("interfaceControlGeneral");
				interfaceControlGeneralService.insert(interfaceControlGeneral);
			}
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage("系统繁忙，请稍后再试");
			return "/backend/webservice/interctrlgenr_add";
		}
		return list(request,null,model);
		
	}
	
	@RequestMapping(value = "/editUI", method=RequestMethod.GET)
	public String editUI(HttpServletRequest request,String generalId, Model model) throws Exception{
		InterfaceControlGeneral general = interfaceControlGeneralService.selectByPrimaryKey(generalId);
		model.addAttribute("interfaceControlGeneral", general);
		return "/backend/webservice/interctrlgenr_edit";
	}
	
	@RequestMapping(value = "/edit", method=RequestMethod.POST)
	public String edit(HttpServletRequest request,InterfaceControlGeneral interfaceControlGeneral, 
			Model model) throws Exception{
		String id = interfaceControlGeneral.getId();
		String serialNum = interfaceControlGeneral.getSerialNumber();
		String ips = interfaceControlGeneral.getIps();
		if(StringUtils.isBlank(id)){
			addErrorMessage("修改失败！");
			return list(request,null,model);
		}
		if(StringUtils.isBlank(serialNum) || StringUtils.isBlank(ips)){
			addErrorMessage("修改失败，序列号和IP地址不能为空！");
			return editUI(request,interfaceControlGeneral.getId(),model);
		}
		InterfaceControlGeneral existGeneral = interfaceControlGeneralService.selectByPrimaryKey(interfaceControlGeneral.getId());
		if(!serialNum.equals(existGeneral.getSerialNumber()) && interfaceControlGeneralService.findBySerialNumber(serialNum)!=null){
			addErrorMessage("修改失败，序列号【"+serialNum+"】已被使用");
			return "/backend/webservice/interctrlgenr_edit";
		}
		existGeneral.setIps(ips);
		existGeneral.setSerialNumber(serialNum);
		existGeneral.setState(interfaceControlGeneral.getState());
		interfaceControlGeneralService.updateByPrimaryKeySelective(existGeneral);
		model.asMap().remove("interfaceControlGeneral");
		return list(request,null,model);
	}
	
	@RequestMapping(value = "/updateState", method=RequestMethod.GET)
	@ResponseBody
	public boolean updateState(String generalId) throws Exception{
		boolean result = true;
		InterfaceControlGeneral  existGeneral = interfaceControlGeneralService.selectByPrimaryKey(generalId);
		if(existGeneral != null){
			if(InterfaceControlState.SYZ.equals(existGeneral.getState())){
				existGeneral.setState(InterfaceControlState.TY);
			}else {
				existGeneral.setState(InterfaceControlState.SYZ);
			}
			interfaceControlGeneralService.updateByPrimaryKey(existGeneral);
		}else{
			addErrorMessage("更新状态失败，传入参数为空！");
			result = false;
		}
		return result;
	}
	/**
	 * 为账户配接口权限UI
	 * @param request
	 * @param generalId
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/editDetailUI", method=RequestMethod.GET)
	public String editDetailUI(HttpServletRequest request,String generalId, Model model) throws Exception{
		List<InterfaceInfo> allInterfaces = interfaceInfoService.findAllOrderByJkIdAsc();
		List<InterfaceControlDetail> existDetails = interfaceControlDetailService.findByGeneralId(generalId);
		model.addAttribute("allInterfaces", allInterfaces);
		model.addAttribute("existDetails", existDetails);
		model.addAttribute("generalId", generalId);
		return "/backend/webservice/interctrlgenr_editDetailUI";
	}
	
	@RequestMapping(value = "/editDetail", method=RequestMethod.POST)
	public String editDetail( HttpServletRequest request,String[] interIds,
			String generalId,Model model,int isUpdate) throws Exception{
		if(interIds.length > 0){
			List<InterfaceControlDetail>  details = new ArrayList<InterfaceControlDetail>();
			for (int i = 0; i < interIds.length; i++) {
				InterfaceControlDetail detail = new InterfaceControlDetail();
				detail.setGeneralId(generalId);
				detail.setInterfaceId(interIds[i]);
				detail.setCreateDate(new Date());
				detail.setState(InterfaceControlState.SYZ); //默认
				detail.setTimes(10L);  //默认
				details.add(detail);
			}
			if(details.size() > 0){
				if(isUpdate == 1){
					List<InterfaceControlDetail> existDetails = interfaceControlDetailService.findByGeneralId(generalId);
					//先删除
					interfaceControlDetailService.deleteByGeneralId(generalId);
				}
				interfaceControlDetailService.batchAdd(details);
			}
		}else{
			model.addAttribute("errorMessage", "您未选择任何接口");
			return editDetailUI(request,generalId,model);
		}
		return list(request,null,model);
	}
}
