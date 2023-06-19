package com.atunk.sqlsession;

import java.util.List;

/**
 * @description:
 * @author: 张军
 * @email: 23166321@qq.com
 * @date: 2023/6/15 15:19
 */
public interface SqlSession {

	/**
	 * 查询所有
	 * @param statementId
	 * @return
	 * @param <E>
	 */
	<E> List<E> selectList(String statementId);

	<E> List<E> selectList(String statementId, Object param);

	/**
	 * 根据查询单个
	 * @param statementId
	 * @return
	 * @param <T>
	 */
	<T> T selectOne(String statementId, Object param);

}
