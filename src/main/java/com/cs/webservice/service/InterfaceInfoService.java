package com.cs.webservice.service;

import java.util.List;

import com.cs.mvc.service.BaseService;
import com.cs.webservice.entity.InterParamRelation;
import com.cs.webservice.entity.InterfaceInfo;

/**
 * WebService接口信息Service
 * @ClassName:    InterfaceInfoService
 * @Description:  
 * @Author        succ
 * @date          2017-10-28  上午10:57:31
 */
public interface InterfaceInfoService extends BaseService<InterfaceInfo,String> {
	/**
	 * 查询接口的参数列表
	 * @param id
	 * @return
	 * @throws Exception 
	 * @Author        succ
	 * @date 2017-10-28 上午11:01:12
	 */
	public List<InterParamRelation> findParameters(String id) throws Exception;
	
	/**
	 * 查询所有数据，根据JkId Asc排序
	 * @return
	 * @throws Exception
	 * @Author        succ
	 * @date 2017-11-22 上午9:54:14
	 */
	public List<InterfaceInfo> findAllOrderByJkIdAsc() throws Exception;
	
	public InterfaceInfo findByJkId(String jkId) throws Exception;
}
