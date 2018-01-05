package com.cs.appoint.timer;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.cs.appoint.entity.VehIsFlow;
import com.cs.appoint.service.VehIsFlowService;
import com.cs.common.utils.BeanConverterUtils;
import com.cs.common.utils.DateUtils;
import com.cs2.veh.entity.IVehIsFlow;
import com.cs2.veh.service.IVehIsFlowService;

/**
 * 同步流水信息定时器【每晚凌晨一点更新前一天的数据】
 * @author Administrator
 *
 */
public class DownloadVehIsFlowTimer {
	@Autowired
	private IVehIsFlowService ivehIsFlowService;
	@Autowired
	private VehIsFlowService vehIsFlowService;
	
	public void download() throws Exception{
		//第一步、下载登录日期为前一天的流水记录
		List<IVehIsFlow> ivehIsFlows = ivehIsFlowService.findByDate(DateUtils.addDays(new Date(), -1));
		//第二步、插入本地流水信息表
		if(CollectionUtils.isNotEmpty(ivehIsFlows)){
			VehIsFlow tmp = null;
			for(IVehIsFlow iflow : ivehIsFlows){
				tmp = (VehIsFlow)BeanConverterUtils.convert(iflow, VehIsFlow.class);
				try{
					vehIsFlowService.insert(tmp);
				}catch(org.springframework.dao.DuplicateKeyException exception){
				}
			}
			
			System.out.println("-----------------------------------");
			System.out.println("-----------------------------------");
			System.out.println("-----------------------------------");
		}
	}
	
	public static void main(String[] args) {
		Date d = DateUtils.addDays(new Date(), -1);
		System.out.println(DateUtils.getDate(d));
	}
}

