package com.cs.mvc.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * MyBatis SQL 条件封装类
 * 
 * @author vincent
 * 
 */
public class SqlCondition {

	/** 排序条件 */
	private List<String> orderbyClause = new ArrayList<String>();

	/** 去重复关键字 */
	private boolean distinct;

	/** where语句条件 */
	private List<SqlCriterion> sqlCriterions = new ArrayList<SqlCriterion>();

	/**
	 * 添加 无参数 条件 <null , not null...>
	 * 
	 * @author vincent
	 * 
	 */
	public void addCriterion(String condition) {
		addCriterion(true, false, false, false, condition, null, null);
	}

	/**
	 * 添加 单个参数 条件 ( = , > , < ... )、添加列表参数条件 (IN , NOT IN)
	 * 
	 * @param condition
	 * @param value
	 * @throws Exception
	 */
	public void addSingleCriterion(String condition, Object value)
			throws Exception {
		addSingleCriterion(condition, value, false, true);
	}

	/**
	 * 添加 单个非空参数 条件 ( = , > , < ... )、添加列表参数条件 (IN , NOT IN)
	 * 
	 * @param condition
	 * @param value
	 * @throws Exception
	 */
	public void addSingleNotNullCriterion(String condition, Object value)
			throws Exception {
		addSingleCriterion(condition, value, false, false);
	}

	/**
	 * 添加 单个参数 LIKE条件
	 * 
	 * @param condition
	 * @param value
	 * @throws Exception
	 */
	public void addLikeCriterion(String condition, Object value)
			throws Exception {
		addSingleCriterion(condition, value, true, true);
	}

	/**
	 * 添加 单个参数 LIKE条件
	 * 
	 * @param condition
	 * @param value
	 * @throws Exception
	 */
	public void addLikeNotNullCriterion(String condition, Object value)
			throws Exception {
		addSingleCriterion(condition, value, true, false);
	}

	/**
	 * 添加 单个参数 条件 ( = , > , < ... )、添加列表参数条件 (IN , NOT IN)
	 * 
	 * @param condition
	 * @param value
	 * @param 是否为like语句
	 */
	private void addSingleCriterion(String condition, Object value,
			boolean isLike, boolean isNullable) throws Exception {
		if (value != null) {
			if (value instanceof List<?>) {// List类型 对应listValue
				addCriterion(false, false, false, true, condition, value, null);
				return;
			} else if (value instanceof String) {
				if (StringUtils.isNotBlank((String) value)) {
					if (isLike) {
						value = "%" + value + "%";
					}
					addCriterion(false, true, false, false, condition, value,
							null);
					return;
				}
			} else {// 普通Object 对应 singleValue
				addCriterion(false, true, false, false, condition, value, null);
				return;
			}
		}
		if (!isNullable) {
			throw new Exception("参数不能为空！");
		}
	}

	/**
	 * 添加 两参数 条件(between)
	 * 
	 * @param condition
	 * @param value
	 * @param secondValue
	 */
	public void addBetweenCriterion(String condition, Object value,
			Object secondValue) throws Exception {
		addBetweenCriterion(condition, value, secondValue, true);
	}

	/**
	 * 添加 两非空参数 条件(between)
	 * 
	 * @param condition
	 * @param value
	 * @param secondValue
	 * @throws Exception
	 */
	public void addBetweenNotNullCriterion(String condition, Object value,
			Object secondValue) throws Exception {
		addBetweenCriterion(condition, value, secondValue, false);
	}

	/**
	 * 添加 两参数 条件(between)
	 * 
	 * @param condition
	 * @param value
	 * @param secondValue
	 */
	private void addBetweenCriterion(String condition, Object value,
			Object secondValue, boolean isNullable) throws Exception {

		if (value != null && secondValue != null) {
			if (value instanceof String && secondValue instanceof String) {
				if (StringUtils.isNotBlank((String) value)
						&& StringUtils.isNotBlank((String) secondValue)) {
					addCriterion(false, false, true, false, condition, value,
							secondValue);
						return;
				}
			} else {// 普通Object
				addCriterion(false, false, true, false, condition, value,
						secondValue);
					return;
			}
		}
		if (!isNullable) {
			throw new Exception("参数不能为空！");
		}
	}

	private void addCriterion(boolean noValue, boolean singleValue,
			boolean betweenValue, boolean listValue, String condition,
			Object value, Object secondValue) {
		SqlCriterion criterion = new SqlCriterion();
		criterion.setNoValue(noValue);
		criterion.setSingleValue(singleValue);
		criterion.setBetweenValue(betweenValue);
		criterion.setListValue(listValue);
		criterion.setCondition(condition.toUpperCase());
		criterion.setValue(value);
		criterion.setSecondValue(secondValue);
		sqlCriterions.add(criterion);
	}

	/**
	 * 添加 orderby 顺序对应列
	 * 
	 * @param ColumnName
	 */
	public void addAscOrderbyColumn(String columnName) {
		if (StringUtils.isNotBlank(columnName)) {
			orderbyClause.add(columnName.toUpperCase() + " ASC");
		}
	}

	/**
	 * 添加 orderby 反序对应列
	 * 
	 * @param ColumnName
	 */
	public void addDescOrderbyColumn(String columnName) {
		if (StringUtils.isNotBlank(columnName)) {
			orderbyClause.add(columnName.toUpperCase() + " DESC");
		}
	}

	/**
	 * 判断是否需要orderby语句
	 * 
	 * @return
	 */
	public boolean isOrderby() {

		if (orderbyClause.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	public List<String> getOrderbyClause() {
		return orderbyClause;
	}

	public void setOrderbyClause(List<String> orderbyClause) {
		this.orderbyClause = orderbyClause;
	}

	public boolean isDistinct() {
		return distinct;
	}

	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	public List<SqlCriterion> getSqlCriterions() {
		return sqlCriterions;
	}

	public void setSqlCriterions(List<SqlCriterion> sqlCriterions) {
		this.sqlCriterions = sqlCriterions;
	}

	/**
	 * Sql语句过滤类
	 * 
	 * @author vincent
	 * 
	 */
	public class SqlCriterion {

		private String condition;

		private Object value;

		private Object secondValue;

		private boolean noValue;

		private boolean singleValue;

		private boolean betweenValue;

		private boolean listValue;

		public String getCondition() {
			return condition;
		}

		public void setCondition(String condition) {
			this.condition = condition;
		}

		public Object getValue() {
			return value;
		}

		public void setValue(Object value) {
			this.value = value;
		}

		public Object getSecondValue() {
			return secondValue;
		}

		public void setSecondValue(Object secondValue) {
			this.secondValue = secondValue;
		}

		public boolean isNoValue() {
			return noValue;
		}

		public void setNoValue(boolean noValue) {
			this.noValue = noValue;
		}

		public boolean isSingleValue() {
			return singleValue;
		}

		public void setSingleValue(boolean singleValue) {
			this.singleValue = singleValue;
		}

		public boolean isBetweenValue() {
			return betweenValue;
		}

		public void setBetweenValue(boolean betweenValue) {
			this.betweenValue = betweenValue;
		}

		public boolean isListValue() {
			return listValue;
		}

		public void setListValue(boolean listValue) {
			this.listValue = listValue;
		}

	}

}
