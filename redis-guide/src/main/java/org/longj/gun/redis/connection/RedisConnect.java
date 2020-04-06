package org.longj.gun.redis.connection;

import org.longj.gun.redis.datatype.string.StringType;
import redis.clients.jedis.Jedis;

/**
 * @Author: LongJ
 * @Date: 2020/03/12 22:46
 * @Description:
 */
public class RedisConnect {

    private static String host = "192.168.1.11";
    private static int port = 6379;

    public static Jedis connectRedis() {
        Jedis jedis = new Jedis(host,port);
        jedis.auth("123456");
        if ( "PONG".equalsIgnoreCase(jedis.ping()) ) {
            return jedis;
        } else {
            throw new RuntimeException("Redis 连接失败");
        }
    }

    public static void initData(Jedis jedis){
        // 字符串初始化
        StringType.setNx(jedis,"name","LongJ");
        StringType.setNx(jedis,"age","25");
        StringType.setNx(jedis,"city","Zhangzhou");
        StringType.setNx(jedis,"work","Aluba");
        StringType.setNx(jedis,"wx","JJWW");

    }

}
