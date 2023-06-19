package com.atunk.pojo;

/**
 * @description: 映射配置类，存储UserMapper.xml解析出来的内容
 * @author: 张军
 * @email: 23166321@qq.com
 * @date: 2023/6/15 14:36
 */
public class MappedStatement {

	private String id;
	private String resultType;
	private String parameterType;
	private String sql;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getResultType() {
		return resultType;
	}

	public void setResultType(String resultType) {
		this.resultType = resultType;
	}

	public String getParameterType() {
		return parameterType;
	}

	public void setParameterType(String parameterType) {
		this.parameterType = parameterType;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}
}
