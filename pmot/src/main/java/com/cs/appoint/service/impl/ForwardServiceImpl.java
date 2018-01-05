package com.cs.appoint.service.impl;

import java.util.Random;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.encoding.XMLType;

import org.apache.axis.client.Call;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cs.appoint.service.ForwardService;
import com.cs.mvc.init.InitData;
import com.cs.system.entity.GlobalConfig;
import com.cs.system.service.GlobalConfigService;

@Service
@Transactional
public class ForwardServiceImpl implements ForwardService{

	@Autowired
	GlobalConfigService configService;
	
	@Override
	public String getCarInfo(String queryXmlDoc) throws Exception{
		//200.201.38.200$200.201.29.200$200.201.24.200
		//String url = "http://200.201.24.200/HSWeb/VehicleCheck.asmx";//接口调用URL
		
		String url = null;
		String urlStr = InitData.getGlobalValue("FORWARD_IP");
		if(urlStr!=null){
			String[] urlArr = urlStr.split("\\$");
			Random random=new Random();// 定义随机类 
			int result=random.nextInt(urlArr.length);
			url = "http://"+urlArr[result]+"/HSWeb/VehicleCheck.asmx";
		}
		String  namespace = "http://tempuri.org/";//接口命名空间
		String responseXml = null;
		try {
			org.apache.axis.client.Service service = new org.apache.axis.client.Service();
			Call call =  (Call) service.createCall() ;
			call.setTargetEndpointAddress(new java.net.URL(url));
			call.setUseSOAPAction(true);
			call.setSOAPActionURI(namespace + "UploadObjectOut");
			call.setOperationName(new QName(namespace,"UploadObjectOut" ));// 访问地址

			call.addParameter(new QName(namespace, "nSignal"),
					XMLType.XSD_STRING, ParameterMode.IN);
			call.addParameter(new QName(namespace, "strXml"), XMLType.XSD_STRING,
					ParameterMode.IN);
			call.addParameter(new QName(namespace, "strIP"), XMLType.XSD_STRING,
					ParameterMode.IN);
            call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING); // 要返回的数据类型
			Object[] params = new Object[] { "306",queryXmlDoc,null};
			
			responseXml=(String)call.invoke(params); 
		} catch (Exception e) {
			e.printStackTrace();
			responseXml=e.getMessage();
		}
		return responseXml;
	}
}
