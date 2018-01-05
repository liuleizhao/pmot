package com.cs.system.controller.backend.frame;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cs.common.annotation.AuthValidate;
import com.cs.common.entityenum.ResourceType;
import com.cs.mvc.web.BaseController;
import com.cs.system.entity.Resource;
import com.cs.system.entity.User;
import com.cs.system.service.ResourceService;


@Controller
@RequestMapping(value = "/backend/frame")
public class BackendIndexController extends BaseController {

	/** 资源相关服务接口 */
	@Autowired
	private ResourceService resourceService;

	private List<Resource> resourceList;
	
	/**
	 * @描述：跳转到iframeInxdex
	 */
	@AuthValidate(exceptResource = true)
	@RequestMapping(value = "/iframeIndex", method = RequestMethod.GET)
	public String iframeIndex(HttpServletRequest request,Model model) throws Exception {
		User user = this.getBackendUser(request);
		model.addAttribute("user",this.getBackendUser(request));
		getResource(user, model);
		// 获取当前登陆者的所有权限
		return "backend/frame/iframeIndex";
	}
	
	/**
	 * @描述：引入home页面
	 */
	@AuthValidate(exceptResource = true)
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(HttpServletRequest request, Model model)
			throws Exception {
		//列出公告消息
		return "backend/frame/home";
	}
	public void getResource(User user,Model model) throws Exception
	{
		//获取权限资源
		if (user != null) {
			// 获取顶级模块
			List<Resource> topResources = resourceService.findResourceBy(user.getId(),"",ResourceType.MODULE);
			if (topResources != null && topResources.size() == 1) {
				Resource topResource = topResources.get(0);
				// 获取菜单
				List<Resource> parentResources  = resourceService.findResourceBy(user.getId(),topResource.getId(), ResourceType.MENU);
				if (parentResources != null && parentResources.size() > 0) {
					 Map<String, List<Resource>> childResources = new HashMap<String , List<Resource>>();	
					for (Resource resource : parentResources) {
						List<Resource> a = resourceService.findResourceBy(user.getId(),resource.getId(), ResourceType.MENU);
						if (a != null && a.size() > 0) {
							// 获取二级目录下的资源信息
							childResources.put(resource.getId(), a);
						}
					}
					model.addAttribute("childResources", childResources);
					
				}
				model.addAttribute("parentResources", parentResources);
			}
		}
	}
	
	/**
	 * @描述：引入菜单栏页面
	 *//*
	@AuthValidate(exceptResource = true)
	@RequestMapping(value = "/menu", method = RequestMethod.GET)
	public String menu(HttpServletRequest request, Model model)
			throws Exception {
		//resourceList = resourceService.findResourceBy(this.getBackendUser(request).getId(), null, null);
		//System.out.println("用户权限size:"+resourceList.size());
		// 获得登录用户
		BackendUser user = this.getBackendUser(request);
		String userName = user.getName();
		model.addAttribute("userName", userName);
		//获取权限资源
		if (user != null) {
			// 获取顶级模块
			List<Resource> topResources = resourceService.findResourceBy(user.getId(),"",ResourceType.MODULE);
			if (topResources != null && topResources.size() == 1) {
				Resource topResource = topResources.get(0);
				// 获取菜单
				List<Resource> parentResources  = resourceService.findResourceBy(user.getId(),topResource.getId(), ResourceType.MENU);
				if (parentResources != null && parentResources.size() > 0) {
					 Map<String, List<Resource>> childResources = new HashMap<String , List<Resource>>();	
					for (Resource resource : parentResources) {
						List<Resource> a = resourceService.findResourceBy(user.getId(),resource.getId(), ResourceType.MENU);
						if (a != null && a.size() > 0) {
							// 获取二级目录下的资源信息
							Map<String, List<Resource>> threeResources = new HashMap<String , List<Resource>>();
							for (Resource sencodResource : a) {
								List<Resource> threeResourceList = resourceService.findResourceBy(user.getId(),sencodResource.getId(), ResourceType.MENU);
								if (threeResourceList != null && threeResourceList.size() > 0) {
									threeResources.put(sencodResource.getId(), threeResourceList);
								}
							}
							model.addAttribute("threeResources", threeResources);
							childResources.put(resource.getId(), a);
						}
					}
					model.addAttribute("childResources", childResources);
					
				}
				model.addAttribute("parentResources", parentResources);
			}
			
		} 
		return "backend/frame/menu";
	}
*/
	
	/**
	 * 
	* @Title: listMyHomeNotice
	* @Description: 列出首页的公告消息
	* @param request
	* @return PageInfo<PubNotice>    
	* @throws Exception 
	 */
/*	private PageInfo<PubNotice> listMyHomeNotice(HttpServletRequest request) throws Exception {
		PageInfo<PubNotice> notices = null;
		SqlCondition sqlCondition = new SqlCondition();
		sqlCondition.addDescOrderbyColumn("START_DATE");
		BackendUser currentUser = (BackendUser) CacheUtil.getCacheObject(CacheConstant.BACKEND_USER_CACHE, request);//获取当前登录的用户
		PubNotice pubNotice  = new PubNotice();//4038ADAD42E7396FE0536B01A8C0F9A5
		pubNotice.setAcceptPersonIds(currentUser.getId());
		notices = pubNoticeService.findMyNoticeList(pubNotice, 1,5,1);
		return notices;
	}


	public List<Resource> getResourceList() {
		return resourceList;
	}

	public void setResourceList(List<Resource> resourceList) {
		this.resourceList = resourceList;
	}*/
	

}
