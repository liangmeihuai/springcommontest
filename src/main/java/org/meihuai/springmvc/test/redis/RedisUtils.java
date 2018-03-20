package org.meihuai.springmvc.test.redis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.*;

import javax.annotation.PostConstruct;
import java.util.*;


/**
 * redis 操作类
 * com.talkingdata.dmk.util
 * @author chen
 */
public class RedisUtils {
  private static final Logger logger = LoggerFactory.getLogger(RedisUtils.class);
    @Autowired
    private JedisPool jedisPool;
//  @Autowired
//  private JedisPool jedisPool;

  private static RedisUtils redisUtils;
  @PostConstruct
  public void init() {
    redisUtils = this;
    redisUtils.jedisPool = this.jedisPool;
  }
  /**
   * 过去时间 两周
   */
  public final static int EXPIRED_SECONDS_TWO_WEEK = 2*7*24*60*60;

  /**
   * 过期时间 两天
   */
  public final static int EXPIRED_SECONDS_TWO_DAY = 2*24*60*60;

  /**
   * 过期时间 两小时
   */
  public final static int EXPIRED_SECONDS_TWO_HOUR = 2*60*60;

  /**
   * 过期时间 30秒
   */
 // public  static int EXPIRED_30_SECONDS = DynamicConfig.getIntValue(DynamicConstants.EXPIRED_30_SECONDS);


  /**
   * 刷新redis缓存
   *
   * @param key
   * @param value
   * @param timeout 过期时间秒,如果小于等于0，则永不过期
   * @return
   */
  public  static void set(String key, String value, Integer timeout) {
    Jedis jedis = null;
    try {
      jedis = redisUtils.jedisPool.getResource();
      jedis.set(key, value);
      if(timeout > 0){
        jedis.expire(key, timeout);
      }


    } catch (Exception e) {
      logger.error(e.getMessage());
    } finally {
      redisUtils.jedisPool.returnResourceObject(jedis);
    }
  }








  /**
   * 获取redis缓存的值
   *
   * @param key
   * @return
   */
  public static  String get(String key) {
    String value = null;
    Jedis jedis = null;
    try {
      jedis = redisUtils.jedisPool.getResource();
      value = jedis.get(key);
    } catch (Exception e) {
      logger.error(e.getMessage());
    } finally {
      redisUtils.jedisPool.returnResourceObject(jedis);
    }
    return value;
  }


  public static  boolean exists(String key) {
    Jedis jedis = null;
    try {
      jedis = redisUtils.jedisPool.getResource();
      return jedis.exists(key);
    } catch (Exception e) {
      logger.error(e.getMessage());
    } finally {
      redisUtils.jedisPool.returnResourceObject(jedis);
    }
    return false;
  }

  /**
   * 刷新redis缓存
   *
   * @param key
   * @param value
   * @param timeout 过期时间秒,如果小于等于0，则永不过期
   * @return
   */
  public  static void sset(String key, String value, Integer timeout) {
    Jedis jedis = null;
    try {
      jedis = redisUtils.jedisPool.getResource();
      jedis.sadd(key, value);



      if(timeout > 0){
        jedis.expire(key, timeout);
      }


    } catch (Exception e) {
      logger.error(e.getMessage());
    } finally {
      redisUtils.jedisPool.returnResourceObject(jedis);
    }
  }


  /**
   * 刷新redis缓存
   *
   * @param key
   * @param value
   * @param timeout 过期时间秒,如果小于等于0，则永不过期
   * @return
   */
  public  static void zset(String key, Object value, Integer timeout) {
    Jedis jedis = null;
    try {
      jedis = redisUtils.jedisPool.getResource();
      jedis.zadd(key.getBytes(), 0,  SerializeUtil.serialize(value));


      if(timeout > 0){
        jedis.expire(key, timeout);
      }


    } catch (Exception e) {
      logger.error(e.getMessage());
    } finally {
      redisUtils.jedisPool.returnResourceObject(jedis);
    }
  }

  /**
   * 返回Sorted-Sets中的成员数量
   * @param key
   * @return 如果该Key不存在，返回0
   */
  public  static Long zcard(String key) {
    Jedis jedis = null;
    try {
      jedis = redisUtils.jedisPool.getResource();
      return jedis.zcard(key.getBytes());
    } catch (Exception e) {
      logger.error(e.getMessage());
      return 0L;
    } finally {
      redisUtils.jedisPool.returnResourceObject(jedis);
    }
  }

  public  static Long llen(String key) {
    Jedis jedis = null;
    try {
      jedis = redisUtils.jedisPool.getResource();
      return jedis.llen(key.getBytes());
    } catch (Exception e) {
      logger.error(e.getMessage());
      return 0L;
    } finally {
      redisUtils.jedisPool.returnResourceObject(jedis);
    }
  }

  /**
   * 删除Sorted-Sets中的成员
   * @param key key
   * @param members 成员列表
   * @return 实际被删除的成员数量
   */
  public  static Long zrem(String key,String... members) {
    Jedis jedis = null;
    try {
      jedis = redisUtils.jedisPool.getResource();
      return jedis.zrem(key, members);
    } catch (Exception e) {
      logger.error(e.getMessage());
      return 0L;
    } finally {
      redisUtils.jedisPool.returnResourceObject(jedis);
    }
  }

  /**
   * 正排序的名次, 与 zrevrank (String key, Object value)返回值相反
   * @param key
   * @param value
   * @return
   */
  public  static Long zrank (String key, Object value) {
    Jedis jedis = null;
    try {
      jedis = redisUtils.jedisPool.getResource();
      return jedis.zrank(key.getBytes(), SerializeUtil.serialize(value));

    } catch (Exception e) {
      logger.error(e.getMessage());
    } finally {
      redisUtils.jedisPool.returnResourceObject(jedis);
    }
    return null;
  }

  /**
   * 查看vlalue在sorted set中倒排序时排在第几名,查询结果按照INDEX,所以INDEX是3表示排在第四名
   * @param key
   * @param value
   * @return
   */
  public  static Long zrevrank (String key, Object value) {
    Jedis jedis = null;
    try {
      jedis = redisUtils.jedisPool.getResource();
      return jedis.zrevrank(key.getBytes(), SerializeUtil.serialize(value));

    } catch (Exception e) {
      logger.error(e.getMessage());
    } finally {
      redisUtils.jedisPool.returnResourceObject(jedis);
    }
    return null;
  }


  /**
   * 获取redis缓存的值
   *
   * @param key
   * @return
   */
  public static  Set<String> smembers(String key) {
    Set<String> values = null;
    Jedis jedis = null;
    try {
      jedis = redisUtils.jedisPool.getResource();
      values = jedis.smembers(key);
    } catch (Exception e) {
      logger.error(e.getMessage());
    } finally {
      redisUtils.jedisPool.returnResourceObject(jedis);
    }
    return values;
  }

  /**
   * 刷新redis缓存
   *
   * @param key
   * @param value
   * @param timeout 过期时间秒,如果小于等于0，则永不过期
   * @return
   */
  public  static void sset(String key, Object value, Integer timeout) {
    Jedis jedis = null;
    try {
      jedis = redisUtils.jedisPool.getResource();
      jedis.sadd(key.getBytes(), SerializeUtil.serialize(value));

      if(timeout > 0){
        jedis.expire(key, timeout);
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
    } finally {
      redisUtils.jedisPool.returnResourceObject(jedis);
    }
  }

  public  static Long scard(String key) {
    Jedis jedis = null;
    try {
      jedis = redisUtils.jedisPool.getResource();
      return jedis.scard(key.getBytes());
    } catch (Exception e) {
      logger.error(e.getMessage());
      return 0L;
    } finally {
      redisUtils.jedisPool.returnResourceObject(jedis);
    }
  }

  @SuppressWarnings("unchecked")
  public static <T> Set<T> smembersObject(String key,Class<T> t) {
    Set<T> values = null;
    Set<byte[]> bytesvalues = null;
    Jedis jedis = null;
    try {
      jedis = redisUtils.jedisPool.getResource();
      bytesvalues = jedis.smembers(key.getBytes());
      values = new HashSet<T>();
      for(byte[] b : bytesvalues){
        values.add((T)SerializeUtil.unserialize(b));
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
    } finally {
      redisUtils.jedisPool.returnResourceObject(jedis);
    }
    return values;
  }



  /**
   * 刷新redis缓存
   *
   * @param key
   * @param value
   * @param timeout 过期时间秒,如果小于等于0，则永不过期
   * @return
   */
  public  static void setObject(String key, Object value, Integer timeout) {
    Jedis jedis = null;
    try {
      jedis = redisUtils.jedisPool.getResource();
      jedis.set(key.getBytes(), SerializeUtil.serialize(value));
      if(timeout > 0){
        jedis.expire(key.getBytes(), timeout);
      }


    } catch (Exception e) {
      logger.error(e.getMessage());
    } finally {
      redisUtils.jedisPool.returnResourceObject(jedis);
    }
  }


  public  static void fullAll() {
    Jedis jedis = null;
    try {
      jedis = redisUtils.jedisPool.getResource();
      jedis.flushAll();

    } catch (Exception e) {
      logger.error(e.getMessage());
    } finally {
      redisUtils.jedisPool.returnResourceObject(jedis);
    }
  }

  public static RedisUtils getRedisUtils() {
    return redisUtils;
  }

  public static void setRedisUtils(RedisUtils redisUtils) {
    RedisUtils.redisUtils = redisUtils;
  }





  /**
   * 获取redis缓存的值
   *
   * @param key
   * @return
   */
  public static <T>  T  get(String key, Class<T> t) {
    Object value = null;
    Jedis jedis = null;
    try {
      jedis = redisUtils.jedisPool.getResource();
      value = SerializeUtil.unserialize(jedis.get(key.getBytes()));
      if (value != null){
        return (T)value;
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
    } finally {
      redisUtils.jedisPool.returnResourceObject(jedis);
    }
    return null;
  }





  /**
   * 获取redis缓存的值
   *
   * @param key
   * @return
   */
  public static  Object getObject(String key) {
    Object value = null;
    Jedis jedis = null;
    try {
      jedis = redisUtils.jedisPool.getResource();
      byte[] bs = jedis.get(key.getBytes());
      if(bs == null || bs.length == 0){
        return null;
      }
      value = SerializeUtil.unserialize(bs);
    } catch (Exception e) {
      logger.error(e.getMessage());
    } finally {
      redisUtils.jedisPool.returnResourceObject(jedis);
    }
    return value;
  }


  /**
   * 删除redis缓存中的信息；
   *
   * @param key
   */
  public static void del(String key) {
    Jedis jedis = null;

    try {
      jedis = redisUtils.jedisPool.getResource();
      jedis.del(key);
    } catch (Exception e) {
      logger.error(e.getMessage());
    } finally {
      redisUtils.jedisPool.returnResourceObject(jedis);
    }
  }

  /**
   * 删除一个集合中的一个元素
   *
   * @param key
   * @param value
   */
  public  static void srem(String key, String value) {
    Jedis jedis = null;
    try {
      jedis = redisUtils.jedisPool.getResource();
      jedis.srem(key, value);
    } catch (Exception e) {
      logger.error(e.getMessage());
    } finally {
      redisUtils.jedisPool.returnResourceObject(jedis);
    }
  }


  /**
   * 删除一个集合中的一个元素
   *
   * @param key
   * @param value
   */
  public  static void srem(String key, Object value) {
    Jedis jedis = null;
    try {
      jedis = redisUtils.jedisPool.getResource();
      jedis.srem(key.getBytes(), SerializeUtil.serialize(value));
    } catch (Exception e) {
      logger.error(e.getMessage());
    } finally {
      redisUtils.jedisPool.returnResourceObject(jedis);
    }
  }



  /**
   * 获取redis缓存的值 【结合】
   *
   * @param key
   * @return
   */
  public static Set<String> keys(String key) {
    Set<String> value = null;
    Jedis jedis = null;
    try {
      jedis = redisUtils.jedisPool.getResource();
      value = jedis.keys(key);
    } catch (Exception e) {
      logger.error(e.getMessage());
    } finally {
      redisUtils.jedisPool.returnResourceObject(jedis);
    }
    return value;
  }


  /**
   * key改名
   *
   * @param oldkey
   * @param newkey
   * @param timeout 过期时间秒,如果小于等于0，则永不过期
   * @return
   */
  public  static void rename(String oldkey, String newkey, Integer timeout) {
    Jedis jedis = null;
    try {
      jedis = redisUtils.jedisPool.getResource();
      jedis.rename(oldkey, newkey);
      if(timeout > 0){
        jedis.expire(newkey, timeout);
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
    } finally {
      redisUtils.jedisPool.returnResourceObject(jedis);
    }
  }

  public static final long ONE_MILLI_NANOS = 1000000L;
  public static final long DEFAULT_TIME_OUT = 3000;
  //锁的超时时间（秒），过期删除
  public static final int EXPIRE = 5 * 60;
  public static final String LOCKED = "TRUE";
  public static final Random r = new Random();
  private static boolean locked = false;

  public static boolean lock( String key,long timeout) {
    long nano = System.nanoTime();
    timeout *= ONE_MILLI_NANOS;
    Jedis jedis = null;
    try {
      jedis = redisUtils.jedisPool.getResource();
      while ((System.nanoTime() - nano) < timeout) {
        if (jedis.setnx(key, LOCKED) == 1 ){
          jedis.expire(key, EXPIRE);
          locked = true;
          return locked;
        }
        // 短暂休眠，nano避免出现活锁
        Thread.sleep(0, r.nextInt(300));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }finally {
      redisUtils.jedisPool.returnResourceObject(jedis);
    }
    locked= false;
    return locked;
  }

  /**
   * 刷新redis缓存
   *
   * @param key
   * @param value
   * @param timeout 过期时间秒,如果小于等于0，则永不过期
   * @return
   */
  public  static void rpushObject(String key, Object value, Integer timeout) {
    Jedis jedis = null;
    try {
      jedis = redisUtils.jedisPool.getResource();
      jedis.rpush(key.getBytes(), SerializeUtil.serialize(value));

      if(timeout > 0){
        jedis.expire(key, timeout);
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
    } finally {
      redisUtils.jedisPool.returnResourceObject(jedis);
    }
  }



  @SuppressWarnings("unchecked")
  public static <T> List<T> lrangeObject(String key,Class<T> t) {
    List<T> values = null;
    List<byte[]> bytesvalues = null;
    Jedis jedis = null;
    try {
      jedis = redisUtils.jedisPool.getResource();
      Long end=jedis.llen(key);
      bytesvalues = (List<byte[]>) jedis.lrange(key.getBytes(), 0, end.intValue());

      values = new ArrayList<T>();
      for(byte[] b : bytesvalues){
        values.add((T)SerializeUtil.unserialize(b));
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
    } finally {
      redisUtils.jedisPool.returnResourceObject(jedis);
    }
    return values;
  }

  /**
   * 将哈希表 key 中的域 field 的值设为 value 。
   * 如果 key 不存在，一个新的哈希表被创建并进行 HSET 操作。
   * 如果域 field 已经存在于哈希表中，旧值将被覆盖。
   * @param key A String
   * @param field A String
   * @param value A String
   * @param {@code}
   * @return 如果 field 是哈希表中的一个新建域，并且值设置成功，返回 1 。</br>
   * 如果哈希表中域 field 已经存在且旧值已被新值覆盖，返回 0 。
   */
  public static Long mpush(String key,String field,String value){
    Jedis jedis = null;
    try {
      jedis = redisUtils.jedisPool.getResource();
      return jedis.hset(key, field, value);

    } catch (Exception e) {
      logger.error(e.getMessage());
    } finally {
      redisUtils.jedisPool.returnResourceObject(jedis);
    }
    return 0L;
  }


  /**
   * 将哈希表 key 中的域 field 的值设为 序列化后的value 。
   * 如果 key 不存在，一个新的哈希表被创建并进行 HSET 操作。
   * 如果域 field 已经存在于哈希表中，旧值将被覆盖。
   * @param key A String
   * @param field A String
   * @param object A Object
   * @param {@code}
   * @return 如果 field 是哈希表中的一个新建域，并且值设置成功，返回 1 。</br>
   * 如果哈希表中域 field 已经存在且旧值已被新值覆盖，返回 0 。
   */
  public static Long mpush(String key,String field,Object object){
    Jedis jedis = null;
    try {
      jedis = redisUtils.jedisPool.getResource();
      return jedis.hset(key.getBytes(), field.getBytes(), SerializeUtil.serialize(object));
    } catch (Exception e) {
      logger.error(e.getMessage());
    } finally {
      redisUtils.jedisPool.returnResourceObject(jedis);
    }
    return 0L;
  }

  public static String mget(String key,String field){
    Jedis jedis = null;
    try {
      jedis = redisUtils.jedisPool.getResource();
      return jedis.hget(key, field);
    } catch (Exception e) {
      logger.error("hget error",e);
    } finally {
      redisUtils.jedisPool.returnResourceObject(jedis);
    }
    return "";
  }


  public static <T> T mgetObject(String key, String field){
    Jedis jedis = null;
    try {
      jedis = redisUtils.jedisPool.getResource();
      return (T) SerializeUtil.unserialize(jedis.hget(key.getBytes(), field.getBytes()));
    } catch (Exception e) {
      logger.error("hget error",e);
    } finally {
      redisUtils.jedisPool.returnResourceObject(jedis);
    }
    return null;
  }


  public static Long hdel(String key,String field){
    Jedis jedis = null;
    try {
      jedis = redisUtils.jedisPool.getResource();
      return jedis.hdel(key, field);
    } catch (Exception e) {
      logger.error(e.getMessage());
    } finally {
      redisUtils.jedisPool.returnResourceObject(jedis);
    }
    return 0L;
  }


  /**
   * 查看哈希表 key 中，给定域 field 是否存在。
   * @param key
   * @param field
   * @return 如果哈希表含有给定域，返回 1 。</br>
   *    如果哈希表不含有给定域，或 key 不存在，返回 0 。
   */
  public static boolean hexists(String key,String field){
    Jedis jedis = null;
    try {
      jedis = redisUtils.jedisPool.getResource();
      return jedis.hexists(key, field);
    } catch (Exception e) {
      logger.error(e.getMessage());
    } finally {
      redisUtils.jedisPool.returnResourceObject(jedis);
    }
    return false;
  }

  /**
   * 查看哈希表 key 中，给定域 field 是否存在。
   *
   * @param key
   * @param field
   * @return 如果哈希表含有给定域，返回 1 。</br>
   *    如果哈希表不含有给定域，或 key 不存在，返回 0 。
   */
  public static boolean hexists(byte[] key,byte[] field){
    Jedis jedis = null;
    try {
      jedis = redisUtils.jedisPool.getResource();
      return jedis.hexists(key, field);
    } catch (Exception e) {
      logger.error(e.getMessage());
    } finally {
      redisUtils.jedisPool.returnResourceObject(jedis);
    }
    return false;
  }


  public static boolean lock(String key) {
    return lock(key,DEFAULT_TIME_OUT);
  }

  // 无论是否加锁成功，必须调用
  public static void unlock(String key) {
    Jedis jedis = null;
    try {
      if (locked){
        jedis = redisUtils.jedisPool.getResource();
        jedis.del(key);
      }
    } finally {
      redisUtils.jedisPool.returnResourceObject(jedis);
    }
  }


  /**
   * 设置过期时间
   * @param key
   * @param expireTime 单位秒
   * @return
   */
  public static Long expire(String key,int expireTime){
    Jedis jedis = null;
    Long value = null;
    try {
      jedis = redisUtils.jedisPool.getResource();
      value = jedis.expire(key, expireTime);
    } finally {
      redisUtils.jedisPool.returnResourceObject(jedis);
    }
    return value;
  }



  /**
   * 全局扫描hset
   *
   * @param match field匹配模式
   * @return
   */
  public static List<Map.Entry<String, String>> scanHSet(String key, String match) {
    Jedis jedis = null;
    try {
      int cursor = 0;
      jedis = redisUtils.jedisPool.getResource();
      ScanParams scanParams = new ScanParams();
      scanParams.match(match).count(Integer.MAX_VALUE);
      ScanResult<Map.Entry<String, String>> scanResult;
      List<Map.Entry<String, String>> list = new ArrayList<Map.Entry<String, String>>();
      do {
        scanResult = jedis.hscan(key, String.valueOf(cursor), scanParams);
        list.addAll(scanResult.getResult());
        cursor = Integer.parseInt(scanResult.getStringCursor());
      } while (cursor > 0);
      return list;
    } catch (Exception ex) {
      logger.error("scanHSet error.", ex);
      redisUtils.jedisPool.returnResourceObject(jedis);
    } finally {
      redisUtils.jedisPool.returnResourceObject(jedis);
    }
    return null;
  }


  public void setJedisPool(JedisPool jedisPool) {
    this.jedisPool = jedisPool;
  }

  public JedisPool getJedisPool() {
    return jedisPool;
  }
}
