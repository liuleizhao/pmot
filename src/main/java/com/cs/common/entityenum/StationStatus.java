package com.cs.common.entityenum;

import java.util.Arrays;
import java.util.List;
import com.cs.mvc.mybatis.enumhandler.Identifiable;
import com.cs.common.utils.EnumUtils;

/**
 * 状态
 * @author vincent
 */
public enum StationStatus implements Identifiable<Integer>{
	
	YES(1,"可用"), NO(0,"不可用");
	
	private int id;  
    private String description;
      
    private StationStatus(int id ,String description){  
        this.id = id;  
        this.description = description;
    }  
      
    @Override  
    public Integer getId(){  
        return this.id;  
    }

	@Override
	public String getDescription() {
		return  this.description;
	}  
	
	public static List<StationStatus> getAll() {
		return Arrays.asList(StationStatus.class.getEnumConstants());
	}

	public static StationStatus findByIndex(Integer index) {
		return EnumUtils.getEnum(StationStatus.class, index);
	}

	public String getValue() {
		return this.name();
	}

	public String toString() {
		return this.name();
	}
}
