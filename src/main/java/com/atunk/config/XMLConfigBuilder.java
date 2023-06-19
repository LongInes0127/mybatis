package com.atunk.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.atunk.io.Resources;
import com.atunk.pojo.Configuration;
import com.atunk.pojo.MappedStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @description: 解析配置文件（dom4j + xpath）
 * @author: 张军
 * @email: 23166321@qq.com
 * @date: 2023/6/15 14:48
 */
public class XMLConfigBuilder {

	private Configuration configuration;
	private DruidDataSource druidDataSource = new DruidDataSource();
	private Properties properties = new Properties();

	public XMLConfigBuilder() {
		this.configuration = new Configuration();
	}

	/**
	 * 使用dom4j+xpath解析配置文件，封装Configuration对象
	 * @param inputStream
	 * @return
	 */
	public Configuration parse(InputStream inputStream) {
		try {
			Document document = new SAXReader().read(inputStream);
			// <configuration> 根标签
			Element rootElement = document.getRootElement();
			List<Element> propertyList = rootElement.selectNodes("//property");

			for (Element element : propertyList) {
				String name = element.attributeValue("name");
				String value = element.attributeValue("value");

				properties.setProperty(name, value);
			}

			druidDataSource.setDriverClassName(properties.getProperty("driverClassName"));
			druidDataSource.setUrl(properties.getProperty("url"));
			druidDataSource.setUsername(properties.getProperty("username"));
			druidDataSource.setPassword(properties.getProperty("password"));

			configuration.setDataSource(druidDataSource);

			/*
		       解析映射配置文件
		       	1.获取映射配置文件路径
		       	2.解析
		       	3.封装好mappedStatement
			 */
			List<Element> mapperList = rootElement.selectNodes("//mapper");
			for (Element element : mapperList) {
				String mapperPath = element.attributeValue("resource");
				InputStream resourceAsSteam = Resources.getResourceAsSteam(mapperPath);
				XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(configuration);
				xmlMapperBuilder.parse(resourceAsSteam);
				Map<String, MappedStatement> mappedStatementMap = configuration.getMappedStatementMap();
				MappedStatement mappedStatement = mappedStatementMap.get("user.selectList");
				System.out.println("selectList:" + mappedStatement.getParameterType());
			}
			System.out.println("selectOne:" + configuration.getMappedStatementMap().get("user.selectOne").getParameterType());
		} catch (DocumentException e) {
			throw new RuntimeException(e);
		}
		return configuration;
	}
}
