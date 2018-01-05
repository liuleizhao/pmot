package com.cs.data.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.cs.common.entityenum.ExportState;
import com.cs.data.entity.Export;
import com.cs.data.service.ConfigureService;
import com.cs.mvc.dao.SqlCondition;
import com.cs.mvc.web.BaseController;
import com.github.pagehelper.PageInfo;



@Controller
@RequestMapping(value = "/backend/data/conf")
public class ConfigureController extends BaseController{
	
	@Autowired
	private ConfigureService configureService;
	
	private PageInfo<Export> pageView;
	
	@RequestMapping(value = "/list",method = RequestMethod.GET)
	public String list(HttpServletRequest request,Model model,String tableName,ExportState state) throws Exception{
		
    	SqlCondition sqlCondition = new SqlCondition();
	    sqlCondition.addLikeCriterion("TABLE_NAME LIKE", tableName);
	    sqlCondition.addLikeCriterion("STATE =",state);
        sqlCondition.addAscOrderbyColumn("TABLE_NAME");
      	pageView = configureService.findByCondition(sqlCondition,getCurrentPage(request), getCurrentPageSize(request));
      	model.addAttribute("pageView", pageView);

		List<Export> list = configureService.findAllData();
		List<ExportState> statelist= ExportState.getAll();
		
		model.addAttribute("stateList", statelist);
		model.addAttribute("tableName", tableName);
		model.addAttribute("state", state);
		model.addAttribute("list", list);
		return "backend/data/conf_list";
	}
	
	@RequestMapping(value = "/add",method = RequestMethod.GET)
	public String add(HttpServletRequest request,Model model) throws Exception{
		
		List<String> tableList =  configureService.findTableName();
		List<String> colList =  configureService.findTableCol(tableList.get(0));
		
		List<Export> exportList = configureService.findEnable();
		for (int j = 0; j < exportList.size(); j++) {
			String tableName = exportList.get(j).getTableName();
			if(tableList.indexOf(tableName) != -1){
				tableList.remove(tableName);
			}
		}
		
		
		List<ExportState> statelist= ExportState.getAll();
		model.addAttribute("stateList", statelist);
		model.addAttribute("tableList", tableList);
		model.addAttribute("colList", colList);
		
		return "backend/data/conf_add";
	}
	
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	public String add(HttpServletRequest request,Export export,RedirectAttributesModelMap modelMap) throws Exception{
		
		if(configureService.checkTableName(export.getTableName())){
			modelMap.addFlashAttribute("message", "当前表名已存在");
			return redirect("/backend/data/conf/add");
		}
		export.setCreateDate(new Date());
		configureService.insert(export);
		modelMap.addFlashAttribute("message", "添加成功");
		return redirect("/backend/data/conf/list");
	}
	
	@RequestMapping(value = "/edit",method = RequestMethod.GET)
	public String edit(HttpServletRequest request,Model model,String id,RedirectAttributesModelMap modelMap) throws Exception{
		
		Export export = configureService.selectByPrimaryKey(id);
		
		if(export == null){
			modelMap.addAttribute("message", "数据库没有这张表的导出配置，请先添加");
			return redirect("/backend/data/conf/list");
		}
		
		List<String> colList =  configureService.findTableCol(export.getTableName());
		List<ExportState> statelist= ExportState.getAll();
		
		model.addAttribute("export", export);
		model.addAttribute("stateList", statelist);
		model.addAttribute("colList", colList);
		return "backend/data/conf_edit";
	}
	
	@RequestMapping(value = "/edit",method = RequestMethod.POST)
	public String edit(HttpServletRequest request,Export export,RedirectAttributesModelMap modelMap) throws Exception{
		if(configureService.updateByPrimaryKeySelective(export)>0){
			modelMap.addFlashAttribute("message", "修改成功");
		}else{
			modelMap.addFlashAttribute("message", "修改失败");
		}
		return redirect("/backend/data/conf/list");
	}
	
	@RequestMapping(value = "/del",method = RequestMethod.POST)
	public String delete(HttpServletRequest request,RedirectAttributesModelMap modelMap,String id) throws Exception{
		if(configureService.deleteByPrimaryKey(id)>0){
			modelMap.addFlashAttribute("message", "删除成功");
		}else{
			modelMap.addFlashAttribute("message", "删除失败");
		}
		return redirect("/backend/data/conf/list");
	}
	
	@RequestMapping(value = "/getCol",method = RequestMethod.POST)
	@ResponseBody
	public List<String> getCol(HttpServletRequest request,String tableName) throws Exception{
		return configureService.findTableCol(tableName);
	}
	
}
