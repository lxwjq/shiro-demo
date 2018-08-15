package cn.thislx.utils.jedis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @Author: LX
 * @Description: Jedis工具类
 * @Date: Created in 19:03 2018/8/7
 * @Modified by:
 */
@Component
public class JedisUtils {

    @Autowired
    private JedisPool jedisPool;

    private Jedis getJedis() {
        return jedisPool.getResource();
    }


    /**
     * @param key:    key
     * @param value:  value
     * @param expire: 保存时间
     * @Author: LX
     * @Description: 保存
     * @Date: 2018/8/7 19:12
     * @Modified by:
     */
    public void set(byte[] key, byte[] value, int expire) {
        Jedis jedis = getJedis();
        try {
            jedis.set(key, value);
            jedis.expire(key, expire);
        } finally {
            jedis.close();
        }
    }

    /**
     * @Author: LX
     * @Description: 获得缓存中的数据
     * @Date: 2018/8/7 19:23
     * @Modified by:
     */
    public byte[] get(byte[] key) {
        Jedis jedis = getJedis();
        byte[] bytes;
        try {
            bytes = jedis.get(key);
            return bytes;
        } finally {
            jedis.close();
        }
    }


    public void del(byte[] key) {
        Jedis jedis = getJedis();
        try {
            jedis.del(key);
        } finally {
            jedis.close();
        }
    }

    public Set<byte[]> getKeys(String shiroSessionPerfix) {
        Jedis jedis = getJedis();
        try {
            return jedis.keys((shiroSessionPerfix + "*").getBytes());
        } finally {

        }
    }
}
