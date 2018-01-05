package com.cs.mvc.interceptor;

import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cs.common.annotation.AuthValidate;
import com.cs.common.constant.CacheConstant;
import com.cs.common.utils.CacheUtil;
import com.cs.system.entity.User;
import com.cs.system.service.ResourceService;

/**
 * 后台权限拦截器
 * 
 * @author vincent
 * 
 */
public class BackendPermissionInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private ResourceService resourceService;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		AuthValidate authValidate = null;

		if (handler instanceof HandlerMethod) {
			Method method = ((HandlerMethod) handler).getMethod();
			authValidate = method.getAnnotation(AuthValidate.class);
		}

		if (authValidate != null && authValidate.exceptAuth()) {
			return true;
		}

		// 获取请求方式
		String reqType = request.getHeader("X-Requested-With");

		// 获取登录用户
 		User user = (User) CacheUtil.getCacheObject(CacheConstant.BACKEND_USER_CACHE, request);

		if (user == null) {
			if ("XMLHttpRequest".equals(reqType)) {
				// AJAX被拦截，返回字符串提示
				response.setContentType("text/html;charset=UTF-8");
				response.getWriter().write("用户未登录,请登录系统后操作!");
				return false;
			} else {
				// 被拦截，重定向到login界面
				response.sendRedirect(request.getContextPath()+ "/backend/login");
				return false;
			}
		}

		if (authValidate != null && authValidate.exceptResource()) {
			return true;
		}
		
		// 如果是系统管理员无须拦截
/*		if (user.getIsAdmin() == 1) {
			return true;
		}*/
		// 获取请求URL和Method方式
		String requestUri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String url = requestUri.substring(contextPath.length());
		String methodType = request.getMethod();

		List<String> accessUrls = resourceService.findAccessUrl(user.getId(),
				null, null , false);
		 if (accessUrls != null) {
			if (accessUrls.contains(url + "&" + methodType)) {
				return true;
			} else {
				if ("XMLHttpRequest".equals(reqType)) {
					response.setContentType("text/html;charset=UTF-8");
					response.getWriter().write("您没有权限。请与管理员联系!");
					return false;
				} else {
					request.setAttribute("msg", "对不起，您没有访问权限");
					request.getRequestDispatcher("/WEB-INF/views/error/403.jsp")
							.forward(request, response);
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		super.afterCompletion(request, response, handler, ex);
	}

}
