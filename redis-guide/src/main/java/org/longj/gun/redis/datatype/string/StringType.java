package org.longj.gun.redis.datatype.string;

import org.longj.gun.redis.connection.RedisConnect;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

import java.util.concurrent.TimeUnit;

/**
 * @Author: LongJ
 * @Date: 2020/03/12 23:01
 * @Description:
 */
public class StringType {

    /**
     * 获取键对应的值
     * @param jedis redis连接
     * @param key 键
     * @return null - 键不存在时
     */
    public static String get(Jedis jedis,String key){
        return jedis.get(key);
    }

    /**
     * 当key不存在的时候，设置键值对
     * @param jedis redis连接
     * @param key 键
     * @param val 值
     * @return OK - 执行成功<br/>null - 执行失败
     */
    public static String setNx(Jedis jedis,String key,String val){
        return jedis.set(key,val,new SetParams().nx());
    }

    /**
     * 当key存在的时候，设置键值对
     * @param jedis redis连接
     * @param key 键
     * @param val 值
     * @return OK - 执行成功<br/>null - 执行失败
     */
    public static String setXx(Jedis jedis,String key,String val){
        return jedis.set(key,val,new SetParams().xx());
    }

    /**
     * 设置键并添加过期时间
     * @param jedis redis连接
     * @param key 键
     * @param val 值
     * @param secondsToExpire 过期时间（秒）
     * @return OK - 执行成功<br/>null - 执行失败
     */
    public static String setEx(Jedis jedis,String key,String val,int secondsToExpire){
        if ( secondsToExpire <= 0 ){
            throw new IllegalArgumentException("过期时间不合法，必须是大于0");
        }
        return jedis.set(key,val,new SetParams().ex(secondsToExpire));
    }

    /**
     * 设置键并添加过期时间
     * @param jedis redis连接
     * @param key 键
     * @param val 值
     * @param millisecondsToExpire 过期时间（毫秒）
     * @return OK - 执行成功<br/>null - 执行失败
     */
    public static String setPx(Jedis jedis,String key,String val,long millisecondsToExpire){
        if ( millisecondsToExpire <= 0 ){
            throw new IllegalArgumentException("过期时间不合法，必须是大于0");
        }
        return jedis.set(key,val,new SetParams().px(millisecondsToExpire));
    }

    /**
     * 获取键的过期时间
     * @param jedis redis连接
     * @param key 键
     * @return 过期时间（大于0） - 键存在<br/>
     *         -1 - 键永不过期<br/>
     *         -2 - 键不存在（或者键已过期）<br/>
     *
     */
    public static Long ttl(Jedis jedis,String key){
        return jedis.ttl(key);
    }

    /**
     * 获取字符串长度
     * @param jedis redis连接
     * @param key 键
     * @return 值长度（返回0时可能是键不存在）
     */
    public static Long strLen(Jedis jedis,String key){
        return jedis.strlen(key);
    }

    /**
     * 追加字符串
     * <p>如果键 key 已经存在并且它的值是一个字符串， APPEND 命令将把 value 追加到键 key 现有值的末尾。</p>
     * <p>如果 key 不存在， APPEND 就简单地将键 key 的值设为 value ， 就像执行 <code>SET key value</code> 一样。</p>
     * @param jedis redis连接
     * @param key 键
     * @param val 值
     * @return 新字符串的长度
     */
    public static Long append(Jedis jedis,String key,String val){
        return jedis.append(key,val);
    }

    /**
     * 判断键是否存在
     * @param jedis redis连接
     * @param key 键
     * @return true - 存在<br/>false - 不存在
     */
    public static Boolean exists(Jedis jedis,String key){
        return jedis.exists(key);
    }

    /**
     * 修改部分字符串
     * <p>从偏移量 offset 开始， 用 value 参数覆写(overwrite)键 key 储存的字符串值。</p>
     * <p>不存在的键 key 当作空白字符串处理。</p>
     * <p>SETRANGE 命令会确保字符串足够长以便将 value 设置到指定的偏移量上，
     * 如果键 key 原来储存的字符串长度比偏移量小(比如字符串只有 5 个字符长，但你设置的 offset 是 10 )，
     * 那么原字符和偏移量之间的空白将用零字节(zerobytes, "\x00" )进行填充。</p>
     * <p>因为 Redis 字符串的大小被限制在 512 兆(megabytes)以内， 所以用户能够使用的最大偏移量为 2^29-1(536870911) ， 如果你需要使用比这更大的空间， 请使用多个 key </p>
     *
     * @param jedis redis连接
     * @param key 键
     * @param offset 偏移量
     * @param val 值
     * @return 新字符串的长度
     */
    public static Long setRange(Jedis jedis,String key,Long offset,String val){
        return jedis.setrange(key,offset,val);
    }

    public static void main(String[] args) throws InterruptedException {
        Jedis jedis = RedisConnect.connectRedis();
//        StringType stringType = new StringType();
//        System.out.println(StringType.exists(jedis,"city1"));
//        System.out.println(StringType.setRange(jedis,"city1",10L,"Xiamen"));
//        System.out.println(StringType.get(jedis,"city1"));
        System.out.println(StringType.strLen(jedis,"city1"));
//        System.out.println(StringType.get(jedis,"city1"));
//        System.out.println(StringType.get(jedis,"city"));
//        System.out.println("city长度："+StringType.exists(jedis,"city"));
//        System.out.println(StringType.get(jedis,"city"));
//        System.out.println(StringType.setNx(jedis,"city","zhangzhou"));
//        System.out.println("city过期时间："+StringType.ttl(jedis,"city"));
//        System.out.println(StringType.setXx(jedis,"city","xiamen"));
//        System.out.println(StringType.setEx(jedis,"city","xiamen",5));
//        System.out.println("city过期时间："+StringType.ttl(jedis,"city"));
//        System.out.println("city长度："+StringType.strLen(jedis,"city"));
//        System.out.println(StringType.setXx(jedis,"city",""));
//        System.out.println("city是否存在："+StringType.exists(jedis,"city"));
//        System.out.println("city长度："+StringType.strLen(jedis,"city"));
//        System.out.println("sex长度："+StringType.strLen(jedis,"sex"));
//        System.out.println(jedis.getDB());

//        TimeUnit.SECONDS.sleep(5L);
//        System.out.println("sex过期时间："+StringType.ttl(jedis,"sex"));
//        System.out.println("city过期时间："+StringType.ttl(jedis,"city"));
//        System.out.println(StringType.setPx(jedis,"city","xiamen",4000));
//        System.out.println("city过期时间："+StringType.ttl(jedis,"city"));
//        System.out.println("sex过期时间："+StringType.ttl(jedis,"sex"));
//        TimeUnit.SECONDS.sleep(2L);
//        System.out.println("city过期时间："+StringType.ttl(jedis,"city"));

    }
}
