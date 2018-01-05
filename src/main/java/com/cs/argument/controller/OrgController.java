package com.cs.argument.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cs.argument.entity.Org;
import com.cs.argument.service.OrgService;
import com.cs.common.annotation.UserActionLog;
import com.cs.common.entityenum.UserLogType;
import com.cs.mvc.dao.SqlCondition;
import com.cs.mvc.web.BaseController;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @ClassName:    OrgController
 * @Description:  组织控制器
 * @Author        xj
 * @date          2017-10-26  上午9:53:59
 */
@Controller
@RequestMapping("/backend/argument/org")
public class OrgController extends BaseController {

	@Autowired
	private OrgService orgService;
	
	
	private PageInfo<Org> pageView;
	/**
	 * @throws Exception 
	 * @Description:  查询机构列表
	 * 
	 */
	@RequestMapping(value = "/list",method=RequestMethod.GET)
	public String list(HttpServletRequest request,@ModelAttribute("org") Org org,
			Model model) throws Exception{
		SqlCondition sqlCondition = new SqlCondition();
		if(null != org)
		{
			sqlCondition.addLikeCriterion("code like ", org.getCode());
			sqlCondition.addLikeCriterion("name like ", org.getName());
			sqlCondition.addSingleCriterion("STATUS = ", org.getStatus());
			sqlCondition.addLikeCriterion("BUSINESS_TYPE like ", org.getBusinessType());
		}
		sqlCondition.addAscOrderbyColumn("CODE");
		pageView = orgService.findByCondition(sqlCondition,getCurrentPage(request),12);
		model.addAttribute("pageView", pageView);
		return "/backend/argument/org-list";
	}
	
	@RequestMapping(value = "/addUI", method = RequestMethod.GET)
	public String addUI(Model model) throws Exception {
		
		return "/backend/argument/org-add";
	}
	
	/**
	 * 验证code是否重复
	 * 
	 * @return
	 */
	@RequestMapping(value = "/checkCode")
	@ResponseBody
	public Object checkCode(HttpServletRequest request, String code)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Boolean checkResult = false;
		if (StringUtils.isBlank(code)) {
			map.put("isBlank", true);
		} else {
			map.put("isBlank", false);
			Org org = orgService.findByCode(java.net.URLDecoder.decode(
					java.net.URLDecoder.decode(code, "UTF-8"), "UTF-8"));
			if (null != org) {
				checkResult = true;
			}
		}
		map.put("isExist", checkResult);
		return map;
	}

	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@UserActionLog(userLogType = UserLogType.ARGUMENTS,description="添加受理单位")
	public String add(HttpServletRequest request,Org org, Model model) throws Exception {
		orgService.insert(org);
		return list(request, org, model);
	}
	
	
	@RequestMapping(value = "/editUI", method = RequestMethod.GET)
	public String editUI(Model model,Org org) throws Exception {
		org = orgService.selectByPrimaryKey(org.getId());
		
		model.addAttribute("org",org);
		return "/backend/argument/org-edit";
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@UserActionLog(userLogType = UserLogType.ARGUMENTS,description="修改受理单位")
	public String edit(HttpServletRequest request,Org org, Model model) throws Exception {
		orgService.updateByPrimaryKey(org);
		return list(request, org, model);
	}
	
	
	@RequestMapping(value = "/del", method = RequestMethod.GET)
	@UserActionLog(userLogType = UserLogType.WORKDAY,description="删除受理单位")
	public String del(Org org) throws Exception{
		orgService.deleteByPrimaryKey(org.getId());
		return list(null,org,null);
	}
	
}
