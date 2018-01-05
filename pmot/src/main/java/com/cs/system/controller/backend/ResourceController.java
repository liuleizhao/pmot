package com.cs.system.controller.backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cs.common.annotation.UserActionLog;
import com.cs.common.entityenum.MethodType;
import com.cs.common.entityenum.ResourceType;
import com.cs.common.entityenum.UserLogType;
import com.cs.mvc.web.BaseController;
import com.cs.system.entity.Resource;
import com.cs.system.entity.User;
import com.cs.system.service.ResourceService;


/**
 * @ClassName:    ResourseController.java
 * @Description:  资源控制器
 * @Author        xuj
 * @date          2016-10-25  下午5:02:25
 */
@Controller
@RequestMapping(value="/backend/system/resource")
public class ResourceController extends BaseController{
	
	@Autowired
	private ResourceService resourceService;
	
	/**
	 * @Description:  查询资源列表
	 * 
	 */
	@RequestMapping(value = "/list",method=RequestMethod.GET)
	public String list(HttpServletRequest request,Model model){
		return "/backend/system/resource_list";
	}
	
	/**
	 * @Description:  跳转到添加资源的页面
	 * 
	 */
	@RequestMapping(value = "/add",method=RequestMethod.GET)
	public String addUI(HttpServletRequest request,Model model){
		List<ResourceType> resourceTypes = ResourceType.getAll();
		List<MethodType> methodTypes = MethodType.getAll();
		model.addAttribute("resourceTypes",resourceTypes);
		model.addAttribute("methodTypes",methodTypes);
		return "/backend/system/resource_add";
	}
	
	
	/**
	 * @Description:  执行添加的方法
	 * 
	 */
	@UserActionLog(userLogType = UserLogType.AUTHORITY,description="增加资源")
	@RequestMapping(value = "/add",method=RequestMethod.POST)
	public String add(HttpServletRequest request,Model model,Resource resource){
		try {
			// 使用request获取resource实体不能接受的数据 
			User user = getBackendUser(request);
			resource.setCreator(user.getId());
    		resourceService.addResource(resource,user);
    		// 传值到页面
			List<ResourceType> resourceTypes = ResourceType.getAll();
			List<MethodType> methodTypes = MethodType.getAll();
			model.addAttribute("resourceTypes",resourceTypes);
			model.addAttribute("methodTypes",methodTypes);
			model.addAttribute("message","增加资源【"+resource.getName()+"】成功.");
			return list(request, model);
		} catch (Exception e) {
		  e.printStackTrace();
		  List<ResourceType> resourceTypes = ResourceType.getAll();
		  List<MethodType> methodTypes = MethodType.getAll();
		  model.addAttribute("resourceTypes",resourceTypes);
		  model.addAttribute("methodTypes",methodTypes);
		  model.addAttribute("message","增加资源【"+resource.getName()+"】失败.");
		  return "/backend/system/resource_add";
		}
	}
	
	/**
	 * 针对Ztree 跳转到修改，添加页面
	 */
	@RequestMapping(value="/edit",method=RequestMethod.GET)
	public String editUI(HttpServletRequest request,String resourceId,String mark,Model model) throws Exception{
		String parentName = "无";
		Resource resource = null; 
		// 添加
		 if(StringUtils.isNotBlank(mark)&&"1".equals(mark)){ 
			 resource = new Resource();
			 Resource parentResource = resourceService.selectByPrimaryKey(resourceId);
			 if(null != parentResource){ // 根据父标签传过来的值，获取父标签
				 resource.setParentId(parentResource.getId());
				 parentName = parentResource.getName();
			 }
		 }else // 修改，使用同一个页面
		 {
			// 获取当前节点的信息
			  resource = resourceService.selectByPrimaryKey(resourceId);
			 // 为VO赋值，用于在页面显示父节点的名称
			 if(StringUtils.isNotBlank(resource.getParentId()))
			 {
				 Resource parentResource = resourceService.selectByPrimaryKey(resource.getParentId());
				 parentName = parentResource.getName();
			 }
		 }
		 // 传值到页面
		 List<ResourceType> resourceTypes = ResourceType.getAll();
		 List<MethodType> methodTypes = MethodType.getAll();
		 model.addAttribute("resource",resource);
		 model.addAttribute("parentName",parentName);
		 model.addAttribute("resourceTypes",resourceTypes);
		 model.addAttribute("methodTypes",methodTypes);
		return "/backend/system/resource_edit";
	}

	/**
	 * @throws Exception 
	 * @Description: 执行修改的方法
	 * @date          2015-11-7  下午4:34:15
	 */
	@UserActionLog(userLogType = UserLogType.AUTHORITY,description="编辑资源")
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object>  edit(HttpServletRequest request,Resource resource,Model model)
			throws Exception{
		User user = getBackendUser(request);
		Map<String,Object> result = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(resource.getId())){
			resourceService.updateByPrimaryKeySelective(resource);
			result.put("msg", "修改成功");
			result.put("mark", "0");
		}else {
			//添加子节点资源
			resource.setCreator(getBackendUser(request).getId());
			//resourceService.insert(resource);
			resourceService.addResource(resource,user);
    		result.put("msg", "新增成功");
    		result.put("mark", "1");
    		result.put("newNodeId", resource.getId());
    		result.put("newNodeName", resource.getName());
		}
		return result;
	}
	
	@UserActionLog(userLogType = UserLogType.AUTHORITY,description="删除资源")
	@RequestMapping(value="/remove",method=RequestMethod.POST)
	@ResponseBody
	public boolean remove(HttpServletRequest request,String resourceId,Model model) throws Exception{
		boolean flag = true;
		if(StringUtils.isNotBlank(resourceId)){
			resourceService.deleteResourceById(resourceId);	
    		flag = true;
    	}else{
    		flag = false;
    	}
		return flag;
	}
	
	@RequestMapping(value="/openradio")
	public String  openRadio(){
		return "/backend/system/resource_radio";
	}
	
	/**
	 * 根据条件获取资源，并组装
	 * @param request
	 * @param resource
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	
	@RequestMapping(value="/getResource",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getResource(HttpServletRequest request,Integer treeType,String parentId,String userId,String roleId ,Model model) throws Exception{
		 Map<String,Object> result = new HashMap<String,Object>(); 
		 //result.put("resources","{zNodes:[{id:'402881e44e57f529014e57f5b48e0000',name:'系统管理',open:false,children:[{id:'402881e44e57f529014e57f5b4a40001',name:'用户管理',open:false,children:[{id:'402881e44e57f529014e57f5b4a40002',name:'添加用户',open:false,children:[{id:'402881f1515be45101515c164a5e006d',name:'账户验证'}]},{id:'402881e44e57f529014e57f5b4a40003',name:'用户列表',open:false,children:[{id:'402881e44e57f529014e57f5b4a40004',name:'修改用户'},{id:'402881e44e57f529014e57f5b4a40005',name:'删除用户'},{id:'402881e44e57f529014e57f5b4a40006',name:'授权',open:false,children:[{id:'402881e44e57f529014e57f5b4a50007',name:'角色授权',open:false,children:[{id:'402881e44e57f529014e57f5b4a50008',name:'保存角色授权'}]},{id:'402881e44e57f529014e57f5b4a50009',name:'资源授权',open:false,children:[{id:'402881e44e57f529014e57f5b4a5000a',name:'保存资源授权'},{id:'402881f15165dcbf0151661669a20040',name:'获取资源'}]},{id:'402881e44e57f529014e57f5b4a5000b',name:'角色临时授权',open:false,children:[{id:'402881e44e57f529014e57f5b4a5000c',name:'保存角色临时授权'}]},{id:'402881e44e57f529014e57f5b4a5000d',name:'资源临时授权',open:false,children:[{id:'402881e44e57f529014e57f5b4a5000e',name:'保存资源临时授权'},{id:'402881f15165dcbf015166169e2d0042',name:'获取资源'}]},{id:'402881e44e57f529014e57f5b4a6000f',name:'资源临时授权管理',open:false,children:[{id:'402881e44e57f529014e57f5b4a80010',name:'停止临时资源授权'},{id:'402881e44e57f529014e57f5b4a80011',name:'启动临时资源授权'}]},{id:'402881e44e57f529014e57f5b4a80012',name:'角色临时授权管理',open:false,children:[{id:'402881e44e57f529014e57f5b4a80013',name:'停止临时角色授权'},{id:'402881e44e57f529014e57f5b4a80014',name:'启动临时角色授权'}]}]}]}]},{id:'402881e44e57f529014e57f5b4a80015',name:'权限管理',open:false,children:[{id:'402881e44e57f529014e57f5b4a80016',name:'添加角色',open:false,children:[{id:'402881e44e57f529014e57f5b4a80017',name:'保存角色'},{id:'402881f1515be45101515c18314b00d1',name:'获取资源'},{id:'402881f15165dcbf015166040caf0002',name:'验证角色'}]},{id:'402881e44e57f529014e57f5b4a90018',name:'角色列表',open:false,children:[{id:'402881e44e57f529014e57f5b4a90019',name:'修改角色',open:false,children:[{id:'402881e44e57f529014e57f5b4a9001a',name:'保存修改'},{id:'402881f1515be45101515c1911bb00d3',name:'获取资源'}]},{id:'402881e44e57f529014e57f5b4a9001b',name:'删除角色'}]},{id:'402881e44e57f529014e57f5b4a9001c',name:'资源列表',open:false,children:[{id:'402881e9510f3fc801510f7a97500136',name:'删除资源'}]},{id:'402881e44e57f529014e57f5b4a9001d',name:'添加资源',open:false,children:[{id:'402881e44e57f529014e57f5b4a9001e',name:'保存资源'},{id:'402881f1515be45101515c1bfe2e00d5',name:'选择父资源'},{id:'402881f1515be45101515c1cc4de00d7',name:'获取资源'}]}]},{id:'402881e44e57f529014e57f5b4a9001f',name:'内容管理',open:false,children:[{id:'402881e44e57f529014e57f5b4a90020',name:'内容列表',open:false,children:[{id:'402881e9510f3fc801510f87d3ae01c4',name:'新增内容',open:false,children:[{id:'402881e9510f3fc801510f88517e01c6',name:'新增保存'},{id:'402881f1515be45101515c228f9300df',name:'验证代码'}]},{id:'402881e9510f3fc801510f8b7a8601cc',name:'删除内容'},{id:'402881e9510f3fc801510f889ea701c8',name:'修改内容',open:false,children:[{id:'402881e9510f3fc801510f88ede301ca',name:'修改保存'}]}]},{id:'402881e44e57f529014e57f5b4aa0021',name:'协议告知',open:false,children:[{id:'402881e9510f3fc801510f8cbb3201ce',name:'协议保存'}]}]},{id:'402881e44e57f529014e57f5b4aa0022',name:'参数管理',open:false,children:[{id:'402881e44e57f529014e57f5b4aa0023',name:'检测站列表',open:false,children:[{id:'402881e9510f3fc801510f5d99360012',name:'修改检测站',open:false,children:[{id:'402881e9510f3fc801510f62d7fb007b',name:'修改保存'},{id:'402881f1515be45101515c254ae6014d',name:'删除附件'}]},{id:'402881e9510f3fc801510f641567007d',name:'添加检测站',open:false,children:[{id:'402881e9510f3fc801510f648e01007f',name:'添加保存'},{id:'402881f1515be45101515c1e0de500d9',name:'验证检测站代码'},{id:'402881f1515be45101515c24e78400e1',name:'删除附件'}]}]},{id:'402881e44e57f529014e57f5b4aa0024',name:'区县列表',open:false,children:[{id:'402881e9510f3fc801510f6679cf00b7',name:'新增区县',open:false,children:[{id:'402881e9510f3fc801510f66f6b600b9',name:'新增保存'},{id:'402881f1515be45101515c207f2400db',name:'验证区县代码'}]},{id:'402881e9510f3fc801510f6769f800bb',name:'修改区县',open:false,children:[{id:'402881e9510f3fc801510f67ac2900bd',name:'修改保存'}]},{id:'402881e9510f3fc801510f7f8ae401b6',name:'删除区县'}]},{id:'402881e44e57f529014e57f5b4aa0025',name:'车辆类型列表',open:false,children:[{id:'402881e9510f3fc801510f850e0101c0',name:'修改车辆类型',open:false,children:[{id:'402881e9510f3fc801510f857c1f01c2',name:'修改保存'}]},{id:'402881e9510f3fc801510f803ae601b8',name:'删除车辆类型'},{id:'402881e9510f3fc801510f80e65601ba',name:'新增车辆类型',open:false,children:[{id:'402881f1515be45101515c21227300dd',name:'验证号牌种类代码'},{id:'402881e9510f3fc801510f81fb7301be',name:'新增保存'}]}]}]},{id:'402881e44e57f529014e57f5b4aa0027',name:'预约管理',open:false,children:[{id:'402881e44e57f529014e57f5b4aa0028',name:'年审预约查询'},{id:'402881e44e57f529014e57f5b4aa0029',name:'预约统计'},{id:'402881e9510df6e001510e1233f20000',name:'预约更改',open:false,children:[{id:'402881e9510f3fc801510f7e264801b4',name:'预约更改保存'}]},{id:'402881e9510df6e001510e12865d0002',name:'预约更改记录'}]},{id:'402881e44e57f529014e57f5b4aa002a',name:'工作日管理',open:false,children:[{id:'402881e44e57f529014e57f5b4ab002b',name:'预约日维护',open:false,children:[{id:'402881e9510feac601510fedd81d0096',name:'预约日新增',open:false,children:[{id:'402881e9510feac601510feedd01009c',name:'新增保存'}]},{id:'402881e9510feac601510fee2d650098',name:'预约日修改',open:false,children:[{id:'402881e9510feac601510fef3680009e',name:'修改保存'}]},{id:'402881e9510feac601510fee857a009a',name:'预约日删除'}]},{id:'402881e44e57f529014e57f5b4ab002c',name:'特殊预约日维护',open:false,children:[{id:'402881e9510feac601510ff293aa00a8',name:'特殊预约日删除'},{id:'402881e9510feac601510ff15c0700a4',name:'特殊预约日修改',open:false,children:[{id:'402881e9510feac601510ff23d7400a6',name:'修改保存'}]},{id:'402881e9510feac601510ff072d200a0',name:'特殊预约日新增',open:false,children:[{id:'402881e9510feac601510ff0c2fc00a2',name:'新增保存'}]}]},{id:'402881e9510df6e001510e1750bc0006',name:'特殊预约时间段',open:false,children:[{id:'402881e9510feac601510ff8906200b6',name:'特殊预约时间段新增',open:false,children:[{id:'402881e9510feac601510ff901e800b8',name:'新增保存'}]},{id:'402881e9510feac601510ff969eb00ba',name:'特殊预约时间段修改',open:false,children:[{id:'402881e9510feac601510ff9a5f600bc',name:'修改保存'}]},{id:'402881e9510feac601510ffa58b700be',name:'特殊预约时间段删除'}]},{id:'402881e9510df6e001510e162bb30004',name:'默认预约时间段',open:false,children:[{id:'402881e9510feac601510ff6268c00ac',name:'默认预约时间段新增',open:false,children:[{id:'402881e9510feac601510ff66a8000ae',name:'新增保存'}]},{id:'402881e9510feac601510ff7d80500b4',name:'默认预约时间段删除'},{id:'402881e9510feac601510ff7285300b0',name:'默认预约时间段修改',open:false,children:[{id:'402881e9510feac601510ff763f200b2',name:'修改保存'}]},{id:'402881e9510feac601510ff4cb3d00aa',name:'设置默认预约时间段'}]}]},{id:'4028810b523f040d01523f1e35790090',name:'支付管理',open:false,children:[{id:'4028810b523f040d01523f1f0c520092',name:'支付查询'}]},{id:'402881e9510df6e001510e17f3c40008',name:'数据管理',open:false,children:[{id:'402881e9510df6e001510e182a99000a',name:'数据导出'},{id:'402881e9510df6e001510e1885a2000c',name:'数据导入'}]},{id:'402881e9510df6e001510e18dd27000e',name:'短信信息',open:false,children:[{id:'402881e9510df6e001510e1938d30010',name:'短信信息查询'}]}]}]}");
		/* User user = (User)request.getSession().getAttribute(Constants.SESSION_USER);
		 if (user==null) {
			 result.put("code","9");//用户未登陆
			 result.put("errorMessage","用户未登陆");
			 return result;
		}*/
		 List<Resource> resources = new ArrayList<Resource>();
		 User user = getBackendUser(request);
		 if(treeType == 1){
         	resources = resourceService.findResourceBy(user.getId(), parentId, ResourceType.MENU);
         } else if(treeType == 2){
         	resources = resourceService.findResourceByParentId(null);
         } else if(treeType == 3){
         	resources = resourceService.findResourceBy(user.getId(), "", ResourceType.MODULE);
         }
		 StringBuffer jsonObj = new StringBuffer();
         if (resources == null || resources.size() == 0) {
             jsonObj = new StringBuffer("{}");
         } else {
         	//保存角色(或用户或用户组)拥有的资源
         	List<Resource> roleOrUserResourceList = new ArrayList<Resource>();
             if(userId != null && !"".equals(userId)){
             	roleOrUserResourceList = resourceService.findResourceByUserId(userId);
             } else 
            	 if(roleId != null && !"".equals(roleId)){
             	roleOrUserResourceList = resourceService.findResourceByRoleId(roleId);
             }
             //获取上下文路径
             String contextPath = request.getContextPath();
             jsonObj.append("{zNodes:[");
             jsonObj.append(buildEditTteeJson(resources,user, treeType, roleOrUserResourceList, contextPath));
             jsonObj.append("]}");
         }
		 result.put("resources", jsonObj);
		return result;
	}
    /**
     * @Description:  组装资源树json
     * @date          2015-11-6  下午12:13:57
     */
    private StringBuffer buildEditTteeJson(List<Resource> resources, User user,Integer treeType, List<Resource> roleOrUserResourceList, String contextPath) throws Exception{
        StringBuffer jsonObj = new StringBuffer();
        for (Resource resource : resources) {
            jsonObj.append("{id:'");
            jsonObj.append(resource.getId());
            jsonObj.append("'");
            jsonObj.append(",name:'");
            jsonObj.append(resource.getName());
            jsonObj.append("'");
            int count = 0;
            if(treeType == 1){
            	count = resourceService.findResourceCountBy(user.getId(), resource.getId(), ResourceType.MENU);
            } else if(treeType == 2){
            	count = resourceService.getResourceCountByParentId(resource.getId()); //
            } else if(treeType == 3){
            	count = resourceService.findResourceCountBy(user.getId(), resource.getId(), null);
            } 
            if(count > 0){
                jsonObj.append(",open:false");
            }
            if(treeType == 1 && resource.getPath() != null && !"".equals(resource.getPath())){
            	jsonObj.append(",url:'").append(contextPath).append(resource.getPath()).append("'"); 
            	jsonObj.append(",target:'mainFrame'"); 
            }
            if(roleOrUserResourceList.contains(resource)) jsonObj.append(",checked: true");
            if(count > 0){
            	List<Resource> childResources = new ArrayList<Resource>();
                if(treeType == 1){
                	 childResources = resourceService.findResourceBy(user.getId(), resource.getId(), ResourceType.MENU);
                } else if(treeType == 2){
                	childResources = resourceService.findResourceByParentId(resource.getId());
                } else if(treeType == 3){
                	childResources = resourceService.findResourceBy(user.getId(), resource.getId(), null);
                }
                jsonObj.append(",children:[").append(buildEditTteeJson(childResources,user, treeType, roleOrUserResourceList, contextPath)).append("]");
            }
            jsonObj.append("},"); 
        }
        jsonObj = jsonObj.deleteCharAt(jsonObj.length() - 1);
        return jsonObj;
    }
}
