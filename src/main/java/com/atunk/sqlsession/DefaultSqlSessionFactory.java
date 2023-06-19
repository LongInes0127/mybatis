package com.atunk.sqlsession;

import com.atunk.executor.Executor;
import com.atunk.executor.simpleExecutor;
import com.atunk.pojo.Configuration;

/**
 * @description:
 * @author: 张军
 * @email: 23166321@qq.com
 * @date: 2023/6/15 14:51
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {

	private Configuration configuration;

	public DefaultSqlSessionFactory(Configuration configuration) {
		this.configuration = configuration;
	}

	public SqlSession openSession() {
		// 创建一个执行器对象
		Executor executor = new simpleExecutor();
		// 创建一个SqlSession对象
		SqlSession sqlSession = new DefaultSqlSession(configuration, executor);
		return sqlSession;
	}
}
