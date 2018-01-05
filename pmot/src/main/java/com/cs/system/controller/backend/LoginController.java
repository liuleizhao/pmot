package com.cs.system.controller.backend;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cs.common.annotation.AuthValidate;
import com.cs.common.annotation.UserActionLog;
import com.cs.common.constant.CacheConstant;
import com.cs.common.entityenum.UserLogType;
import com.cs.common.entityenum.UserStatus;
import com.cs.common.utils.CacheUtil;
import com.cs.common.utils.IpUtil;
import com.cs.common.utils.enCrypt.BASE64Encoder;
import com.cs.common.utils.enCrypt.EnCryptUtil;
import com.cs.common.utils.enCrypt.HMACSHA1;
import com.cs.mvc.web.BaseController;
import com.cs.system.entity.GlobalConfig;
import com.cs.system.entity.User;
import com.cs.system.service.GlobalConfigService;
import com.cs.system.service.ResourceService;
import com.cs.system.service.UserService;

/**
 * @描述：用户登录控制类
 * @作者：xuj
 */
@Controller
@RequestMapping(value = "/backend")
public class LoginController extends BaseController{

    /** 序列版本号 */
    private static final long serialVersionUID = 1L;
    
    /** 用户相关服务接口 */
    @Autowired
    private UserService userService;
    @Autowired
    private ResourceService resourceService;
    
    @Autowired
    private GlobalConfigService globalConfigService;
    
    /**
     * @描述：进入登入页面 
     * @作者：肖佳
     * @开发日期：2011-7-7
     * @return
     * @throws Exception
     */
	@AuthValidate(exceptAuth = true)
	@RequestMapping(value = "/login" ,method = RequestMethod.GET)
    public String loginUI(HttpServletRequest request, Model model) throws Exception{
    	return "backend/backend_login";
    }

	/**
     * @描述：验证用户登录，获取用户可见菜单、角色
     * @作者：肖佳
     * @开发日期：2011-6-10
     * @return
     * @throws Exception
     */
	@AuthValidate(exceptAuth = true)
    @UserActionLog(userLogType = UserLogType.LOGIN,description="用户登录")
    @RequestMapping(value = "/login" ,method = RequestMethod.POST)
    public String auth(HttpServletRequest request, Model model,String account,String password) throws Exception{
    	// 盐值
		String salt = EnCryptUtil.getCommKey();
    	User user = userService.checkUser(account, BASE64Encoder.encode(HMACSHA1.getHmacSHA1(salt, password)));
        if(user == null){
            model.addAttribute("errorMessage", "用户不存在！");
			model.addAttribute("account",account);
            return loginUI(request,model);
        }
        String ip = IpUtil.getClientIp(request);
        
        //判断是否开启ip限制，判断是否允许ip登录
        GlobalConfig ipLimit = globalConfigService.findByDataKey("IP_LIMIT");
        if(ipLimit!=null&&"1".equals(ipLimit.getDataValue())){
        	if(user.getIp() == null || user.getIp().indexOf(ip) == -1){
        		model.addAttribute("errorMessage", "非法终端登录！如需登录，请联系管理员！");
        		model.addAttribute("account",account);
        		return loginUI(request,model);
        	}
        }
        
        if(user.getStatus().equals(UserStatus.DISABLE)){
        	model.addAttribute("errorMessage", "该用户已停用");
 			model.addAttribute("account",account);
            return loginUI(request,model);
        }
		// 登陆成功 保存用户登录信息
		// 获得客户端IP
		user.setLastLoginDate(new Date());
		user.setLastLoginIp(ip);
		user.setLoginCount(user.getLoginCount()+1);
		userService.updateByPrimaryKey(user);
		
        CacheUtil.setCacheObject(CacheConstant.BACKEND_USER_CACHE, user, request);
        //强制刷新、并保存用户资源列表
      	resourceService.findAccessUrl(user.getId(), null,  null, true);
        
        String redirectUrl = request.getParameter("redirectUrl");
        if (StringUtils.isNotBlank(redirectUrl)) {
			return forward(redirectUrl);
		} else {
			return redirect("/backend/frame/iframeIndex");
		}
    }
    
    /**
     * @描述：用户退出
     * @作者：肖佳
     * @开发日期：2011-8-3
     * @return
     */
	@AuthValidate(exceptResource = true)
    @RequestMapping(value = "/loginout" ,method = RequestMethod.GET)
    public String loginout(HttpServletRequest request,HttpServletResponse response,Model model) throws Exception{
    	removeBackendUser(request);
    	return loginUI(request,model);
    }
	/**
	 * 进入修改密码页面
	 * @return
	 * @throws Exception
	 */
	@AuthValidate(exceptResource = true)
    @RequestMapping(value = "/enter" ,method = RequestMethod.GET)
	public String enter() throws Exception {
		// 生成用户访问标识
		 return "backend/system/login_change_pwd";
	}
	
	@AuthValidate(exceptResource = true)
    @RequestMapping(value = "/checkPwd" ,method = RequestMethod.GET)
    @ResponseBody
 	public Object checkPwd(Model model,HttpServletRequest request,String password) throws Exception {
    	Map<String, Object> map = new HashMap<String, Object>();
    	String errorMessage = null;
    	if(StringUtils.isBlank(password)){
    		errorMessage = "请输入旧密码！";
    	}
    	String salt = EnCryptUtil.getCommKey();
    	User existUserVO = (User) CacheUtil.getCacheObject(CacheConstant.BACKEND_USER_CACHE, request);
		if(!BASE64Encoder.encode(HMACSHA1.getHmacSHA1(salt,password)).equals(existUserVO.getPassword())) {
			errorMessage = "旧密码错误！";
		}
		map.put("errorMessage", errorMessage);
    	return map;
    }
    
	/**
	 * 修改密码
	 * @return
	 * @throws Exception
	 */
	@AuthValidate(exceptResource = true)
    @RequestMapping(value = "/changePwd" ,method = RequestMethod.POST)
	 @ResponseBody
 	public Object changePwd(Model model,HttpServletRequest request,String password,String newPwd) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String code = "1";
    	if(StringUtils.isBlank(password) || StringUtils.isBlank(newPwd)){
    		errorMessage = "请输入旧密码和新密码！";
    		map.put("errorMessage", errorMessage);
    		map.put("code", "0");
			return map;
    	}
		String salt = EnCryptUtil.getCommKey();
		User existUserVO = (User) CacheUtil.getCacheObject(CacheConstant.BACKEND_USER_CACHE, request);
		if (!BASE64Encoder.encode(HMACSHA1.getHmacSHA1(salt,password)).equals(existUserVO.getPassword())) {
			errorMessage = "旧密码错误！";
    		map.put("errorMessage", errorMessage);
    		map.put("code", "0");
			return map;
		}
		existUserVO.setPassword(BASE64Encoder.encode(HMACSHA1.getHmacSHA1(salt, newPwd)));
		userService.updateByPrimaryKey(existUserVO);
		removeBackendUser(request);
		errorMessage = "修改成功！";
		map.put("errorMessage", errorMessage);
		map.put("code", code);
		return map;
	}
}
