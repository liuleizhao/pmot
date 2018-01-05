package com.cs.mvc.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cs.common.annotation.UserActionLog;
import com.cs.common.constant.CacheConstant;
import com.cs.common.utils.CacheUtil;
import com.cs.system.entity.User;
/*import com.cs.system.entity.UserLog;
import com.cs.system.service.UserLogService;*/


/**
 * 日志拦截器
 * 
 * @author vincent
 * 
 */
public class LogInterceptor extends HandlerInterceptorAdapter {

/*	@Autowired
	private UserLogService userLogService;*/
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		return true;
	}

	/**
	 * 拦截记录用户操作
	 * @author llz
	 */
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		UserActionLog userActionLog = null;
		
		if (handler instanceof HandlerMethod) {
			Method method = ((HandlerMethod) handler).getMethod();
			userActionLog = method.getAnnotation(UserActionLog.class);
		}
		if (userActionLog != null) {
			User user = (User) CacheUtil.getCacheObject(CacheConstant.BACKEND_USER_CACHE, request);
			if(user != null){
				/*UserLog userLog = new UserLog();
				userLog.setUserId(user.getId());
				userLog.setDescription(userActionLog.description());
				userLog.setType(userActionLog.userLogType());
				userLogService.SaveLog(userLog);*/
			}
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		super.afterCompletion(request, response, handler, ex);
	}

}
