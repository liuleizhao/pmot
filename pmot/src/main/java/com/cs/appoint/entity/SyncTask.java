package com.cs.appoint.entity;

import java.util.Date;

import com.cs.common.utils.DateUtils;
import com.cs.mvc.dao.BaseEntity;

/**
 * 任务表实体
 * @ClassName:    SyncTask
 * @Description:  
 * @Author        succ
 * @date          2017-11-8  下午12:02:09
 */
public class SyncTask extends BaseEntity{

	private static final long serialVersionUID = -6388961323614268278L;

	private int state;

    private Date createDate;

    private Date startDate;

    private Date finishDate;

    private String dataId;

    private String tableName;

    private String dataXml;
    
    public SyncTask(){
    	super();
    }

    public SyncTask( String dataId, String tableName, String dataXml) {
		super();
		this.state = 0;
		this.createDate = new Date();
		this.dataId = dataId;
		this.tableName = tableName;
		this.dataXml = dataXml;
	}

	public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getDataXml() {
        return dataXml;
    }

    public void setDataXml(String dataXml) {
        this.dataXml = dataXml;
    }

}
