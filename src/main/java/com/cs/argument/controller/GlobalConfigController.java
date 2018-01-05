package com.cs.argument.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cs.mvc.dao.SqlCondition;
import com.cs.mvc.web.BaseController;
import com.cs.system.entity.GlobalConfig;
import com.cs.system.service.GlobalConfigService;
import com.github.pagehelper.PageInfo;

/**
 * 全局参数控制器
 * @ClassName:    GlobalConfigController
 * @Description:  
 * @Author        succ
 * @date          2017-11-22  上午11:03:07
 */
@Controller
@RequestMapping("/backend/argument/globalConfig")
public class GlobalConfigController extends BaseController {
	
	@Autowired
	private GlobalConfigService globalConfigService;
	
	/**
	 * @throws Exception
	 * @Description: 查询全局配置列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(HttpServletRequest request, GlobalConfig globalConfig, Model model) throws Exception {
		SqlCondition sqlCondition = new SqlCondition();
		if(globalConfig != null){
			sqlCondition.addSingleCriterion("DATA_KEY = ", globalConfig.getDataKey());
			sqlCondition.addLikeCriterion("NAME LIKE ", globalConfig.getName());
		}
		sqlCondition.addDescOrderbyColumn("create_date");
		PageInfo<GlobalConfig> pageView = globalConfigService.findByCondition(sqlCondition,getCurrentPage(request),getCurrentPageSize(request));
		model.addAttribute("pageView", pageView);
		return "backend/argument/globalConfig_list";
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
		return "backend/argument/globalConfig_add";
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
	public String add(HttpServletRequest request, Model model, GlobalConfig globalConfig)throws Exception {
		if(StringUtils.isBlank(globalConfig.getDataKey()) || StringUtils.isBlank(globalConfig.getName())
				|| StringUtils.isBlank(globalConfig.getDataValue()))
		{
			addErrorMessage("参数名称和参数值不能为空！");
			return addUI(request, model);
		} 
		if(this.checkDataKeyIsExisted(globalConfig.getDataKey())){
			addErrorMessage("参数Key已存在！");
			return addUI(request, model);
		}
		globalConfigService.insert(globalConfig);
		addMessage("添加参数【" + globalConfig.getName() + "】成功！");
		return list(request,globalConfig, model);
	}

	/**
	 * @Description: 跳转到修改页面
	 * @param model
	 * @return String
	 * @throws Exception
	 * @date 2016-10-31 下午12:12:33
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String editUI(String globalConfigId, Model model) throws Exception {
		GlobalConfig globalConfig = globalConfigService.selectByPrimaryKey(globalConfigId);
		model.addAttribute("globalConfig", globalConfig);
		return "backend/argument/globalConfig_edit";
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
	public String edit(HttpServletRequest request,Model model,GlobalConfig globalConfig) throws Exception {
		try {
			GlobalConfig currentGlobalConfig = globalConfigService.selectByPrimaryKey(globalConfig.getId());
			if(null == currentGlobalConfig)
			{
				addErrorMessage("修改参数信息失败，未找到对应的参数信息！");
				return list(request,null, model);
			}
			if(StringUtils.isBlank(globalConfig.getDataKey()) || StringUtils.isBlank(globalConfig.getName())
					|| StringUtils.isBlank(globalConfig.getDataValue()))
			{
				addErrorMessage("参数名称和参数值不能为空！");
				return "backend/argument/globalConfig_edit";
			} 
			if(!globalConfig.getDataKey().equals(currentGlobalConfig.getDataKey()) 
					&& this.checkDataKeyIsExisted(globalConfig.getDataKey())){
				globalConfig.setDataKey(currentGlobalConfig.getDataKey());
				addErrorMessage("参数Key已存在！");
				return "backend/argument/globalConfig_edit";
			}
			
			globalConfigService.updateByPrimaryKeySelective(globalConfig);
			addMessage("修改检参数信息【"+globalConfig.getName()+"】成功！");
			return list(request,globalConfig, model);
		}catch (Exception e) {
			 e.printStackTrace();
			 addErrorMessage("修改检参数信息【"+globalConfig.getName()+"】失败！");
			 return "backend/argument/globalConfig_edit";
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
	public boolean delete(String globalConfigId) throws Exception{
		if (StringUtils.isBlank(globalConfigId) ||  null == globalConfigService.selectByPrimaryKey(globalConfigId)) {
			return false;
		}else {
			globalConfigService.deleteByPrimaryKey(globalConfigId);
			return true;
		}
	}
	
	/**
	 * 校验Key是否存在，存在返回true，不存在返回false
	 * @param dataKey
	 * @return
	 * @throws Exception
	 * @Author        succ
	 * @date 2017-11-23 上午10:42:36
	 */
	@RequestMapping(value="/checkDataKeyIsExisted")
	@ResponseBody
	public boolean checkDataKeyIsExisted(String dataKey) throws Exception{
		boolean result = false;
		if(StringUtils.isNotBlank(dataKey) && null!=globalConfigService.findByDataKey(dataKey)){
			result = true;
		}
		return result;
	}
}
