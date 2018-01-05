package com.cs.system.entity;

import java.util.Date;

import com.cs.common.entityenum.UserStatus;
import com.cs.common.entityenum.UserType;
import com.cs.mvc.dao.BaseEntity;

public class User extends BaseEntity{

	/** 账户名称 */
    private String account;

    /** 密码*/
    private String password;

    /** 真实姓名 */
    private String name;
    
    /** 盐值 */
    private String salt;

    /** 账号状态 */
	private UserStatus status = UserStatus.ENABLE;

	/** 最后一次登录时间 */
    private Date lastLoginDate;

    /** 最后一次登录IP地址 */
    private String lastLoginIp;

	/** 是否在线    0:不在线  1:在线  */
	private Integer isOnline = 0;

	/** 登录次数 */
	private Integer loginCount = 0;

	/** 创建人 */
    private String creator;

    /** 创建时间 */
	private Date createDate = new Date();

	/** 修改人 */
    private String modificator;

	/** 修改时间 */
    private Date modifyDate;

	/** 性别 */
	private Integer sex;

	/** 职务 */
    private String post;

    /** 手机号码 */
    private String mobile;

    /** 邮箱地址 */
    private String email;

    /** 身份证明号码 */
    private String idNumber;

    /** 所属检测站ID */
    private String stationId;
    
    /** 所属检测站name*/
    private String stationName;

    /** 用户类型 */ 
    private UserType userType;
    
    /** 是否超级管理员 */ 
    private Integer isAdmin = 0;
    
    /** 所属组织ID */
    private String orgId;
    
    /** 组织名 */ 
    private String orgName ;
    
    /** 组织类型 */ 
    private Integer orgType ;
    
    private String ip;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }
 
    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getModificator() {
        return modificator;
    }

    public void setModificator(String modificator) {
        this.modificator = modificator;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

	public Integer getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(Integer isOnline) {
		this.isOnline = isOnline;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public Integer getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Integer isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Integer getOrgType() {
		return orgType;
	}

	public void setOrgType(Integer orgType) {
		this.orgType = orgType;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
    
	
}