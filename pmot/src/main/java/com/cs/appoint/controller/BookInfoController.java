package com.cs.appoint.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cs.appoint.entity.BookInfo;
import com.cs.appoint.service.BookInfoService;
import com.cs.appoint.service.VehIsFlowInspectionLogService;
import com.cs.appoint.webservice.BookInfoWebService;
import com.cs.common.constant.CacheConstant;
import com.cs.common.entityenum.BookChannel;
import com.cs.common.entityenum.BookState;
import com.cs.common.entityenum.UserType;
import com.cs.common.utils.CacheUtil;
import com.cs.mvc.dao.SqlCondition;
import com.cs.mvc.web.BaseController;
import com.cs.system.entity.User;
import com.cs2.veh.service.IVehIsFlowService;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("/backend/appoint/bookInfo")
public class BookInfoController extends BaseController {
	
	@Autowired
	private BookInfoService bookInfoService;
	@Autowired
	private BookInfoWebService bookInfoWebService;
	@Autowired
	private IVehIsFlowService ivehIsFlowService;
	@Autowired
	private VehIsFlowInspectionLogService vehIsFlowInspectionLogService;
	
	private PageInfo<BookInfo> pageView;
	
	/**
	 * @throws Exception
	 * @Description: 查询检测站列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(HttpServletRequest request,
			@ModelAttribute("bookInfo") BookInfo bookInfo,String startDate,String endDate,Model model){
		SqlCondition sqlCondition = new SqlCondition();
		try {
			if(null != bookInfo){
				sqlCondition.addSingleCriterion("book_number = ", bookInfo.getBookNumber());
				//sqlCondition.addSingleCriterion("STATION_ID = ", bookInfo.getStationId());
				sqlCondition.addLikeCriterion("STATION_NAME like ", bookInfo.getStationName());
				sqlCondition.addSingleCriterion("ID_NUMBER = ", bookInfo.getIdNumber());
				sqlCondition.addSingleCriterion("PLAT_NUMBER = ", bookInfo.getPlatNumber());
				sqlCondition.addSingleCriterion("FRAME_NUMBER = ", bookInfo.getFrameNumber());
				sqlCondition.addSingleCriterion("BOOK_STATE = ", bookInfo.getBookState());
				sqlCondition.addSingleCriterion("BOOK_CHANNEL = ", bookInfo.getBookChannel());
			}
			if(StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)){
				//sqlCondition.addBetweenNotNullCriterion("to_char(create_date,'YYYY-MM-DD') BETWEEN", startDate, endDate);
				sqlCondition.addBetweenNotNullCriterion("book_Date BETWEEN", startDate, endDate);
				model.addAttribute("startDate", startDate);
				model.addAttribute("endDate", endDate);
			}
			// 这里根据用户来查询
			User user = (User) CacheUtil.getCacheObject(CacheConstant.BACKEND_USER_CACHE, request);
			// 检测站用户只能查看自己检测站的预约信息
			if(user.getUserType().equals(UserType.STATION)){ 
				String userId = user.getId();
				if(user.getUserType().equals(UserType.STATION) && StringUtils.isNotBlank(user.getStationId())){
					sqlCondition.addSingleCriterion("station_ID = ",user.getStationId());	
				}else
				{
					sqlCondition.addSingleCriterion("user_ID = ",userId);	
				}
			}
			sqlCondition.addDescOrderbyColumn("create_date");
			pageView = bookInfoService.findByCondition(sqlCondition,getCurrentPage(request),12);
			//List<Station> stationInfoList = stationService.findAllData();
			//model.addAttribute("stationInfoList", stationInfoList);
			List<BookChannel> channelList = BookChannel.getAll();
			model.addAttribute("channelList", channelList);
			List<BookState> bookStateList = BookState.getAll();
			model.addAttribute("bookStateList", bookStateList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("pageView", pageView);
		return  "backend/appoint/bookInfo_list";
	}
	/**
	 * @throws Exception
	 * @Description: 查看详情
	 */
	@RequestMapping(value = "/view", method=RequestMethod.GET)
	public String view(HttpServletRequest request,@ModelAttribute("bookInfo") BookInfo bookInfo, Model model){
		try {
			bookInfo = bookInfoService.selectByPrimaryKey(bookInfo.getId());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		model.addAttribute("bookInfo", bookInfo);
		return "/backend/appoint/bookInfo_view";
	}
	
	
	/**
	 * 作废弹出小窗口
	 * @return
	 */
	@RequestMapping(value = "/returnNumUI", method = RequestMethod.GET)
	public String returnNumUI(){
		return "/backend/appoint/input_verifyCode";
	}
	
	/**
	 * 作废弹出小窗口
	 * @return
	 */
	@RequestMapping(value = "/returnNum", method = RequestMethod.GET)
	public String returnNum(HttpServletRequest request,Model model,String bookNumber,String verifyCode){
		String message = bookInfoService.returnNumber(bookNumber, verifyCode);
		if(StringUtils.isNotBlank(message)){
			model.addAttribute("errorMessage", message);
		}else{
			model.addAttribute("errorMessage", "废除成功！");
		}
		return list(request, null, null, null, model);
	}
	
	/**
	 * 检测站用户修改信息
	 * @return
	 */
	@RequestMapping(value = "/editUI", method = RequestMethod.GET)
	public String editUI(HttpServletRequest request,Model model,@ModelAttribute("bookInfo") BookInfo bookInfo,String bookNumber){
		try {
			bookInfo = bookInfoService.selectByPrimaryKey(bookInfo.getId());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		model.addAttribute("bookInfo", bookInfo);
		return "backend/appoint/bookInfo_edit";
	}
	
	/**
	 * 检测站用户修改信息
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String edit(HttpServletRequest request,Model model,@ModelAttribute("bookInfo") BookInfo bookInfo){
		try {
			BookInfo existBookInfo = bookInfoService.selectByPrimaryKey(bookInfo.getId());
			if(null != existBookInfo){
				existBookInfo.setFrameNumber(bookInfo.getFrameNumber());
				bookInfoService.updateByPrimaryKey(existBookInfo);
			}else
			{
				model.addAttribute("errorMessage", "修改失败，未找到对应的预约信息！");
			}
		} catch (Exception e) {
			model.addAttribute("errorMessage", "修改失败，出现异常"+e.getMessage()+"！");
			e.printStackTrace();
		}
		model.addAttribute("errorMessage", "修改成功！");
		model.addAttribute("bookInfo", bookInfo);
		return list(request, null, null, null, model);
	}
}
