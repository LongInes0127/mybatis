package com.atunk.executor;

import com.atunk.pojo.Configuration;
import com.atunk.pojo.MappedStatement;

import java.util.List;

/**
 * @description:
 * @author: 张军
 * @email: 23166321@qq.com
 * @date: 2023/6/15 15:21
 */
public interface Executor {
	<E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object param);
}
