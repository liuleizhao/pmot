package com.cs.common.utils;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public final class RedisUtil {

	// Redis服务器IP
	private static String ADDR = "127.0.0.1";

	// Redis的端口号
	private static int PORT = 6379;

	// 访问密码
	private static String AUTH = "jawave";//

	// 可用连接实例的最大数目，默认值为8；
	// 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
	private static int MAX_ACTIVE = 1024;

	// 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
	private static int MAX_IDLE = 200;

	// 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
	private static int MAX_WAIT = 10000;

	private static int TIMEOUT = 10000;

	// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
	private static boolean TEST_ON_BORROW = true;

	private static JedisPool jedisPool = null;

	/**
	 * 初始化Redis连接池
	 */
	static {
		try {
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxTotal(MAX_ACTIVE);
			config.setMaxIdle(MAX_IDLE);
			config.setMaxWaitMillis(MAX_WAIT);
			config.setTestOnBorrow(TEST_ON_BORROW);
			jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT, AUTH);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取Jedis实例
	 * 
	 * @return
	 */
	public synchronized static Jedis getJedis() {
		try {
			if (jedisPool != null) {
				Jedis resource = jedisPool.getResource();
				return resource;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 释放jedis资源
	 * 
	 * @param jedis
	 */
	public static void returnResource(final Jedis resource) {
		if (resource != null) {
			jedisPool.returnResource(resource);
		}
	}

	/**
	 * 写入缓存
	 * 
	 * @param key
	 * @param value
	 * @param unixTime
	 */
	public static void setValue(String key, String value, Integer unixTime) {

		Jedis jedis = getJedis();
		jedis.set(key, value);

		if (unixTime != null) {
			jedis.expire(key, unixTime);
		}
		returnResource(jedis);
	}

	/**
	 * 写入缓存
	 * 
	 * @param key
	 * @param obj
	 * @param unixTime
	 */
	public static void setObjValue(String key, Object obj, Integer unixTime) {

		Jedis jedis = getJedis();
		jedis.set(key.getBytes(), SerializeUtil.serialize(obj));

		if (unixTime != null) {
			jedis.expire(key, unixTime);
		}
		returnResource(jedis);
	}

	/**
	 * 读取缓存对象
	 * 
	 * @param key
	 * @return
	 */
	public static String getValue(String key) {
		String value = null;
		if (StringUtils.isNotBlank(key)) {
			Jedis jedis = getJedis();
			value = jedis.get(key);
			returnResource(jedis);
		}
		return value;
	}

	/**
	 * 读取缓存对象
	 * 
	 * @param key
	 * @return
	 */
	public static Object getObjValue(String key) {
		Object obj = null;
		if (StringUtils.isNotBlank(key)) {
			Jedis jedis = getJedis();
			byte[] value = jedis.get(key.getBytes());
			if (value != null) {
				obj = SerializeUtil.unserialize(value);
			}
			returnResource(jedis);
		}
		return obj;
	}

	/**
	 * 写入List
	 * 
	 * @param key
	 * @param list
	 * @param unixTime
	 */
	public static void setStrListValue(String key, List<String> list,
			Integer unixTime) {
		Jedis jedis = getJedis();
		if (list != null) {
			for (String value : list) {
				jedis.lpush(key, value);
			}
			if (unixTime != null) {
				jedis.expire(key, unixTime);
			}
		}
		returnResource(jedis);
	}

	/**
	 * 读取List
	 * 
	 * @param key
	 * @return
	 */
	public static List<String> getStrListValue(String key) {
		List<String> list = null;
		if (StringUtils.isNotBlank(key)) {
			Jedis jedis = getJedis();
			list = jedis.lrange(key, 0, -1);
			returnResource(jedis);
		}
		return list;
	}

	/**
	 * 删除键值对
	 * 
	 * @param key
	 * @return
	 */
	public static long delByKey(String key) {
		Jedis jedis = getJedis();
		Long result = jedis.del(key);
		returnResource(jedis);
		return result;
	}

	/**
	 * 删除对象键值对
	 * 
	 * @param key
	 * @return
	 */
	public static long delObjByKey(byte[] key) {
		Jedis jedis = getJedis();
		Long result = jedis.del(key);
		returnResource(jedis);
		return result;
	}
	
	/**
	 * 设置到期时间
	 * 
	 * @param key
	 * @return
	 */
	public static void expire(String key ,long unixTime) {
		if(StringUtils.isNotBlank(key)){
			Jedis jedis = getJedis();
			jedis.expireAt(key, unixTime);
			returnResource(jedis);
			
		}
	}

	
	
}
