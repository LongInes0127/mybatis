package com.atunk.config;

import com.atunk.pojo.Configuration;
import com.atunk.pojo.MappedStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

/**
 * @description:
 * @author: 张军
 * @email: 23166321@qq.com
 * @date: 2023/6/15 15:08
 */
public class XMLMapperBuilder {

	private Configuration configuration;

	public XMLMapperBuilder(Configuration configuration) {
		this.configuration = configuration;
	}

	/**
	 * 解析映射配置文件 --》 MappedStatement --》 Configuration里面的Map<String, MappedStatement>当中
	 * @param resourceAsSteam 映射配置文件的输入流
	 */
	public void parse(InputStream resourceAsSteam) throws DocumentException {
		Document document = new SAXReader().read(resourceAsSteam);
		// <mapper> 根标签
		Element rootElement = document.getRootElement();
		List<Element> selectList = rootElement.selectNodes("//select");
		String namespace = rootElement.attributeValue("namespace");
		for (Element element : selectList) {
			String id = element.attributeValue("id");
			String resultType = element.attributeValue("resultType");
			String parameterType = element.attributeValue("parameterType");
			String sql = element.getTextTrim();

			// 封装MappedStatement对象
			MappedStatement mappedStatement = new MappedStatement();
			// StatementId = namespace.id
			String StatementId = namespace + "." + id;
			mappedStatement.setId(StatementId);
			mappedStatement.setResultType(resultType);
			mappedStatement.setParameterType(parameterType);
			mappedStatement.setSql(sql);

			configuration.getMappedStatementMap().put(StatementId, mappedStatement);
		}
	}
}
