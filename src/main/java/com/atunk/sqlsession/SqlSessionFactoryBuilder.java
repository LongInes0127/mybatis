package com.atunk.sqlsession;

import com.atunk.config.XMLConfigBuilder;
import com.atunk.pojo.Configuration;

import java.io.InputStream;

/**
 * @description:
 * @author: 张军
 * @email: 23166321@qq.com
 * @date: 2023/6/15 14:45
 */
public class SqlSessionFactoryBuilder {

	/**
	 * 解析配置文件（dom4j + xpath）
	 * 创建SqlSessionFactory工厂
	 * @param inputStream
	 * @return
	 */
	public SqlSessionFactory build(InputStream inputStream) {
		XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder();
		Configuration configuration = xmlConfigBuilder.parse(inputStream);

		return new DefaultSqlSessionFactory(configuration);
	}
}
