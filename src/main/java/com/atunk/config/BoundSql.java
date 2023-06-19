package com.atunk.config;

import com.atunk.utils.ParameterMapping;

import java.util.List;

/**
 * @description:
 * @author: 张军
 * @email: 23166321@qq.com
 * @date: 2023/6/15 15:54
 */
public class BoundSql {

	private List<ParameterMapping> parameterMappings;
	private String finalSql;

	public BoundSql(List<ParameterMapping> parameterMappings, String finalSql) {
		this.parameterMappings = parameterMappings;
		this.finalSql = finalSql;
	}

	public List<ParameterMapping> getParameterMappings() {
		return parameterMappings;
	}

	public void setParameterMappings(List<ParameterMapping> parameterMappings) {
		this.parameterMappings = parameterMappings;
	}

	public String getFinalSql() {
		return finalSql;
	}

	public void setFinalSql(String finalSql) {
		this.finalSql = finalSql;
	}
}
