package com.cs.common.utils.xml;

import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.sax.SAXSource;

import org.dom4j.Document;
import org.dom4j.io.*;
import org.dom4j.Element;
import org.xml.sax.*;
import org.xml.sax.helpers.XMLFilterImpl;
import org.xml.sax.helpers.XMLReaderFactory;

import com.cs.common.utils.SerializeUtil;
import com.thoughtworks.xstream.XStream;

public class XmlBeanUtil {
	
	private static final XmlBeanUtil xmlUtil = new XmlBeanUtil();
 
	public static XmlBeanUtil getInstance() {
		return xmlUtil;
	}
	
    /**
     * java對象转换成xml
     * 
     * @Title: toXml
     * @Description: TODO
     * @param obj
     *            对象实例
     * @return String xml字符串
     */
    public static String toXml(Object obj) {
        XStream xstream = new XStream();
        xstream.processAnnotations(obj.getClass()); // 通过注解方式的，一定要有这句话
        return xstream.toXML(obj);
    }
	
    /**
     * 将传入xml文本转换成Java对象的集合
     * 
     * @Title: XmltoBeanCollection
     * @Description: TODOCOLLECTION
     * @param xmlStr
     * @param cls
     *            xml对应的class类
     * @prame rootElementName 
     *            xml字符中的根节点名称
     * @prame rootElementClass 
     *            xml字符中的根节点对应的集合类型
     * @return T xml对应的class类的实例对象
     * 
     * 调用的方法实例：List<PersonBean> personList= List<PersonBean>XmlUtil.toBean(xmlStr,
     *    PersonBean.class,"personList",List.class);
     */
    public static <T> Collection<T> xmlToBeanCollectioin(String xmlStr, Class<T> cls,String rootElementName,
            Class rootElementClass) {
         
        XStream xstream = new XStream();
        //显示声明此类开启注解
        xstream.processAnnotations(cls);
        //设置根节点对应的类型
        xstream.alias(rootElementName, rootElementClass); 
        Collection <T> obj =  (Collection<T>) xstream.fromXML(xmlStr);
        return obj;
    }
 
    /**
     * 
     * 方法说明 根据map键值对 获得xml串
     * 
     * @param bizObj
     * @return
     */
    public static String getXmlStr(Map<String, Object> bizObj) {
        StringBuffer str=new StringBuffer();
        Set<String> set=bizObj.keySet();
        Iterator<String> iterator=set.iterator();
        str.append("<xml>");
        while(iterator.hasNext()){
            String key=iterator.next();
            str.append("<"+key+">");
            str.append("<![CDATA["+bizObj.get(key)+"]]>");
            str.append("</"+key+">");
        }
        str.append("</xml>");
        return str.toString();
    }
    /**
     * 
     * 方法说明 根据输入流 获得map键值对
     * 
     * @param in 要解析的输入流
     * @return
     * @throws Exception
     */
    public static Map<String,Object> parseXml(InputStream in) throws Exception{
        //将解析结果存入HashMap中
        Map<String,Object> map=new HashMap<String,Object>();
        //读取输入流
        SAXReader reader=new SAXReader();
        Document document=reader.read(in);
        //得到xml根元素
        Element root=document.getRootElement();
        //得到所有子节点
        List<Element> elementList=root.elements();
        //便利所有子节点
        for(Element e:elementList){
            map.put(e.getName(), e.getText());
            System.out.println(e.getName()+":"+e.getText());
        }
        //释放资源
        in.close();
        in=null;
        return map;
    }
    
	/**
     * JavaBean转换成xml 
     * @param obj 
     * @return  
     */  
    public static String beanToXml(Object obj) {  
        String result = null;  
        try {  
            JAXBContext context = JAXBContext.newInstance(obj.getClass());  
            Marshaller marshaller = context.createMarshaller();  
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);  
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "GBK");
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, false);
            
            StringWriter writer = new StringWriter();  
            marshaller.marshal(obj, writer);  
            result = writer.toString();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return result;  
    }  
    
    
   /**
    * JavaBean转换成xml 
    * @param obj
    * @param formatted
    * @param dataEncoding
    * @param fragment
    * @return
    */
    public static String beanToXml(Object obj,  String dataEncoding , boolean fragment ) {  
        String result = null;  
        try {  
            JAXBContext context = JAXBContext.newInstance(obj.getClass());  
            Marshaller marshaller = context.createMarshaller();  
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);  
            marshaller.setProperty(Marshaller.JAXB_ENCODING, dataEncoding);
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, fragment);
            
            StringWriter writer = new StringWriter();  
            marshaller.marshal(obj, writer);  
            result = writer.toString();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return result;  
    }  
    
    
    
    /** 
     * xml转换成JavaBean 
     * @param xml 
     * @param c 
     * @return 
     */  
    public static <T> T xmlToBean(String xml, Class<T> c) {  
        T t = null;  
        try {  
            JAXBContext context = JAXBContext.newInstance(c);  
            Unmarshaller unmarshaller = context.createUnmarshaller();  
            t = (T) unmarshaller.unmarshal(new StringReader(xml));  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return t;  
    }  
    
    /** 
     * 阳光车险特殊处理   加命名空间，去掉命名空间的前缀
     * xml转换成JavaBean 
     * @param xml 
     * @param c 
     * @return 
     */  
    public static String toXML(Object obj,String dataEncoding) {
        try {
            JAXBContext context = JAXBContext.newInstance(obj.getClass());

            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, dataEncoding);// //编码格式
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);// 是否格式化生成的xml串
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, false);// 是否省略xm头声明信息

            StringWriter out = new StringWriter();
            OutputFormat format = new OutputFormat();
            format.setIndent(true);
            format.setNewlines(true);
            format.setNewLineAfterDeclaration(false);
            XMLWriter writer = new XMLWriter(out, format);

            XMLFilterImpl nsfFilter = new XMLFilterImpl() {
                private boolean ignoreNamespace = false;
                private String rootNamespace = null;
                private boolean isRootElement = true;

                @Override
                public void startDocument() throws SAXException {
                    super.startDocument();
                }

                @Override
                public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
                    if (this.ignoreNamespace) uri = "";
                    if (this.isRootElement) this.isRootElement = false;
                    else if (!uri.equals("") && !localName.contains("xmlns")) localName = localName + " xmlns=\"" + uri + "\"";

                    super.startElement(uri, localName, localName, atts);
                }

                @Override
                public void endElement(String uri, String localName, String qName) throws SAXException {
                    if (this.ignoreNamespace) uri = "";
                    super.endElement(uri, localName, localName);
                }

                @Override
                public void startPrefixMapping(String prefix, String url) throws SAXException {
                    if (this.rootNamespace != null) url = this.rootNamespace;
                    if (!this.ignoreNamespace) super.startPrefixMapping("", url);

                }
            };
            nsfFilter.setContentHandler(writer);
            marshaller.marshal(obj, nsfFilter);
            return out.toString();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    
    public static <T> T fromXML(String xml, Class<T> valueType) {
        try {
            JAXBContext context = JAXBContext.newInstance(valueType);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            // return (T) unmarshaller.unmarshal(new StringReader(xml));
            SerializeUtil obj = new SerializeUtil();
            XMLReader reader = XMLReaderFactory.createXMLReader();
            XMLFilterImpl nsfFilter = new XMLFilterImpl() {
                private boolean ignoreNamespace = false;

                @Override
                public void startDocument() throws SAXException {
                    super.startDocument();
                }

                @Override
                public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
                    if (this.ignoreNamespace) uri = "";
                    super.startElement(uri, localName, qName, atts);
                }

                @Override
                public void endElement(String uri, String localName, String qName) throws SAXException {
                    if (this.ignoreNamespace) uri = "";
                    super.endElement(uri, localName, localName);
                }

                @Override
                public void startPrefixMapping(String prefix, String url) throws SAXException {
                    if (!this.ignoreNamespace) super.startPrefixMapping("", url);
                }
            };
            nsfFilter.setParent(reader);
            InputSource input = new InputSource(new StringReader(xml));
            SAXSource source = new SAXSource(nsfFilter, input);
            return (T) unmarshaller.unmarshal(source);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    
}
