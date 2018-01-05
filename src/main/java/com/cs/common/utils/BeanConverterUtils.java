package com.cs.common.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;


/**
 * @描述：对象间转换工具类
 * @作者：肖达
 * @开发日期：2011-3-8
 * @版权：永泰软件有限公司
 * @版本：1.0
 */

public class BeanConverterUtils {
	
	/** 日志记录 */
	//private static Logger log=Logger.getLogger(BeanConverterUtils.class);
	
	/** 待转换的对象的SET方法集合 */
	private static Map<Object, Object> hmSetMethodsBeans=null;
	
	/** 源对象的GET方法集合 */
	private static Map<Object, Object> hmGetMethodsBeans = null;
	
	/**
	  * @描述：源对象与目标对象值转换
	  * @作者：肖达
	  * @开发日期：2011-3-8
	  * @param instance 源对象
	  * @param cls 目标对象反射类
	  * @return
	  * @throws Exception
	  */
	@SuppressWarnings("unchecked")
	public static Object convert(Object instance, Class cls) throws Exception {
		if(instance == null) return null;
		// 目标对象
		Object obj = null;
		hmSetMethodsBeans=new HashMap<Object,Object>();
		hmGetMethodsBeans=new HashMap<Object,Object>();
		// 获得源对象方法中的GET属性集合
		Map<Object,Object> hmGetMethods = getGetMethods(instance.getClass());
		// 获得目标对象方法中的SET属性集合
		Map<Object,Object> hmSetMethods = getSetMethods(cls);
		Set keySet = hmSetMethods.keySet();
		// 没有目标属性装载则退出
		if(null==keySet || keySet.isEmpty())return null;
		// 实例化目标对象
		if (null==obj)obj = cls.newInstance();

		
		Iterator it = keySet.iterator();
		while (it.hasNext()) {
			String _propertyName = (String) it.next();
			Method getMethod = (Method) hmGetMethods.get(_propertyName);
			Method setMethod = (Method) hmSetMethods.get(_propertyName);

			if (null!=setMethod  && null!=getMethod)
			{
				try {
					// 源对象方法反转
					Object params = null;
					// 方法属性类型
					Class setMethodType = setMethod.getParameterTypes()[0];
					// 如果检测到void方法则退出单词循环
					if(setMethodType.equals(void.class))continue;
					
					// 基本数据类型处理逻辑
					if(setMethodType.equals(java.lang.String.class) ||
					   setMethodType.equals(java.lang.Integer.class) ||
					   setMethodType.equals(java.lang.Long.class) ||
					   setMethodType.equals(java.lang.Float.class) ||
					   setMethodType.equals(java.lang.Double.class) ||
					   setMethodType.equals(java.util.Date.class)||
					   setMethodType.equals(char.class)||
					   setMethodType.equals(int.class)||
					   setMethodType.equals(byte[].class)||
					   setMethodType.equals(java.lang.Boolean.class)
						)
					{
						params = getMethod.invoke(instance);
						// 反射置入目标对象
						setMethod.invoke(obj, params);
				    // LIST数据类型处理逻辑
					}else if(setMethodType.equals(List.class)){
						if(getMethod.getGenericReturnType() instanceof ParameterizedType){
							ParameterizedType parameterizedType = (ParameterizedType)setMethod.getGenericParameterTypes()[0];
						    Class genericClass = (Class)parameterizedType.getActualTypeArguments()[0];
						    List srcList = (List)getMethod.invoke(instance);
						    List destList = new ArrayList();
						    if(srcList != null){
						    	for(int i=0;i<srcList.size();i++){
							    	params = convert(srcList.get(i),genericClass);
							    	destList.add(params);
							    }
						    	setMethod.invoke(obj, destList);
						    }
						}
					// SET数据类型处理逻辑	
					}else if(setMethodType.equals(Set.class)){
						if(getMethod.getGenericReturnType() instanceof ParameterizedType){
							ParameterizedType parameterizedType = (ParameterizedType)setMethod.getGenericParameterTypes()[0];
						    Class genericClass = (Class)parameterizedType.getActualTypeArguments()[0];
						    Set srcSet = (Set)getMethod.invoke(instance);
						    Set destSet = new HashSet();
						    if(genericClass == cls){
						    	destSet = null;
						    }else {
						    	if(srcSet != null){
						    		Iterator srcIt = srcSet.iterator();
								    
								    while(srcIt.hasNext()){
								    	
								    		params = convert(srcIt.next(),genericClass);
								    		destSet.add(params);
					                }
						    	}else{
						    		destSet = null;
						    	}
						    	
						    }
						    setMethod.invoke(obj, destSet);
						}
						
					// 类类型处理逻辑	
					}else{
						if(setMethodType.isEnum()){
							setMethod.invoke(obj, getMethod.invoke(instance));
						}/*else if(setMethodType == BFILE.class){
							setMethod.invoke(obj, getMethod.invoke(instance));
						}*/else{
							if(getMethod.invoke(instance) != null){//对象属性的值不为空。
								params = convert(getMethod.invoke(instance),setMethodType);
							}else{
								params = null;
							}
							
							setMethod.invoke(obj, params);
						}
					}
					
				} catch (Exception e) {
					throw e;
				}
			}
		}
		return obj;
	}

	/**
	  * @描述：获得反射类方法中的SET属性集合
	  * @作者：肖达
	  * @开发日期：2011-3-8
	  * @param cls 反射类
	  * @return
	  * @throws Exception
	  */
	@SuppressWarnings("unchecked")
	public static Map getSetMethods(Class cls) throws Exception {
		return getSetMethods(cls, Object.class);
	}

	/**
	  * @描述：获得反射类方法中的SET属性集合的具体实现
	  * @作者：肖达
	  * @开发日期：2011-3-8
	  * @param cls 反射类
	  * @param cls set反射类
	  * @return
	  * @throws Exception
	  */
	@SuppressWarnings("unchecked")
	public static Map getSetMethods(Class cls, Class setClass)throws Exception {
		String clsName = cls.getName();
		Map hmBean = (HashMap) hmSetMethodsBeans.get(clsName);
		if (null!=hmBean)return hmBean;
		BeanInfo beanInfo = null;
		try {
			beanInfo = Introspector.getBeanInfo(cls, setClass);
		} catch (IntrospectionException e) {
			throw e;
		}
		PropertyDescriptor[] props;
		Map hm = new HashMap();
		props = beanInfo.getPropertyDescriptors();
		for (int i = 0; i < props.length; i++) {
			Method setMethod = props[i].getWriteMethod();

			if (setMethod != null) {
				String voField = props[i].getWriteMethod().getName().toString();
				String field = props[i].getName().toLowerCase();
				if(voField.startsWith("set")){
					field = voField.substring(3, 4).toLowerCase() + voField.substring(4);
				}
				//log.info("VO:" + field);
				hm.put(field, setMethod);
			}
		}
		hmSetMethodsBeans.put(clsName, hm);
		return hm;
	}

	/**
	  * @描述：获得反射类方法中的GET属性集合的具体实现
	  * @作者：肖达
	  * @开发日期：2011-3-8
	  * @param cls 反射类
	  * @return
	  * @throws Exception
	  */
	@SuppressWarnings("unchecked")
	public static Map getGetMethods(Class cls) throws Exception {
		return getGetMethods(cls, Object.class);
	}

	/**
	  * @描述：获得反射类方法中的GET属性集合的具体实现
	  * @作者：肖达
	  * @开发日期：2011-3-8
	  * @param cls 反射类
	  * @param setClass set反射类
	  * @return
	  * @throws Exception
	  */
	@SuppressWarnings("unchecked")
	public static Map getGetMethods(Class cls, Class setClass)throws Exception {
		String clsName = cls.getName();
		Map hmBean = (HashMap) hmGetMethodsBeans.get(clsName);
		if (hmBean != null) return hmBean;
		BeanInfo drbeanInfo = null;
		try {
			drbeanInfo = Introspector.getBeanInfo(cls);
		} catch (IntrospectionException e) {
			throw e;
		}
		PropertyDescriptor[] props;
		HashMap hm = new HashMap();
		props = drbeanInfo.getPropertyDescriptors();

		for (int i = 0; i < props.length; i++) {
			Method getMethod = props[i].getReadMethod();
			if (getMethod != null)
			{
				String poField = props[i].getReadMethod().getName().toString();
				String field = props[i].getName().toLowerCase();
				if(poField.startsWith("get")){
					field = poField.substring(3, 4).toLowerCase() + poField.substring(4);
				}
				//log.info("PO:" + field);
				hm.put(field, getMethod);
			}
		}
		hmGetMethodsBeans.put(clsName, hm);
		return hm;
	}
	
	
	/**
	 * @描述：查询结果集对象VO转换专用方法
	 * @作者：肖达
	 * @开发日期：2011-9-28
	 * @param qr 查询结果集
	 * @param cls 目标类型
	 * @return
	 */
	/*@SuppressWarnings("unchecked")
	public static <T, O> QueryResult<T> convertQueryResult(QueryResult<O> qr, Class cls){
		QueryResult<T> qrVO = new QueryResult<T>();
		if(null != qr){
			qrVO.setTotalrecord(qr.getTotalrecord());
		}
		try {
			if(qr.getResultlist().size() > 0){
				List list = new ArrayList();
				for(Object obj : qr.getResultlist()){
					list.add(convert(obj, cls));
				}
				qrVO.setResultlist(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return qrVO;
	}*/
	
	/**
	 * 获取6位随机数
	 * @return
	 */
	public static int randomNumber() {
		int[] array = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		Random rand = new Random();
		for (int i = 9; i > 0; i--) {
			int index = rand.nextInt(i);
			int tmp = array[index];
			array[index] = array[i - 1];
			array[i - 1] = tmp;
		}
		int result = 0;
		for (int i = 0; i < 6; i++)
			result = result * 10 + array[i];
		return result;
	}
	/**
	 * @描述：类型转换器的使用例子。
	 * @作者：肖达
	 * @开发日期：2011-9-27
	 * @param args
	 */
	
	public static void main(String[] args) {
//		Userr user = new Userr(
//				"admin","123",SexEnum.MAN,19L,new Org("1","11111","组织")
//		);
//		Set<Userr> orgs = new HashSet<Userr>();
//		orgs.add(user);
////		user.setOrgs(orgs);
//		try {
//			//测试convert方法
//			UserrVO uVO =(UserrVO) convert(user, UserrVO.class);
//			System.out.println("=============================" + uVO + "=====================");
//			System.out.println(uVO.getAccount() + "\n" + uVO.getPassword() + "\n" + uVO.getAge() 
//					+ "\n" + uVO.getSex() + "\n" + uVO.getOrganization() + "\n" + uVO.getOrganization().getId()
//					 + "\n" + uVO.getOrganization().getCode()  + "\n" + uVO.getOrganization().getName() + "\n" + uVO.getOrgs()
//			);
//			Userr user1 = (Userr)convert(uVO, Userr.class);
//			System.out.println("=============================" + user1 + "=====================");
//			System.out.println(user1.getAccount() + "\n" + user1.getPassword() + "\n" + user1.getAge()
//					 + "\n" + user1.getSex() + "\n" + user1.getOrganization()  + "\n" + user1.getOrganization().getId()
//					 + "\n" + user1.getOrganization().getCode() + "\n" + user1.getOrganization().getName());
			
			//测试convertQueryResult方法
			/*QueryResult<Userr> qrPO = new QueryResult<Userr>();
			qrPO.setTotalrecord(123);
			List<Userr> listUserr = new ArrayList<Userr>();
			for(int i=0; i<3; i++){
				Userr u = new Userr(
						"admin" + i,"123"+i,SexEnum.MAN,19L+i,new Org("1" +i,"11111" +i,"组织"+i));
				listUserr.add(u);
			}
			qrPO.setResultlist(listUserr);
			
			QueryResult qrVO = convertQueryResult(qrPO, UserrVO.class);
			System.out.println("结果集转换结果=" + qrVO);*/
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
	}
}
