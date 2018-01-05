package com.cs.mvc.init;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

import com.cs.webservice.entity.InterParamRelation;
import com.cs.webservice.entity.InterfaceInfo;
import com.cs.webservice.service.InterfaceInfoService;

public class WebContextListener extends ContextLoaderListener{
	private static final Map<String,Object> map = new HashMap<String,Object>();
	
	@Autowired
	private InterfaceInfoService interfaceInfoService;
	
	@Override
	public WebApplicationContext initWebApplicationContext(
			ServletContext servletContext) {
		try {
			List<InterfaceInfo> list = interfaceInfoService.findAllData();
			if(CollectionUtils.isNotEmpty(list)){
				for(InterfaceInfo interfaceInfo : list){
					List<InterParamRelation> params = 
							interfaceInfoService.findParameters(interfaceInfo.getId());
					interfaceInfo.setParams(params);
				}
			}
			
			if(CollectionUtils.isNotEmpty(list)){
				for(InterfaceInfo interfaceInfo : list){
					System.out.println("方法名:"+interfaceInfo.getMethodName());
					for(InterParamRelation relation : interfaceInfo.getParams()){
						System.out.println(relation.getParameter().getName());
					}
					System.out.println("------------------------------------------------------");
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("----------------------------------");
		System.out.println("我只是测试测试");
		System.out.println("我只是测试测试");
		System.out.println("我只是测试测试");
		System.out.println("我只是测试测试");
		System.out.println("我只是测试测试");
		System.out.println("我只是测试测试");
		System.out.println("我只是测试测试");
		return super.initWebApplicationContext(servletContext);
	}
			
}
