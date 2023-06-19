package com.atunk.executor;

import com.atunk.config.BoundSql;
import com.atunk.pojo.Configuration;
import com.atunk.pojo.MappedStatement;
import com.atunk.utils.GenericTokenParser;
import com.atunk.utils.ParameterMapping;
import com.atunk.utils.ParameterMappingTokenHandler;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: 张军
 * @email: 23166321@qq.com
 * @date: 2023/6/15 15:22
 */
public class simpleExecutor implements Executor {
	/**
	 * 执行JDBC操作
	 * @param configuration
	 * @param mappedStatement
	 * @param param
	 * @return
	 * @param <E>
	 */
	public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object param) {
		List<E> list;
		try {
			// 1.注册驱动，获取连接
			Connection connection = configuration.getDataSource().getConnection();
			// 2.获取预处理对象：preparedStatement
			BoundSql boundSql = getBoundSql(mappedStatement.getSql());
			String finalSql = boundSql.getFinalSql();
			PreparedStatement preparedStatement = connection.prepareStatement(finalSql);

			// 3.设置参数
			String parameterType = mappedStatement.getParameterType();
			if (param != null) {
				Class<?> parameterTypeClass = Class.forName(parameterType);
				List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
				for (int i = 0; i < parameterMappings.size(); i++) {
					ParameterMapping parameterMapping = parameterMappings.get(i);
					// id or username
					String fieldName = parameterMapping.getContent();
					Field declaredField = parameterTypeClass.getDeclaredField(fieldName);
					// 暴力访问
					declaredField.setAccessible(true);
					Object value = declaredField.get(param);
					preparedStatement.setObject(i + 1, value);
				}
			}

			// 4.执行sql
			ResultSet resultSet = preparedStatement.executeQuery();

			// 5.封装返回结果集
			list = new ArrayList();
			String resultType = mappedStatement.getResultType();
			Class<?> resultTypeClass = Class.forName(resultType);

			while (resultSet.next()) {
				Object obj = resultTypeClass.newInstance();
				// 元数据信息
				ResultSetMetaData metaData = resultSet.getMetaData();
				for (int i = 1; i <= metaData.getColumnCount(); i++) {
					String columnName = metaData.getColumnName(i);
					Object columnValue = resultSet.getObject(columnName);

					PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName, resultTypeClass);
					Method writeMethod = propertyDescriptor.getWriteMethod();
					writeMethod.invoke(obj, columnValue);
				}
				list.add((E) obj);
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		} catch (NoSuchFieldException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IntrospectionException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}

		return list;
	}

	private BoundSql getBoundSql(String sql) {
		// 标记处理类：主要是配合通用标记解析器GenericTokenParser类完成对配置文件等的解析工作，其中TokenHandler主要完成处理
		ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
		GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", parameterMappingTokenHandler);
		// 解析出来的sql
		String finalSql = genericTokenParser.parse(sql);

		// #{}里面解析出来的参数名称
		List<ParameterMapping> parameterMappings = parameterMappingTokenHandler.getParameterMappings();

		return new BoundSql(parameterMappings, finalSql);
	}
}
