package com.cs.appoint.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cs.appoint.dto.BookInfoAmount;
import com.cs.appoint.entity.BookInfoSupervise;
import com.cs.appoint.service.BookInfoService;
import com.cs.appoint.service.BookInfoSuperviseService;
import com.cs.appoint.service.VehIsFlowService;
import com.cs.argument.entity.Station;
import com.cs.argument.service.StationService;
import com.cs.common.utils.DateUtils;
import com.cs.mvc.web.BaseController;
import com.github.pagehelper.PageInfo;

/**
 * 事后监管控制器
 * @ClassName:    BookInfoSuperviseController
 * @Description:  
 * @Author        succ
 * @date          2017-11-7  上午10:08:04
 */
@Controller
@RequestMapping(value = "/appoint/supervise")
public class BookInfoSuperviseController extends BaseController {
	
	@Autowired
	private BookInfoService bookInfoService;
	@Autowired
	private VehIsFlowService vehIsFlowService;
	@Autowired
	private StationService stationService;
	@Autowired
	private BookInfoSuperviseService superviseService;
	
	/**
	 * 事后监管统计入口页
	 * @param request
	 * @param bookInfoSupervise
	 * @return
	 * @throws Exception
	 * @Author        succ
	 * @date 2017-11-20 下午2:43:11
	 */
	@RequestMapping(value = "/index")
	public String findBookInfoException(HttpServletRequest request,BookInfoSupervise bookInfoSupervise) throws Exception{
		//如果没有显式指定开始日期、结束日期，默认选择昨天
		if(bookInfoSupervise.getStartDate()==null || bookInfoSupervise.getEndDate()==null){
			Date date = DateUtils.truncate(DateUtils.addDays(new Date(), -1), Calendar.DATE);
			bookInfoSupervise.setStartDate(date);
			bookInfoSupervise.setEndDate(date);
		}
		//方便查看测试效果 2017-11-08/2017-11-11 有数据，后期需要删除
//		bookInfoSupervise.setStartDate(DateUtils.parse("2017-11-08", "yyyy-MM-dd"));
//		bookInfoSupervise.setEndDate(DateUtils.parse("2017-11-11", "yyyy-MM-dd"));
		Date startDate = bookInfoSupervise.getStartDate();
		Date endDate = bookInfoSupervise.getEndDate();
		//获取各个检测站的  预约数量、实际办理数量、异常记录数量
		List<BookInfoAmount> bookAmount = bookInfoService.findFinishedBookAmountGroupByStation(startDate, endDate);
		List<BookInfoAmount> flowAmount = superviseService.findFlowAmountGroupByStation(startDate, endDate);
		List<BookInfoAmount> exceptionAmount = superviseService.findExceptionAmountGroupByStation(startDate, endDate);
		
		List<Station> stationList = stationService.findAllData();
		if(CollectionUtils.isNotEmpty(stationList)){
			List<String> stationIdList = new ArrayList<String>();
			//检测站名称集合
			List<String> stationNameList = new ArrayList<String>();
			//预约集合
			List<Integer> bookList = new ArrayList<Integer>();
			//实际办理集合
			List<Integer> realList = new ArrayList<Integer>();
			//异常集合
			List<Integer> exceptionList = new ArrayList<Integer>();
			for(int i=0; i<stationList.size(); i++){
				Station station = stationList.get(i);
				stationIdList.add(station.getId());
				stationNameList.add(station.getName());
				bookList.add(findAmountFromList(bookAmount,station.getId()));
				realList.add(findAmountFromList(flowAmount,station.getId()));
				exceptionList.add(findAmountFromList(exceptionAmount,station.getId()));
			}
			request.setAttribute("stationIdList", JSONArray.fromObject(stationIdList).toString());
			request.setAttribute("stationNameList", JSONArray.fromObject(stationNameList).toString());
			request.setAttribute("bookList", JSONArray.fromObject(bookList).toString());
			request.setAttribute("realList", JSONArray.fromObject(realList).toString());
			request.setAttribute("exceptionList", JSONArray.fromObject(exceptionList).toString());
		}
		return "backend/appoint/supervise_list";
	}
	
	/**
	 * 事后监管详情页
	 * @param request
	 * @param bookInfoSupervise
	 * @return
	 * @throws Exception
	 * @Author        succ
	 * @date 2017-11-20 下午2:58:16
	 */
	@RequestMapping("/detail")
	public String detail(HttpServletRequest request,BookInfoSupervise bookInfoSupervise) throws Exception{
		List<Station> stationList = stationService.findAllData();
		request.setAttribute("stationList", stationList);
		//如果没有显式指定开始日期、结束日期，默认选择昨天
		if(bookInfoSupervise.getStartDate()==null || bookInfoSupervise.getEndDate()==null){
			Date date = DateUtils.truncate(DateUtils.addDays(new Date(), -1), Calendar.DATE);
			bookInfoSupervise.setStartDate(date);
			bookInfoSupervise.setEndDate(date);
		}
		PageInfo<BookInfoSupervise> pageView = superviseService.findPagedBookInfo(bookInfoSupervise, 
				getCurrentPage(request), getCurrentPageSize(request));
		request.setAttribute("pageView", pageView);
		return "backend/appoint/supervise_detail";
	}
	
	/**
	 * 获取指定检测站的数量
	 * @param list
	 * @param stationId
	 * @return
	 * @Author        succ
	 * @date 2017-11-20 下午3:42:47
	 */
	private int findAmountFromList(List<BookInfoAmount> list,String stationId){
		if(CollectionUtils.isEmpty(list)){
			return 0;
		}
		BookInfoAmount equalsObj = new BookInfoAmount();
		equalsObj.setStationId(stationId);
		for(BookInfoAmount obj : list){
			if(equalsObj.equals(obj)){
				return obj.getAmount().intValue();
			}
		}
		return 0;
	}
	
	public static void main(String[] args) {
		Date date = new Date();
		System.out.println(DateUtils.formatDate(date, "yyyy-MM-dd HH:mm:ss"));
		date = DateUtils.truncate(date, Calendar.DATE);
		System.out.println(DateUtils.formatDate(date, "yyyy-MM-dd HH:mm:ss"));
	}
}
