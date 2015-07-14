package com.misproto.common.cash.context;

import java.util.HashMap;

import org.mou.common.JsonUtil;

/****
 * 线程级缓存<br>
 * 
 * @author NBQ
 *
 */
public class ThreadContext {

	HashMap<String, Object> map = new HashMap<String, Object>();

	/**
	 * 检查缓存是否存在
	 * 
	 * @param key
	 * @return
	 */
	public boolean containsKey(String key) {
		return map.containsKey(key);
	}

	/**
	 * 获取缓存Object
	 * 
	 * @param key
	 * @return
	 */
	public Object get(String key) {
		return map.get(key);
	}

	/**
	 * 缓存Object
	 * 
	 * @param key
	 * @return
	 */
	public void put(String key, Object value) {
		map.put(key, value);
	}

	/**
	 * 删除Context的key值
	 * 
	 * @param key
	 */
	public void remove(String key) {
		if (map.containsKey(key)) {
			map.remove(key);
		}
	}

	/****
	 * 判断是否没有缓存内容
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		return map.isEmpty();
	}

	/****
	 * 清除缓存
	 */
	public void clear() {
		map.clear();
	}

	/****
	 * 获取所有缓存的字符串格式内容
	 * 
	 * @return
	 */
	public String getAllCashedInf() {
		return JsonUtil.toJsonStr(map);
	}
}
