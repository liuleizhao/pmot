package com.cs.appoint.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cs.appoint.entity.BookInfoCompatible;
import com.cs.appoint.service.BookInfoCompatibleService;
import com.cs.argument.entity.Station;
import com.cs.argument.service.StationService;
import com.cs.mvc.dao.SqlCondition;
import com.cs.mvc.web.BaseController;
import com.github.pagehelper.PageInfo;

/**
 * 预约兼容控制器
 * @ClassName 	BookInfoCompatibleController
 * @Description
 * @Author 		suchaochen
 * @Date 		2017-12-13	 下午7:31:06
 */
@Controller
@RequestMapping("/backend/appoint/compatible")
public class BookInfoCompatibleController extends BaseController {
	
	@Autowired
	private BookInfoCompatibleService bookInfoCompatibleService;
	@Autowired
	private StationService stationService;
	
	@RequestMapping("/list")
	public String list(HttpServletRequest request,Model model,BookInfoCompatible bookInfoCompatible) throws Exception{
		SqlCondition sqlCondition = new SqlCondition();
		if(bookInfoCompatible != null){
			sqlCondition.addSingleCriterion("STATION_ID = ", bookInfoCompatible.getStationId());
		}
		sqlCondition.addDescOrderbyColumn("create_date");
		PageInfo<BookInfoCompatible> pageView = bookInfoCompatibleService.findByCondition(sqlCondition,getCurrentPage(request),getCurrentPageSize(request));
		model.addAttribute("pageView", pageView);
		model.addAttribute("stations", stationService.findAllData());
		return "backend/appoint/compatible_list";
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
		model.addAttribute("stations", stationService.findAllData());
		return "backend/appoint/compatible_add";
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
	public String add(HttpServletRequest request, Model model, BookInfoCompatible bookInfoCompatible)throws Exception {
		if(StringUtils.isNotBlank(bookInfoCompatible.getId())){
			return edit(request,model,bookInfoCompatible);
		}
		if(StringUtils.isBlank(bookInfoCompatible.getStationId())){
			addErrorMessage("检测站不能为空！");
			return addUI(request, model);
		}
		if(bookInfoCompatible.getCompatibleValue()==null || bookInfoCompatible.getCompatibleValue().compareTo(0)<0){
			addErrorMessage("兼容时长不能为空，且必须是大于0的整数！");	
			return addUI(request, model);
		}
		if(bookInfoCompatible.getStartDate()==null || bookInfoCompatible.getEndDate()==null){
			addErrorMessage("预约兼容必须设置生效开始日期和结束日期！");	
			return addUI(request, model);
		}
		if(bookInfoCompatible.getEndDate().getTime()<bookInfoCompatible.getStartDate().getTime()){
			addErrorMessage("生效开始日期必须小于结束日期！");	
			return addUI(request, model);
		}
		Station station = stationService.selectByPrimaryKey(bookInfoCompatible.getStationId());
		if(station == null){
			addErrorMessage("该检测站不存在！");
			return addUI(request, model);
		}
		bookInfoCompatibleService.insert(bookInfoCompatible);
		addMessage("【" + station.getName() + "】设置成功！");
		model.asMap().remove("bookInfoCompatible");
		return list(request,model,null);
	}

	/**
	 * @Description: 跳转到修改页面
	 * @param model
	 * @return String
	 * @throws Exception
	 * @date 2016-10-31 下午12:12:33
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String editUI(String id, Model model) throws Exception {
		BookInfoCompatible bookInfoCompatible = bookInfoCompatibleService.selectByPrimaryKey(id);
		model.addAttribute("bookInfoCompatible", bookInfoCompatible);
		return "backend/appoint/compatible_edit";
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
	public String edit(HttpServletRequest request,Model model,BookInfoCompatible bookInfoCompatible) throws Exception {
		try {
			BookInfoCompatible currentCompatible = bookInfoCompatibleService.selectByPrimaryKey(bookInfoCompatible.getId());
			if(null == currentCompatible)
			{
				addErrorMessage("修改预约兼容配置失败，未找到对应的配置信息！");
				return list(request,model,null);
			}
			if(bookInfoCompatible.getCompatibleValue()==null || bookInfoCompatible.getCompatibleValue().compareTo(0)<0){
				addErrorMessage("兼容时长不能为空，且必须是大于0的整数！");	
				return "backend/appoint/compatible_edit";
			}
			if(bookInfoCompatible.getStartDate()==null || bookInfoCompatible.getEndDate()==null){
				addErrorMessage("预约兼容必须设置生效开始日期和结束日期！");	
				return "backend/appoint/compatible_edit";
			}
			if(bookInfoCompatible.getEndDate().getTime()<bookInfoCompatible.getStartDate().getTime()){
				addErrorMessage("生效开始日期必须小于结束日期！");	
				return "backend/appoint/compatible_edit";
			}
			currentCompatible.setCompatibleValue(bookInfoCompatible.getCompatibleValue());
			currentCompatible.setStartDate(bookInfoCompatible.getStartDate());
			currentCompatible.setEndDate(bookInfoCompatible.getEndDate());
			bookInfoCompatibleService.updateByPrimaryKey(currentCompatible);
			addMessage("【"+currentCompatible.getStationName()+"】预约兼容修改成功！");
			model.asMap().remove("bookInfoCompatible");
			return list(request,model,null);
		}catch (Exception e) {
			 e.printStackTrace();
			 addErrorMessage("修改失败！");
			 return "backend/appoint/compatible_edit";
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
	public boolean delete(String id) throws Exception{
		if (StringUtils.isBlank(id) ||  null == bookInfoCompatibleService.selectByPrimaryKey(id)) {
			return false;
		}else {
			bookInfoCompatibleService.deleteByPrimaryKey(id);
			return true;
		}
	}
	
	@RequestMapping(value="/findByStationId")
	@ResponseBody
	public BookInfoCompatible findByStationId(String stationId) throws Exception{
		return bookInfoCompatibleService.findByStationId(stationId);
	}
}
