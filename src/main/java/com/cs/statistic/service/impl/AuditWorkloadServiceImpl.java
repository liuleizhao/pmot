package com.cs.statistic.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cs.statistic.dao.AuditWorkloadStatisticDao;
import com.cs.statistic.dto.AuditWorkloadDto;
import com.cs.statistic.entity.AuditWorkloadStatistic;
import com.cs.statistic.service.AuditWorkloadService;

@Service
@Transactional
public class AuditWorkloadServiceImpl implements AuditWorkloadService {
	
	@Autowired
	private AuditWorkloadStatisticDao auditWorkloadDao;
	
	@Override
	public List<AuditWorkloadStatistic> list(AuditWorkloadDto auditWorkloadDto) {
		if(auditWorkloadDto.getAuditDate() == null){
			auditWorkloadDto.setAuditDate(new Date());
		}
		return auditWorkloadDao.list(auditWorkloadDto);
	}

	@Override
	public String aMethod(String argsXML) {
		if(StringUtils.isBlank(argsXML)){
			return "";
		}
		Document document = null;
		try {
			document = DocumentHelper.parseText(argsXML);
		} catch (DocumentException e) {
			e.printStackTrace();
			return "XML格式错误";
		}
		AuditWorkloadDto auditWorkloadDto = new AuditWorkloadDto(); 
		Element root = document.getRootElement();
		Element auditDate = root.element("auditDate");
		if(auditDate == null || StringUtils.isBlank(auditDate.getTextTrim())){
			return "存在非空参数为空! 参数名：auditDate";
		}else{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date d = null;
			try{
				d = sdf.parse(auditDate.getTextTrim());
			}catch(ParseException e){
				return "无效参数！参数名：auditDate，日期格式为 年份-月份-日期";
			}
			auditWorkloadDto.setAuditDate(d);
		}
		Element auditType = root.element("auditType");
		if(auditType!=null && StringUtils.isNotBlank(auditType.getTextTrim())){
			try{
				Integer type = Integer.valueOf(auditType.getTextTrim());
				if(type!=1 && type!=2){
					return "无效参数！参数名：auditType";
				}
				auditWorkloadDto.setAuditType(type);
			}catch(NumberFormatException e){
				return "无效参数！参数名：auditType，参数类型为整形";
			}
		}
//		return XmlUtil.getSuccessResponseXml(this.list(auditWorkloadDto));
		return "成功并返回数据";
	}

}
