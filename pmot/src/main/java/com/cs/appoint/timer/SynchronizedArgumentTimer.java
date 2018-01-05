package com.cs.appoint.timer;

import java.util.Date;
import java.util.List;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cs.appoint.service.VehIsInfoService;
import com.cs.argument.entity.Station;
import com.cs.argument.service.StationService;






@Component(value = "synchronizedArgumentTimer") 
public class SynchronizedArgumentTimer {
	private static Logger logger = LoggerFactory.getLogger(SynchronizedArgumentTimer.class);
	
	
	@Autowired
	private StationService stationService;
	@Autowired
	private VehIsInfoService vehIsInfoService;
	public void getVehis() throws Exception {
		System.out.println("==========================定时任务:下载已预约车辆数据【开始】==========================");
		
		List<Station> stationList = stationService.findAllData();
		
		if(stationList !=null&&stationList.size()>0){
			for(Station station : stationList){
				
					String bookNumbers = vehIsInfoService.getVehIsInfos(station);
					System.out.println("下载已预约车辆数据:【"+station.getName()+"】:"+"bookNumbers");
				
			}
		}
		System.out.println("==========================定时任务:下载已预约车辆数据【结束】==========================");
	}
		
	
	
	
	
}
