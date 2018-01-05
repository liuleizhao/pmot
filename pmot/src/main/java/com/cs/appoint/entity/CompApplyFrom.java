package com.cs.appoint.entity;

import java.util.Date;

import com.cs.common.entityenum.ApproveRemart;
import com.cs.mvc.dao.BaseEntity;
/**
 * 大客户申请表实体
 * @author huang
 *
 */
public class CompApplyFrom extends BaseEntity{

	private static final long serialVersionUID = -4646307348406371617L;

	private String createUserId;

    private Date createDate;

    private String companyName;

    /** 企业统一社会信用代码*/
    private String enterpriseCrediteCode;

    /**组织机构代码*/
    private String organizationCode;

    private Integer applyCount;

    private Date endDate;

    private String discription;

    private String approverId;

    private Date approveDate;

    private ApproveRemart approveRemart;

    private String approveDiscription;

    private String createUserName;

    private String approverName;

    private Date startDate;
    
    
    private Integer addedNum = 0;

    private Integer bookedNum = 0;


    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }


    public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getEnterpriseCrediteCode() {
        return enterpriseCrediteCode;
    }

    public void setEnterpriseCrediteCode(String enterpriseCrediteCode) {
        this.enterpriseCrediteCode = enterpriseCrediteCode;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }


    public Integer getApplyCount() {
		return applyCount;
	}

	public void setApplyCount(Integer applyCount) {
		this.applyCount = applyCount;
	}

	public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getApproverId() {
        return approverId;
    }

    public void setApproverId(String approverId) {
        this.approverId = approverId;
    }

    public Date getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(Date approveDate) {
        this.approveDate = approveDate;
    }


	public String getApproveDiscription() {
        return approveDiscription;
    }

    public void setApproveDiscription(String approveDiscription) {
        this.approveDiscription = approveDiscription;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getApproverName() {
        return approverName;
    }

    public void setApproverName(String approverName) {
        this.approverName = approverName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

	public ApproveRemart getApproveRemart() {
		return approveRemart;
	}

	public void setApproveRemart(ApproveRemart approveRemart) {
		this.approveRemart = approveRemart;
	}

	public Integer getAddedNum() {
		return addedNum;
	}

	public void setAddedNum(Integer addedNum) {
		this.addedNum = addedNum;
	}

	public Integer getBookedNum() {
		return bookedNum;
	}

	public void setBookedNum(Integer bookedNum) {
		this.bookedNum = bookedNum;
	}
	
	
    
}