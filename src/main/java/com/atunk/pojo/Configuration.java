package com.atunk.pojo;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 全局配置类：存储sqlMapConfig.xml配置文件解析出来的内容
 * @author: 张军
 * @email: 23166321@qq.com
 * @date: 2023/6/15 14:36
 */
public class Configuration {

	private DataSource dataSource;
	private Map<String, MappedStatement> mappedStatementMap = new HashMap();

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public Map<String, MappedStatement> getMappedStatementMap() {
		return mappedStatementMap;
	}

	public void setMappedStatementMap(Map<String, MappedStatement> mappedStatementMap) {
		this.mappedStatementMap = mappedStatementMap;
	}
}
