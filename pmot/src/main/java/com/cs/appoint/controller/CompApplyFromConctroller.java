package com.cs.appoint.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cs.appoint.entity.BookInfo;
import com.cs.appoint.entity.CompApplyFrom;
import com.cs.appoint.service.BookInfoService;
import com.cs.appoint.service.CompApplyFromService;
import com.cs.appoint.vo.ComApplyBookInfoVO;
import com.cs.common.constant.CacheConstant;
import com.cs.common.entityenum.ApproveRemart;
import com.cs.common.entityenum.BookChannel;
import com.cs.common.entityenum.BookState;
import com.cs.common.entityenum.UserType;
import com.cs.common.utils.CacheUtil;
import com.cs.common.utils.DateUtils;
import com.cs.common.utils.IpUtil;
import com.cs.common.utils.JacksonUtil;
import com.cs.mvc.dao.SqlCondition;
import com.cs.mvc.web.BaseController;
import com.cs.system.entity.User;
import com.github.pagehelper.PageInfo;
/**
 * 大客户预约
 * @author huang
 *
 */
@Controller
@RequestMapping(value = "/backend/appoint")
public class CompApplyFromConctroller extends BaseController {
	@Autowired
	private CompApplyFromService mApplyFromService;
	private PageInfo<CompApplyFrom> pageView;
	
	private PageInfo<BookInfo> bookInfopageView;
	@Autowired 
	private BookInfoService bookInfoService;
	
	@RequestMapping(value = "/compApplylist" ,method = RequestMethod.GET)
    public String compApplylist(HttpServletRequest request,@ModelAttribute("compApplyFrom") CompApplyFrom compApplyFrom,Date appointDate,Model model){
		SqlCondition sqlCondition = new SqlCondition();
		User backendUser = getBackendUser(request);
		try {
			if(null != compApplyFrom){
				if(null != compApplyFrom.getCompanyName()){
					sqlCondition.addLikeCriterion("COMPANY_NAME like ", compApplyFrom.getCompanyName());
					model.addAttribute("companyName", compApplyFrom.getCompanyName());
				}
				if(null != appointDate){
					sqlCondition.addSingleCriterion("START_DATE <= ", appointDate);
					sqlCondition.addSingleCriterion("END_DATE >= ", appointDate);
					model.addAttribute("appointDate", appointDate);
				}
			}
			sqlCondition.addDescOrderbyColumn("create_date");
			sqlCondition.addSingleNotNullCriterion("CREATE_USER_ID =",backendUser.getId() );
			
			pageView = mApplyFromService.findByCondition(sqlCondition,getCurrentPage(request),12);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("pageView", pageView);
    	return "backend/appoint/compAplly_list";
    }

	
	@RequestMapping(value = "/compApplyAdd" ,method = RequestMethod.GET)
    public String compApplyAddUI(Model model){
		
    	return "backend/appoint/compAplly_add";
    }
 
	/**
	 * 申请表
	 * @param request
	 * @param model
	 * @param compApplyFrom
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/compApplyAdd" ,method = RequestMethod.POST)
    public String compApplyAdd(HttpServletRequest request,Model model,@ModelAttribute("compApplyFrom") CompApplyFrom compApplyFrom) throws Exception{
		if(compApplyFrom != null){
			User user = getBackendUser(request);
			compApplyFrom.setCreateDate(new Date());
			compApplyFrom.setCreateUserId(user.getId());
			compApplyFrom.setCreateUserName(user.getName());
			//证件号转大写
			compApplyFrom.setOrganizationCode(compApplyFrom.getOrganizationCode().toUpperCase());
			compApplyFrom.setEnterpriseCrediteCode(compApplyFrom.getEnterpriseCrediteCode().toUpperCase());
			compApplyFrom.setCompanyName(compApplyFrom.getCompanyName().toUpperCase());
			mApplyFromService.insert(compApplyFrom);
		}
    	return compApplylist(request,null,null,model);
    }
	
	
	
	/**
	 * 审批表
	 * @param request
	 * @param compApplyFrom
	 * @param appointDate
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/approveList" ,method = RequestMethod.GET)
    public String approveList(HttpServletRequest request,@ModelAttribute("compApplyFrom") CompApplyFrom compApplyFrom,Date appointDate,Model model){
		SqlCondition sqlCondition = new SqlCondition();
	//	User backendUser = getBackendUser(request);
		try {
			if(null != compApplyFrom){
				if(null != compApplyFrom.getCompanyName()){
					sqlCondition.addLikeCriterion("COMPANY_NAME like ", compApplyFrom.getCompanyName());
					model.addAttribute("companyName", compApplyFrom.getCompanyName());
				}
				if(null != appointDate){
					sqlCondition.addSingleCriterion("START_DATE <= ", appointDate);
					sqlCondition.addSingleCriterion("END_DATE >= ", appointDate);
					model.addAttribute("appointDate", appointDate);
				}
			}
			sqlCondition.addDescOrderbyColumn("create_date");
			pageView = mApplyFromService.findByCondition(sqlCondition,getCurrentPage(request),12);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("pageView", pageView);
    	return "backend/appoint/approve_list";
    }
	
	
	@RequestMapping(value = "/approveUI" ,method = RequestMethod.GET)
    public String approveUI(HttpServletRequest request,Model model,String compApplyId){
		if(StringUtils.isNotBlank(compApplyId)){
			try {
				CompApplyFrom comApplyFrom = mApplyFromService.selectByPrimaryKey(compApplyId);
				List<ApproveRemart> approveRemarts = ApproveRemart.getAll();
				model.addAttribute("comApplyFrom", comApplyFrom);
				model.addAttribute("approveRemarts", approveRemarts);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
    	return "backend/appoint/compAplly_approve";
    }
	
	/**
	 * 审批
	 * @param request
	 * @param model
	 * @param compApplyFrom
	 * @return
	 */
	@RequestMapping(value = "/approve" ,method = RequestMethod.GET)
    public String approve(HttpServletRequest request,Model model,String compApplyId,ApproveRemart approveRemart,String approveDiscription){
		
		try {
			CompApplyFrom comApplyFrom = mApplyFromService.selectByPrimaryKey(compApplyId);
			if(null != comApplyFrom){
				User user = getBackendUser(request);
				comApplyFrom.setApproverName(user.getName());
				comApplyFrom.setApproverId(user.getId());
				comApplyFrom.setApproveDate(new Date());
				comApplyFrom.setApproveDiscription(approveDiscription);
				comApplyFrom.setApproveRemart(approveRemart);
				mApplyFromService.updateByPrimaryKey(comApplyFrom);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return approveList(request,null,null,model);
    }
	/**
	 * 查看申请表详情
	 * @param request
	 * @param model
	 * @param compApplyId
	 * @return
	 */
	@RequestMapping(value = "/viewDetail" ,method = RequestMethod.GET)
    public String viewDetail(HttpServletRequest request,Model model,String compApplyId){
		if(StringUtils.isNotBlank(compApplyId)){
		
			try {
				CompApplyFrom compApplyFrom = mApplyFromService.selectByPrimaryKey(compApplyId);
				model.addAttribute("compApplyFrom", compApplyFrom);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
		return "backend/appoint/compAplly_viewDetail";
	}

	
	/**
	 * 大客户（特殊预约）预约页面
	 * 检测站用户或者大客户用户信息都有这个权限
	 * @param request
	 * @param bookInfo
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/specialBookUI", method = RequestMethod.GET)
	public String specialBookUI(HttpServletRequest request,Model model,String compApplyFromId){
		CompApplyFrom compApplyFrom = null;
		try {
			compApplyFrom = mApplyFromService.selectByPrimaryKey(compApplyFromId);
			String startDate = DateUtils.getDate(compApplyFrom.getStartDate());
			String endDate = DateUtils.getDate(compApplyFrom.getEndDate());
			String nowDate = DateUtils.getDate("yyyy-MM-dd");
		    if(nowDate.compareTo(startDate) > 0){
		    	startDate = nowDate; 
		    }
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("compApplyFrom", compApplyFrom);
		return "/backend/appoint/special_inputUI";
	}
	/**
	 * 大客户（特殊预约）预约页面
	 * @param request
	 * @param bookInfo
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/specialBook", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> specialBook(HttpServletRequest request,
			@ModelAttribute("bookInfo") BookInfo bookInfo, Model model,String compApplyFormId,String bookInfos){
		String errorMessage = "";
		Map<String,Object> result = new HashMap<String, Object>();
		try {
			//预约渠道必须是检测站
			if(bookInfo.getBookChannel() == BookChannel.BACKEND){
				User backendUser = getBackendUser(request);
				CompApplyFrom compApplyFrom = mApplyFromService.selectByPrimaryKey(compApplyFormId);
				if(StringUtils.isNotBlank(bookInfos)){
					String json = StringEscapeUtils.unescapeXml(bookInfos);
					List<ComApplyBookInfoVO> comBooks = JacksonUtil.jsonToList(json, ComApplyBookInfoVO.class);
					Integer  existCount = compApplyFrom.getAddedNum();
					//验证输入的条数是否大于申请条数
					if(compApplyFrom.getApplyCount() - (existCount+comBooks.size()) >= 0){
						String ip = IpUtil.getClientIp(request);
						bookInfo.setRequestIp(ip);
						List<BookInfo> existBookInfos = bookInfoService.batchAddCompAplyy(bookInfo,comBooks,backendUser,compApplyFrom);
						if(null != existBookInfos && existBookInfos.size() > 0){
							errorMessage = "已预约车辆无法更新";
							result.put("existBookInfos", existBookInfos);
							result.put("code", 1);  //标记要列出车牌号
						}
					}else{
						errorMessage = "所批准的数量为"+compApplyFrom.getApplyCount()+"，已预约"+existCount+"辆，还剩"+
					(compApplyFrom.getApplyCount()-existCount)+"辆，您现在申请："+comBooks.size()+"辆，超过批准的数量";
					}
				}
				
			}else{
				errorMessage = "您无权添加预约信息";
			}
		
		} catch (Exception e) {
			e.printStackTrace();
			errorMessage = "系统异常，请稍后再试";
		}
		result.put("errorMessage", errorMessage);
		return result;
	}
	
	
	@RequestMapping(value = "/comApllyBookList" ,method = RequestMethod.GET)
    public String comApllyBookList(HttpServletRequest request,@ModelAttribute("bookInfo") BookInfo bookInfo,String compApplyFromId,Model model){
		SqlCondition sqlCondition = new SqlCondition();
		try {
			sqlCondition.addSingleCriterion("COMP_APPLY_FORM_ID = ", compApplyFromId);
			if(null != bookInfo){
				sqlCondition.addLikeCriterion("PLAT_NUMBER like ", bookInfo.getPlatNumber());
				sqlCondition.addLikeCriterion("FRAME_NUMBER like ", bookInfo.getFrameNumber());
				sqlCondition.addSingleCriterion("BOOK_DATE = ", bookInfo.getBookDate());
			}
			sqlCondition.addAscOrderbyColumn("BOOK_DATE");
			bookInfopageView = bookInfoService.findByCondition(sqlCondition,getCurrentPage(request),12);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("compApplyFromId", compApplyFromId);
		model.addAttribute("pageView", bookInfopageView);
		return  "backend/appoint/comAplly_book_list";
    }
}
