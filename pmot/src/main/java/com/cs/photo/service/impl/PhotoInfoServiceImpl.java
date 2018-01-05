package com.cs.photo.service.impl;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.axis.MessageContext;
import org.apache.axis.transport.http.HTTPConstants;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cs.common.entityenum.OrgType;
import com.cs.common.utils.IpUtil;
import com.cs.common.utils.webservice.ResultCode;
import com.cs.common.utils.webservice.WebServiceXmlUtil;
import com.cs.common.utils.webservice.XmlUtil;
import com.cs.photo.dao.CheckStationBusinessDao;
import com.cs.photo.dao.PhotoInfoDao;
import com.cs.photo.dao.ServiceStationBusinessDao;
import com.cs.photo.dto.PhotoInfoDto;
import com.cs.photo.entity.CheckStationBusiness;
import com.cs.photo.entity.PhotoInfo;
import com.cs.photo.entity.ServiceStationBusiness;
import com.cs.photo.service.PhotoInfoService;

@Service
@Transactional
public class PhotoInfoServiceImpl implements PhotoInfoService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    public static final Properties allowAccessPhotoType = new Properties();
    static{
    	try {
			allowAccessPhotoType.load(new InputStreamReader(PhotoInfoServiceImpl.class.getResourceAsStream("/photoType.properties"), "utf-8"));
		} catch (Exception e) {
			System.out.println("照片种类读取失败！");
		}
    }
    @Autowired  
    private ApplicationContext applicationContext;
    @Autowired
    private PhotoInfoDao photoInfoDao;
    @Autowired
    private CheckStationBusinessDao checkStationBusinessDao;
    @Autowired
    private ServiceStationBusinessDao serviceStationBusinessDao;

	@Override
	public List<PhotoInfo> list(PhotoInfoDto photoInfo) {
		Set<Object> set = allowAccessPhotoType.keySet();
		List<String> allowAccessPhotoTypes = new ArrayList<String>();
		if(StringUtils.isBlank(photoInfo.getPhotoType()) && set!=null){
			for(Object obj : set){
				allowAccessPhotoTypes.add((String)obj);
			}
		}
		return photoInfoDao.list(photoInfo,allowAccessPhotoTypes);
	}

	@Override
	public String getPhotoByXML(String argsXML) {
		if(StringUtils.isBlank(argsXML)){
			return WebServiceXmlUtil.buildResponseXml(ResultCode.FAIL,"服务器端提示信息：XML格式错误");
		}
		String clientIp = IpUtil.getClientIp(getRequest());
		if(!validateIp(clientIp)){
			return WebServiceXmlUtil.buildResponseXml(ResultCode.FAIL, "服务器端提示信息：当前IP无接口访问权限");
		}
		Document document = null;
		try {
			document = DocumentHelper.parseText(argsXML);
		} catch (DocumentException e) {
			e.printStackTrace();
			return WebServiceXmlUtil.buildResponseXml(ResultCode.FAIL,"服务器端提示信息：XML格式错误");
		}
		PhotoInfoDto photoInfoDto = new PhotoInfoDto();
		Element root = document.getRootElement();
		Element plateNumber = root.element("plateNumber");
		if(plateNumber == null || StringUtils.isBlank(plateNumber.getTextTrim())){
			return WebServiceXmlUtil.buildResponseXml(ResultCode.FAIL,"服务器端提示信息：【"+plateNumber+"】参数不能为空");
		}else{
			photoInfoDto.setPlateNumber(plateNumber.getTextTrim());
		}
		Element photoType = root.element("photoType");
		if(photoType != null && StringUtils.isNotBlank(photoType.getTextTrim())){
			if(allowAccessPhotoType.getProperty(photoType.getTextTrim()) == null){
				return WebServiceXmlUtil.buildResponseXml(ResultCode.FAIL, "服务器端提示信息：照片种类不存在或不在访问权限内");
			}
			photoInfoDto.setPhotoType(photoType.getTextTrim());
		}
		Element plateType = root.element("plateType");
		if(plateType != null && StringUtils.isNotBlank(plateType.getTextTrim())){
			photoInfoDto.setPlateType(plateType.getTextTrim());
		}
		
		CheckStationBusiness checkStationLatest = null;
		ServiceStationBusiness serviceStationLatest = null;
		//从检测站流水信息表中查询最新一条记录
		CheckStationBusiness checkStationBusiness = new CheckStationBusiness();
		checkStationBusiness.setPlateNumber(photoInfoDto.getPlateNumber());
		checkStationBusiness.setPlateType(photoInfoDto.getPlateType());
		checkStationLatest = checkStationBusinessDao.findLatestByCondition(checkStationBusiness);
		photoInfoDto.setOrgType(OrgType.CHECK_STATION.getId());
		//如果从检测站流水信息表中查询不到记录，从服务站流水信息表中查找最新一条记录
		if(checkStationLatest == null){
			ServiceStationBusiness serviceStationBusiness = new ServiceStationBusiness();
			serviceStationBusiness.setPlateNumber(photoInfoDto.getPlateNumber());
			serviceStationBusiness.setPlateType(photoInfoDto.getPlateType());
			serviceStationLatest = serviceStationBusinessDao.findLatestByCondition(serviceStationBusiness);
			photoInfoDto.setOrgType(OrgType.SERVICE_STATION.getId());
		}
		if(checkStationLatest==null && serviceStationLatest==null){
			return WebServiceXmlUtil.buildResponseXml(ResultCode.SUCCESS,"服务器端提示信息：没有找到相关记录");
		}else{
			try{
				List<PhotoInfo> data = this.list(photoInfoDto);
				String bodyXML = WebServiceXmlUtil.buildXmlFromList(data, PhotoInfo.class);
				return WebServiceXmlUtil.buildResponseXml(ResultCode.SUCCESS, "服务器端提示信息：照片获取成功", bodyXML);
			}catch(Exception e){
				e.printStackTrace();
				return WebServiceXmlUtil.buildResponseXml(ResultCode.SYSTEM_ERROR);
			}
		}
		
	}

	private static String[] ipWhiteList;
	private boolean validateIp(String clientIp){
		if(ipWhiteList == null){
			Properties ips = new Properties();
			try {
				ips.load(new InputStreamReader(PhotoInfoServiceImpl.class.getResourceAsStream("/ip_whiteList.properties"), "utf-8"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			String tmp = ips.getProperty("whiteList");
			if(StringUtils.isNoneBlank(tmp)){
				ipWhiteList = tmp.split(",");
			}else{
				ipWhiteList = new String[]{};
			}
		}
		
		for(String ip : ipWhiteList){
			if(clientIp.equals(ip)){
				return true;
			}
		}
		return false;
	}

	private HttpServletRequest getRequest() {
		MessageContext mc = MessageContext.getCurrentContext();
		if (mc == null) {
			System.out.println("无法获取到MessageContext");
			return null;
		}
		HttpServletRequest request = (HttpServletRequest) mc
				.getProperty(HTTPConstants.MC_HTTP_SERVLETREQUEST);
		return request;
	}
}
