package com.cs.argument.controller;

import java.util.Arrays;
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
import com.cs.argument.entity.BlackListRecord;
import com.cs.argument.service.BlackListRecordService;
import com.cs.common.entityenum.BlacklistState;
import com.cs.common.entityenum.RecordType;
import com.cs.mvc.dao.SqlCondition;
import com.cs.mvc.web.BaseController;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping(value = "/backend/argument/blackListRecord")
public class BlackListRecordController extends BaseController{
	
	@Autowired
	private BookInfoService bookInfoService;
	
	@Autowired
	private BlackListRecordService blackListRecordService;
	
	private PageInfo<BlackListRecord> pageView;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(HttpServletRequest request,@ModelAttribute("blackListRecord") 
	BlackListRecord blackListRecord,Model model){
		try {
			SqlCondition sqlCondition = new SqlCondition();
			if(null != blackListRecord){
				sqlCondition.addSingleCriterion("status =",blackListRecord.getStatus());
				sqlCondition.addSingleCriterion("RECORD_TYPE =",blackListRecord.getRecordType());
				sqlCondition.addSingleCriterion("RECORD_VALUE =",blackListRecord.getRecordValue());
			}
			pageView = blackListRecordService.findByCondition(sqlCondition,getCurrentPage(request),12);
		} catch (Exception e) {
			e.printStackTrace();
			errorMessage = "系统繁忙，请稍后再试";
		}
		List<BlacklistState> blacklistStates = BlacklistState.getAll();
		List<RecordType> recordTypes = RecordType.getAll();
		model.addAttribute("blacklistStates", blacklistStates);
		model.addAttribute("recordTypes", recordTypes);
		model.addAttribute("pageView", pageView);
		model.addAttribute("errorMessage", errorMessage);
		return "backend/argument/blackListRecord_list";
	}
	/**
	 * 查看导致进入黑名单的记录
	 * viewUI
	 * */
	@RequestMapping(value = "/viewUI", method = RequestMethod.GET)
	public String viewUI(HttpServletRequest request,String recordId,Model model){
		try {
			BlackListRecord blackListRecord = blackListRecordService.selectByPrimaryKey(recordId);
			if(null == blackListRecord){
				model.addAttribute("errorMessage", "系统繁忙，请稍后再试");
				return list(request, null, model);
			}else{
				if(StringUtils.isNotBlank(blackListRecord.getBookNumbers())){
					String [] bookNumbers = blackListRecord.getBookNumbers().split(",");
					List<String> bookNumberList = Arrays.asList(bookNumbers);
					List<BookInfo> bookInfoList = bookInfoService.queryBlackTRecord(bookNumberList);
					model.addAttribute("bookInfoList", bookInfoList);
				}else
				{
					model.addAttribute("errorMessage", "添加黑名单时未插入完成的信息！");
					return list(request, null, model);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "系统繁忙，请稍后再试");
			return list(request, null, model);
		}
		return "backend/argument/blackListRecord_view";
	}
	
	
}

