package com.cs.mvc.init;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.cs.argument.entity.CarType;
import com.cs.argument.service.CarTypeService;
import com.cs.common.constant.Constants;
import com.cs.common.entityenum.Recordable;
import com.cs.system.entity.GlobalConfig;
import com.cs.system.service.GlobalConfigService;
import com.cs.webservice.entity.InterParamRelation;
import com.cs.webservice.entity.InterfaceInfo;
import com.cs.webservice.service.InterfaceInfoService;

/**
 * 初始化数据Service，将常用数据缓存到内存中
 * @ClassName 	InitData
 * @Description
 * @Author 		suchaochen
 * @Date 		2017-12-19	 下午3:32:56
 */
@Service
public class InitData implements ApplicationListener<ContextRefreshedEvent> {
	
	private static final Map<String,Object> interfaces = new HashMap<String,Object>();
	private static final Map<String,String> global = new HashMap<String,String>();
	
	@Autowired
	private InterfaceInfoService interfaceInfoService;
	@Autowired
	private GlobalConfigService globalConfigService;
	@Autowired
	private CarTypeService carTypeService;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (event.getApplicationContext().getParent() == null) {
			try {
				//全局配置
				reloadGlobalConfig();
				//接口信息
				reloadInterfaceInfo();
				System.out.println("----------------------初始化数据成功----------------------");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 重新加载全局配置
	 * @throws Exception
	 */
	public void reloadGlobalConfig() throws Exception{
		System.out.println("----------------------加载全局配置----------------------");
		List<GlobalConfig> list = globalConfigService.findAllData();
		if (list != null) {

			Map<String, String> newConfigMap = new HashMap<String, String>();
			for (GlobalConfig global : list) {
				newConfigMap.put(global.getDataKey(), global.getDataValue());
			}
			global.putAll(newConfigMap);
		}
		
		List<CarType> carTypes = carTypeService.findAllData();
		if(carTypes != null){
			for(CarType c : carTypes){
				global.put(Constants.CAR_TYPE_ID_PREFIX+c.getId(),c.getCode()); //以号牌种类ID为Key，号牌种类Code为value
			}
		}
	}
	
	/**
	 * 重新加载接口信息
	 * @throws Exception
	 */
	public void reloadInterfaceInfo() throws Exception{
		System.out.println("----------------------加载接口信息----------------------");
		List<InterfaceInfo> list = interfaceInfoService.findAllData();
		if(CollectionUtils.isNotEmpty(list)){
			for(InterfaceInfo interfaceInfo : list){
				List<InterParamRelation> params = 
						interfaceInfoService.findParameters(interfaceInfo.getId());
				interfaceInfo.setParams(params);
				interfaces.put(interfaceInfo.getJkid(), interfaceInfo);
			}
		}
	}
	
	/**
	 * 根据参数Key获取参数值
	 * @param dataKey
	 * @return
	 */
	public static String getGlobalValue(String dataKey){
		return global.get(dataKey);
	}
	
	/**
	 * 根据接口ID获取接口信息
	 * @param jkid
	 * @return
	 * @throws Exception
	 */
	public static InterfaceInfo getInterfaceInfo(String jkid) throws Exception{
		return (InterfaceInfo)BeanUtils.cloneBean((InterfaceInfo)interfaces.get(jkid));
	}
	
	public static void main(String[] args) throws Exception{
		Map<String,String> map = new HashMap<String , String>();
		map.put("01", "JK01");
		
		String s = map.get("01");
		s = "JK99";
		
		System.out.println(map.get("01"));
		
		InterfaceInfo i1 = new InterfaceInfo();
		i1.setClassName("类名");
		i1.setDescription("描述1");
		i1.setId("123");
		i1.setJkid("JK01");
		i1.setMethodName("方法名");
		i1.setRecordable(Recordable.YES);
		List<InterParamRelation> params = new ArrayList<InterParamRelation>();
		for(int i=0; i<3; i++){
			InterParamRelation tmp = new InterParamRelation();
			tmp.setParameterId("relation"+i);
			tmp.setRequiredAttrs("attr"+i);
			params.add(tmp);
		}
		i1.setParams(params);
		
		InterfaceInfo i2 = (InterfaceInfo)BeanUtils.cloneBean(i1);
		System.out.println(i2.getClassName());
		i2.setJkid("JK999");
		System.out.println(i1.getJkid());
	}
}
