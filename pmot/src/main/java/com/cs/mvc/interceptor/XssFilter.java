package com.cs.mvc.interceptor;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class XssFilter implements Filter {
	   @Override  
	    public void init(FilterConfig filterConfig) throws ServletException {  
	          
	    }  
	  
	    @Override  
	    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)  
	            throws IOException, ServletException {  
	    	String requestURI = ((HttpServletRequest) request).getRequestURI();
	    	if(requestURI.contains("/backend/api/")){
	 	        chain.doFilter(request , response);
	    	}else{
	    		XsslHttpServletRequestWrapper xssRequest = new XsslHttpServletRequestWrapper((HttpServletRequest)request);  
	 	        chain.doFilter(xssRequest , response);  
	    	}
	    }  
	      
	    @Override  
	    public void destroy() {  
	          
	    } 
}