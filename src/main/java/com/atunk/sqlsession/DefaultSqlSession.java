package com.atunk.sqlsession;

import com.atunk.executor.Executor;
import com.atunk.pojo.Configuration;
import com.atunk.pojo.MappedStatement;

import java.util.List;

/**
 * @description:
 * @author: 张军
 * @email: 23166321@qq.com
 * @date: 2023/6/15 15:37
 */
public class DefaultSqlSession implements SqlSession {

	private Configuration configuration;
	private Executor executor;

	public DefaultSqlSession(Configuration configuration, Executor executor) {
		this.configuration = configuration;
		this.executor = executor;
	}

	public <E> List<E> selectList(String statementId) {
		return this.selectList(statementId, null);
	}

	public <E> List<E> selectList(String statementId, Object param) {
		// 将要去完成对simpleExecutor里的query方法的调用
		MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
		List<E> list = executor.query(configuration, mappedStatement, param);
		return list;
	}

	public <T> T selectOne(String statementId, Object param) {
		List<Object> objects = this.selectList(statementId, param);

		if (objects != null && objects.size() == 1) {
			return (T) objects.get(0);
		} else if (objects.size() > 1) {
			throw new RuntimeException("查询结果过多");
		}

		return null;
	}
}
