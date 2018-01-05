package com.cs.common.entityenum;

import java.util.Arrays;
import java.util.List;
import com.cs.mvc.mybatis.enumhandler.Identifiable;
import com.cs.common.utils.EnumUtils;

/**
 * 验证状态
 * 
 * @author vincent
 * 
 */
public enum ValidateStatus implements Identifiable<Integer>{
	YES(1,"验车"), NO(0,"非验车");  
    
    private int id;  
    private String description;
      
    private ValidateStatus(int id ,String description){  
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
	
	
	public static List<ValidateStatus> getAll() {
		return Arrays.asList(ValidateStatus.class.getEnumConstants());
	}

	public static ValidateStatus findByIndex(Integer index) {
		return EnumUtils.getEnum(ValidateStatus.class, index);
	}

	public String getValue() {
		return this.name();
	}

	public String toString() {
		return this.name();
	}

}
