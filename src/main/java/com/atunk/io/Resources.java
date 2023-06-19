package com.atunk.io;

import java.io.InputStream;

/**
 * @description:
 * @author: 张军
 * @email: 23166321@qq.com
 * @date: 2023/6/15 14:30
 */
public class Resources {

	public static InputStream getResourceAsSteam(String path) {
		InputStream inputStream = Resources.class.getClassLoader().getResourceAsStream(path);
		return inputStream;
	}
}
