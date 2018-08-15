package cn.thislx.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

import javax.annotation.Resource;

/**
 * @Author: LX
 * @Description:
 * @Date: Created in 19:05 2018/8/14
 * @Modified by:
 */
public class RedisCacheManager implements CacheManager{

    @Resource
    private RedisCache redisCache;

    @Override
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        return redisCache;
    }
}
