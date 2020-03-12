package org.longj.gun.redis.data_type.string;

import org.longj.gun.redis.connection.RedisConnect;
import redis.clients.jedis.Jedis;

/**
 * @Author: LongJ
 * @Date: 2020/03/12 23:01
 * @Description:
 */
public class StringType {

    public static void main(String[] args) {
        Jedis jedis = RedisConnect.connectRedis();

    }
}
