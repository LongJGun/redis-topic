package org.longj.gun.redis.connection;

import org.junit.Test;
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
//        jedis.auth("123456");
        if ( "PONG".equalsIgnoreCase(jedis.ping()) ) {
            return jedis;
        } else {
            throw new RuntimeException("Redis 连接失败");
        }
    }

}
