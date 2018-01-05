package com.cs.common.entityenum;

import java.util.Arrays;
import java.util.List;
import com.cs.mvc.mybatis.enumhandler.Identifiable;
import com.cs.common.utils.EnumUtils;
/**
 * 用户类型
 * @author vincent
 *
 */
public enum UserType implements Identifiable<Integer>{
	
		ADMIN(1,"超级管理员"), NORMAL(2,"中心人员"), STATION(3,"检测站用户");
     
	    private int id;  
	    private String description;
	      
	    private UserType(int type ,String description){  
	        this.id = type;  
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
		
		
		public static List<UserType> getAll() {
			return Arrays.asList(UserType.class.getEnumConstants());
		}

		public static UserType findByIndex(Integer index) {
			return EnumUtils.getEnum(UserType.class, index);
		}

		public String getValue() {
			return this.name();
		}

		public String toString() {
			return this.name();
		}
		

}
