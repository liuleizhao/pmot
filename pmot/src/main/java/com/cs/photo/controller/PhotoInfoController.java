package com.cs.photo.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.encoding.XMLType;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cs.appoint.entity.BookInfoSupervise;
import com.cs.common.utils.xml.XmlBeanUtil;
import com.cs.mvc.web.BaseController;
import com.cs.photo.dto.PhotoInfoDto;
import com.cs.photo.entity.PhotoInfo;
import com.cs.photo.service.PhotoInfoService;

@Controller
@RequestMapping("/photoInfo")
public class PhotoInfoController extends BaseController {
	@Autowired
	private PhotoInfoService photoInfoService;
	
	private static final String targetAddress = "http://localhost:8080/pmot/services/TmriOutAccess";
	private Call createCall() throws Exception{
		Service service = new Service();
		Call call = (Call) service.createCall();
		call.setTargetEndpointAddress(new URL(targetAddress));
		call.setUseSOAPAction(true);
		call.setSOAPActionURI("http://service.myssm.com/queryObjectOut");
		call.setOperationName(new QName("http://service.myssm.com/","queryObjectOut"));
		call.addParameter("in0", XMLType.XSD_STRING, ParameterMode.IN);
//		call.addParameter("in1", XMLType.XSD_STRING, ParameterMode.IN);
//		call.addParameter("in2", XMLType.XSD_STRING, ParameterMode.IN);
//		call.addParameter("in3", XMLType.XSD_STRING, ParameterMode.IN);
		call.setReturnType(XMLType.XSD_STRING);// 返回值类型
		return call;
	}
	
	@RequestMapping("/testPhoto")
	public void testPhotoInfo(PhotoInfoDto photoInfoDto) throws IOException{
		FileOutputStream out = null;
		try {
			Call call = createPhotoCall();
			StringBuilder temp = new StringBuilder("<?xml version=\"1.0\" encoding=\"utf-8\"?><root>");
			if(StringUtils.isNotBlank(photoInfoDto.getPlateNumber())){
				temp.append("<plateNumber>"+photoInfoDto.getPlateNumber()+"</plateNumber>");
			}
			if(StringUtils.isNoneBlank(photoInfoDto.getPhotoType())){
				temp.append("<photoType>"+photoInfoDto.getPhotoType()+"</photoType>");
			}
			if(StringUtils.isNoneBlank(photoInfoDto.getPlateType())){
				temp.append("<plateType>"+photoInfoDto.getPlateType()+"</plateType>");
			}
			temp.append("</root>");
			String result = (String)call.invoke(new Object[]{temp.toString()});
			System.out.println(result);
			
			Document doc = DocumentHelper.parseText(result);
			Element root = doc.getRootElement();
			Element bodyEle = root.element("body");
			List<Element> data = bodyEle.elements("vehispara");
			if(!CollectionUtils.isEmpty(data)){
				int counter = 0;
				for(Element e : data){
					PhotoInfo tmp = XmlBeanUtil.xmlToBean(e.asXML().replaceAll("vehispara", "PhotoInfo"), PhotoInfo.class);
//					byte[] s = e.elementText("photo");
//					out.write(s.getByte());
					out = new FileOutputStream("e:\\"+counter+".jpg");
					out.write(tmp.getPhoto());
					out.flush();
					out.getFD().sync();
					counter++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(out != null){
				out.close();
			}
		}
	} 
	
	private static final String targetAddress_photo = "http://localhost:8080/pmot/services/PhotoInfoService";
	private Call createPhotoCall() throws Exception{
		Service service = new Service();
		Call call = (Call) service.createCall();
		call.setTargetEndpointAddress(new URL(targetAddress_photo));
		call.setUseSOAPAction(true);
		call.setSOAPActionURI("http://service.myssm.com/getPhoto");
		call.setOperationName(new QName("http://service.myssm.com/","getPhoto"));
		call.addParameter("in0", XMLType.XSD_STRING, ParameterMode.IN);
		call.setReturnType(XMLType.XSD_STRING);// 返回值类型
		return call;
	}
	
	@RequestMapping("/test2")
	public String test() throws Exception{
//		String str = "<?xml version=\"1.0\" encoding=\"GBK\"?><root><tables><table>" +
//				"<name>BOOK_INFO_SCC</name><objs>" +
//				"<obj><ID>5C84D604AEE96199E0536B01A8C00029</ID>" +
//				"<CAR_TYPE_ID>e4e48584399473d20139947fff4e2b2e</CAR_TYPE_ID>" +
//				"<PLAT_NUMBER>粤1234567</PLAT_NUMBER>" +
//				"<FRAME_NUMBER>dsdsd</FRAME_NUMBER>" +
//				"<MOBILE>1852647515</MOBILE>" +
//				"<BOOK_DATE>2017-11-02</BOOK_DATE>" +
//				"<BOOK_TIME>09:00-12:00</BOOK_TIME>" +
//				"<STATION_ID>e4e48584399473d201399b0da7b72b3b</STATION_ID>" +
//				"<BOOK_STATE>1</BOOK_STATE>" +
//				"<BOOK_NUMBER>12345</BOOK_NUMBER>" +
//				"<CREATE_DATE>2017-10-30 16:42:39</CREATE_DATE>" +
//				"</obj></objs></table></tables></root>";	
//		String str = "<?xml version=\"1.0\" encoding=\"GBK\"?><root><tables><table><name>BOOK_INFO_SCC</name><objs><obj><id>5C84D604AEE96199E0536B01A8C00029</id><carTypeId>e4e48584399473d20139947fff4e2b2e</carTypeId><platNumber>粤BDSD</platNumber><frameNumber>dsdsd</frameNumber><mobile>1852647515</mobile><bookDate>2017-11-02</bookDate><bookTime>09:00-12:00</bookTime><stationCode>e4e48584399473d201399b0da7b72b3b</stationCode><bookState>1</bookState><bookNumber>12345</bookNumber><createDate>2017-10-30 16:42:39</createDate></obj></objs></table></tables></root>";
		//<platNumber>粤BSD121</platNumber>
		String str = "<?xml version=\"1.0\" encoding=\"GBK\"?><root><head><xtlb>18</xtlb><jkid>JK01</jkid><jkxlh>123</jkxlh></head><QueryCondition><platNumber>粤DSDSD</platNumber><frameNumber>JHJHFCXCDSF</frameNumber><stationCode>4400000157</stationCode><carType>402881e44e345afa014e345c4a700001</carType></QueryCondition></root>";
		
		String result = (String)createCall().invoke(new Object[]{URLEncoder.encode(str,"utf-8")});
		System.out.println(result);
		
		/*SynchronizeEntity record = new SynchronizeEntity();
		record.setTableName("BOOK_INFO_SCC");
		List<String> cols = new ArrayList<String>();
		cols.add("id");
		cols.add("PLAT_NUMBER");
		record.setColumns(cols);
		List<String> values = new ArrayList<String>();
		values.add("12345");
		values.add("粤B12345");
		
		record.setValues(values);
		
		dao.insert(record);*/
		return "test";
	}
	
	public static void main(String[] args) throws Exception {
//		new PhotoInfoController().test();
		String str = "<?xml version=\"1.0\" encoding=\"GBK\"?><root><QueryCondition><platNumber>粤BDDDDD</platNumber><frameNumber>DDDHGHG</frameNumber><stationCode>5CC1465686DA2E69E0536B01A8C0DF3B</stationCode><carTypeId>402881e44e345afa014e345bf6840000</carTypeId></QueryCondition></root>";
		System.out.println(URLEncoder.encode(str, "utf-8"));
	}
	
	@RequestMapping(value="/testJson")
	@ResponseBody
	public Map<String,Object> testJson(Date startDate,Date endDate){
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("startDate", startDate);
		result.put("endDate", endDate);
		return result;
	}
	
	@RequestMapping(value="/testJson2")
	@ResponseBody
	public Map<String,Object> testJson(BookInfoSupervise b){
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("startDate", b.getStartDate());
		result.put("endDate", b.getEndDate());
		result.put("b", b);
		System.out.println(b.getStartDate());
		return result;
	}
}

