package com.cs.mvc.web;

import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.cs.common.constant.CacheConstant;
import com.cs.common.utils.CacheUtil;
import com.cs.common.utils.DateUtils;
import com.cs.common.utils.mapper.JsonMapper;
import com.cs.system.entity.User;


/**
 * Controller基类
 * 
 * @author vincent
 * 
 */
public class BaseController {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	protected String errorMessage;
	
	@Autowired
	private HttpServletRequest currentRequest;
	/**
	 * 重定向
	 * @param uri
	 * @return
	 */
	protected String redirect(String uri) {
		return "redirect:" + uri;
	}

	/**
	 * 请求转发
	 * @param uri
	 * @return
	 */
	protected String forward(String uri) {
		return "forward:" + uri;
	}

	
	/**
	 * 客户端返回JSON字符串
	 * @param response
	 * @param object
	 * @return
	 */
	protected String renderString(HttpServletResponse response, Object object) {
		return renderString(response, JsonMapper.toJsonString(object), "application/json");
	}
	
	/**
	 * 客户端返回字符串
	 * @param response
	 * @param string
	 * @return
	 */
	protected String renderString(HttpServletResponse response, String string, String type) {
		try {
			response.reset();
	        response.setContentType(type);
	        response.setCharacterEncoding("utf-8");
			response.getWriter().print(string);
			return null;
		} catch (IOException e) {
			return null;
		}
	}
	
	/**
	 * 初始化数据绑定
	 * 1. 将所有传递进来的String进行HTML编码，防止XSS攻击
	 * 2. 将字段中Date类型转换为String类型
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		// String类型转换，将所有传递进来的String进行HTML编码，防止XSS攻击
		binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(text == null ? null : StringEscapeUtils.escapeHtml4(text.trim()));
			}
			@Override
			public String getAsText() {
				Object value = getValue();
				return value != null ? value.toString() : "";
			}
		});
		// Date 类型转换
		binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(DateUtils.parseDate(text));
			}
			@Override
			public String getAsText() {
				Object value = getValue();
				return value != null ? DateUtils.formatDateTime((Date)value) : "";
			}
		});
		
	}
	
	/**
	 * 获得当前后端用户
	 * @param request
	 * @return
	 */
	protected User getBackendUser(HttpServletRequest request){
		User currentUser = null;
		try {
			currentUser =  (User) CacheUtil.getCacheObject(CacheConstant.BACKEND_USER_CACHE, request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return currentUser;
	}
	
	/**
	 * 删除当前登录的后端用户
	 * @param request
	 */
	protected void removeBackendUser(HttpServletRequest request){
		
		try {
			CacheUtil.removeCacheObject(CacheConstant.BACKEND_USER_CACHE, request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 获得当前页码
	 * @param request
	 * @return
	 */
	protected Integer getCurrentPage(HttpServletRequest request) {
		String currentPage = request.getParameter("currentPage");
		if (StringUtils.isNotBlank(currentPage)) {
			return Integer.parseInt(currentPage);
		} else {
			return 1;
		}
	}

	/**
	 * 获得当前页码
	 * @param currentPage
	 * @return
	 */
	protected Integer getCurrentPage(Integer currentPage) {
		if (currentPage > 0) {
			return currentPage+1;
		} else {
			return 1;
		}
	}
	
	/**
	 * 获取当前页面显示条数
	 * @param request
	 * @return
	 * @date 2017-3-2 上午10:06:26
	 */
	protected Integer getCurrentPageSize(HttpServletRequest request) {
		String pageSize = request.getParameter("pageSize");
		if (StringUtils.isNotBlank(pageSize)) {
			return Integer.parseInt(pageSize);
		} else {
			return 12;
		}
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	/**
	 * 向Request中添加提示消息
	 * @param message
	 * @Author        succ
	 * @date 2017-11-23 上午11:56:06
	 */
	protected void addMessage(String message){
		currentRequest.setAttribute("message", message);
	}
	
	/**
	 * 向Request中添加错误提示
	 * @param errorMessage
	 * @Author        succ
	 * @date 2017-11-23 上午11:56:24
	 */
	protected void addErrorMessage(String errorMessage){
		currentRequest.setAttribute("errorMessage", errorMessage);
	}
	
}
