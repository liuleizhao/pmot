package com.cs.system.controller.backend;

import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.servlet.ModelAndView;

import com.cs.argument.entity.Station;
import com.cs.argument.service.StationService;
import com.cs.common.annotation.AuthValidate;
import com.cs.common.annotation.UserActionLog;
import com.cs.common.constant.CacheConstant;
import com.cs.common.entityenum.UserLogType;
import com.cs.common.entityenum.UserStatus;
import com.cs.common.entityenum.UserType;
import com.cs.common.utils.CacheUtil;
import com.cs.common.utils.enCrypt.BASE64Encoder;
import com.cs.common.utils.enCrypt.EnCryptUtil;
import com.cs.common.utils.enCrypt.HMACSHA1;
import com.cs.mvc.dao.SqlCondition;
import com.cs.mvc.web.BaseController;
import com.cs.system.entity.Role;
import com.cs.system.entity.User;
import com.cs.system.service.RoleService;
import com.cs.system.service.UserService;
import com.github.pagehelper.PageInfo;

/**
 * @ClassName: UserController.java
 * @Description: 用户管理控制器
 * @Author xuj
 * @date 2016-10-25 下午5:02:25
 */
@Controller
@RequestMapping(value = "/backend/system/user")
public class UserController extends BaseController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private StationService stationInfoService;
	
	@Autowired
	private RoleService roleService;
	
	private PageInfo<User> pageView;

	/**
	 * @throws Exception
	 * @Description: 查询用户列表
	 */
	@RequestMapping(value = "/list", method=RequestMethod.GET)
	public String list(HttpServletRequest request,
			@ModelAttribute("user") User user , Model model) throws Exception {
		SqlCondition sqlCondition = new SqlCondition();
		if(user != null){
			sqlCondition.addSingleCriterion("account = ", user.getAccount());
			sqlCondition.addLikeCriterion("name like ", user.getName());//专门用于 like语句
			sqlCondition.addLikeCriterion("USER_TYPE = ", user.getUserType());
		}
		sqlCondition.addDescOrderbyColumn("create_date");
		pageView = userService.findByCondition(sqlCondition,getCurrentPage(request),12);
		List<UserType> userTypeList = UserType.getAll();
		model.addAttribute("pageView", pageView);
		model.addAttribute("userTypeList", userTypeList);
		return "/backend/system/user_list";
	}

	/**
	 * @Description: 跳转到添加页面
	 * @param model
	 * @return String
	 * @throws Exception
	 * @date 2016-10-31 下午12:12:33
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addUI(Model model, HttpServletRequest request) throws Exception {
		// 获取检测站信息
		List<Station> stationInfoList = stationInfoService.findAllData();
		List<UserType> userTypeList = UserType.getAll();
		// 获取用户类型
		model.addAttribute("stationInfoList", stationInfoList);
		model.addAttribute("userTypeList", userTypeList);
		//如果当前登录用户为超级管理员获取用户组织列表
		return "backend/system/user_add";
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
	@UserActionLog(userLogType = UserLogType.USER,description="添加用户")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(HttpServletRequest request, Model model, @ModelAttribute("user")User user)
			throws Exception {
		try {
			// 再次验证用户名是否存在
			User checkUser = userService.findUserByAccount(user.getAccount());
			if(null != checkUser)
			{
				model.addAttribute("message", "添加用户【" + user.getName() + "】失败，当前用户名为"+user.getName()+"已经存在】！");
				addUI(model,request);
			}
			if(StringUtils.isNotBlank(user.getStationId())){
				Station station = stationInfoService.selectByPrimaryKey(user.getStationId());
				if(null == station){
					model.addAttribute("message", "添加用户【" + user.getName() + "】失败,未找到对应的检测站信息！");
					addUI(model,request);
				}
				user.setStationName(station.getName());
			}
			// 对密码处理(盐值)
			String salt = EnCryptUtil.getCommKey();
			user.setSalt(salt);
			user.setPassword(BASE64Encoder.encode(HMACSHA1.getHmacSHA1(salt,user.getPassword())));
			user.setCreator(getBackendUser(request).getId());
			userService.insert(user);
			model.addAttribute("message", "添加用户【" + user.getName() + "】成功！");
			return list(request, user, model);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "添加用户【" + user.getName() + "】失败！");
			return addUI(model,request);
		}
	}

	/**
	 * @Description: 跳转到添加页面
	 * @param model
	 * @return String
	 * @throws Exception
	 * @date 2016-10-31 下午12:12:33
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String editUI(String userId, Model model,HttpServletRequest request) throws Exception {
		User user = userService.selectByPrimaryKey(userId);
		// 获取检测站信息
		List<Station> stationInfoList = stationInfoService.findAllData();
		List<UserType> userTypeList = UserType.getAll();
		List<UserStatus> userStatusList  = UserStatus.getAll();
		// 获取用户类型
		model.addAttribute("userStatusList", userStatusList);
		model.addAttribute("stationInfoList", stationInfoList);
		model.addAttribute("userTypeList", userTypeList);
		// 获取用户类型
		model.addAttribute("userVO", user);
		return "backend/system/user_edit";
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
	@UserActionLog(userLogType = UserLogType.USER,description="编辑用户")
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String edit(HttpServletRequest request,Model model,User user) throws Exception {
		try {
			// 获取当前要修改的用户
			User currentLoginUser = userService.selectByPrimaryKey(user.getId());
			if(null == currentLoginUser)
			{
				model.addAttribute("message","修改用户信息失败，未找到对应的用户信息！");
				return list(request,null, model);
			}
			if(StringUtils.isNotBlank(user.getStationId())){
				Station station = stationInfoService.selectByPrimaryKey(user.getStationId());
				if(null == station){
					model.addAttribute("message", "修改用户信息【" + user.getName() + "】失败,未找到对应的检测站信息！");
					return editUI(user.getId(), model,request);
				}
				user.setStationName(station.getName());
			}
			user.setModificator(getBackendUser(request).getId());
			user.setModifyDate(new Date());
			userService.updateByPrimaryKeySelective(user);
			model.addAttribute("message","修改用户【"+user.getName()+"】成功！");
			return list(request,user, model);
		}catch (Exception e) {
			 e.printStackTrace();
			 model.addAttribute("message","修改用户【"+user.getName()+"】失败！");
			 return editUI(user.getId(), model,request);
		}
	}

	/**
	 * @Description: 跳转到角色授权页面
	 * @param model
	 * @return String
	 * @throws Exception
	 * @date 2016-10-31 下午12:12:33
	 */
	@RequestMapping(value = "/userRoleAuthorize", method = RequestMethod.GET)
	public String userRoleAuthorizeUI(HttpServletRequest request,
			String userId, Model model) throws Exception {
		// 授权的用户
		User user = userService.selectByPrimaryKey(userId);
		User currentUser = (User) CacheUtil.getCacheObject(CacheConstant.BACKEND_USER_CACHE, request);
		// 这里判断不能操作超级管理员的权限
		/*
		 * User sessionUser = (User)
		 * request.getSession().getAttribute(Constants.SESSION_USER); if
		 * ("admin".equals(user.getAccount()) &&
		 * !"admin".equals(sessionUser.getAccount())) {
		 * model.addAttribute("errorMessage", "您无权操作【超级系统管理员】！"); return
		 * list(request, null, model); }
		 */
		// 被授权用户已有的角色
		List<Role> userRoleList = roleService.findRoleByUserId(userId);
		// 查询可授权的角色（即用户组织所拥有的角色）
		List<Role> roleList = roleService.findAllData();
		model.addAttribute("userRoleList", userRoleList);
		model.addAttribute("roleList", roleList);
		model.addAttribute("user", user);
		return "backend/system/user_role_authorize";
	}

	/**
	 * @Description: 保存角色授权信息
	 * @param model
	 * @return String
	 * @throws Exception
	 * @date 2016-10-31 下午12:12:33
	 */
	@UserActionLog(userLogType = UserLogType.AUTHORITY,description="角色授权")
	@RequestMapping(value = "/userRoleAuthorize", method = RequestMethod.POST)
	public String userRoleAuthorize(HttpServletRequest request,String[] oldRoleIds,
			String[] newRoleIds, String userId, Model model) throws Exception {
		// 授权的用户
		User user = userService.selectByPrimaryKey(userId);
		if (null != user) {
			if (null==newRoleIds)
				newRoleIds=new String[]{};
			if (null==oldRoleIds)
				oldRoleIds=new String[]{};
			userService.saveResourceByRoleIds(userId,newRoleIds, oldRoleIds);
			model.addAttribute("message", "为用户【" + user.getAccount()
					+ "】通过角色授权成功！");
			return list(request, null, model);
		} else {
			model.addAttribute("message", "未找到用户不能授权！");
		}
		return "backend/system/user_role_authorize";
	}

	/**
	 * @Description: 跳转到资源授权页面
	 * @param model
	 * @return String
	 * @throws Exception
	 * @date 2016-10-31 下午12:12:33
	 */
	@RequestMapping(value = "/userResourceAuthorize", method = RequestMethod.GET)
	public ModelAndView userResourceAuthorizeUI(HttpServletRequest request,
			String userId, Model model) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		// 授权的用户
		User user = userService.selectByPrimaryKey(userId);
		model.addAttribute("user", user);

		modelAndView.addObject("user", user);
		modelAndView.setViewName("backend/system/user_resource_authorize");
		return modelAndView;
	}
	

	/**
	 * @Description: 用户资源授权写入数据库
	 * @param model
	 * @return String
	 * @throws Exception
	 * @date 2016-10-31 下午12:12:33
	 */
	@UserActionLog(userLogType = UserLogType.AUTHORITY,description="资源授权")
	@RequestMapping(value = "/userResourceAuthorize", method = RequestMethod.POST)
	public String userResourceAuthorize(HttpServletRequest request,
			String resourceIds, String userId, Model model) throws Exception {
		User user = userService.selectByPrimaryKey(userId);
		if (null != user) {
			//userService.saveResourceByResourceIds(userId, resourceIds);
			model.addAttribute("message", "为用户【" + user.getAccount()
					+ "】通过资源授权成功！");
			return list(request, user, model);
		} else {
			model.addAttribute("message", "未找到用户不能授权！");
			return "backend/system/user_resource_authorize";
		}
	}

	/**
	 * @Description: 根据用户名判断用户是否存在
	 * @param model
	 * @return String
	 * @throws Exception
	 * @date 2016-10-31 下午12:12:33
	 */
	@RequestMapping(value = "/checkAccount")
	@ResponseBody
	public Object checkAccount(HttpServletRequest request, String account)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isBlank(account)) {
			map.put("isBlank", true);
		} else {
			map.put("isBlank", false);
			User user = userService.findUserByAccount(java.net.URLDecoder.decode(
					java.net.URLDecoder.decode(account, "UTF-8"), "UTF-8"));
			Boolean checkResult = false;
			if (user != null) {
				checkResult = true;
			}
			map.put("isExist", checkResult);
		}
		return map;
	}
	/**
	 * @Description: 跳转到修改密码页面
	 * @param model
	 * @return String
	 * @throws Exception
	 * @date 2016-10-31 下午12:12:33
	 */
	@AuthValidate(exceptResource=true)
	@RequestMapping(value = "/changePwdUI", method = RequestMethod.GET)
	public String changePwdUI(Model model) throws Exception {
		return "backend/system/change_pwd";
	}
	/**
	 * @Description: 修改密码
	 * @param model
	 * @return String
	 * @throws Exception
	 * @date 2016-11-03 下午14:12:33
	 */
	@AuthValidate(exceptResource=true)
	@UserActionLog(userLogType = UserLogType.USER,description="修改用户密码")
	@RequestMapping(value = "/changePwd", method = RequestMethod.POST)
	public String changePwd(HttpServletRequest request, String oldPassword,
			String newPassword, Model model) throws Exception {
		String salt = EnCryptUtil.getCommKey();
		// 获取登录用户
		User currentUser = (User) CacheUtil.getCacheObject(CacheConstant.CLIENT_USER_CACHE, request);
		if (!BASE64Encoder.encode(HMACSHA1.getHmacSHA1(salt, oldPassword))
				.equals(currentUser.getPassword())) {
			model.addAttribute("message", "原密码不正确");
			return changePwdUI(model);
		}
		currentUser.setPassword(BASE64Encoder.encode(HMACSHA1.getHmacSHA1(salt,
				newPassword)));
		userService.updateByPrimaryKeySelective(currentUser);
		model.addAttribute("message", "更新用户密码成功");
		return changePwdUI(model);
	}
	
	@UserActionLog(userLogType = UserLogType.USER,description="删除用户")
	@RequestMapping(value = "/delete")
	public String delete(String userId,Model model,HttpServletRequest request) throws Exception{
		if(StringUtils.isNotBlank(userId)){
			User user = userService.selectByPrimaryKey(userId);
			if(user == null){
				addErrorMessage("删除失败，未找到该用户");
			}else{
				userService.deleteByPrimaryKey(userId);
				addMessage("删除用户【"+user.getName()+"】成功");
			}
		}else{
			addErrorMessage("删除失败，传入参数为空！");
		}
		return list(request,null,model);
	}
	
}
