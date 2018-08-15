package cn.thislx.session;

import cn.thislx.utils.jedis.JedisUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.util.CollectionUtils;
import org.springframework.util.SerializationUtils;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author: LX
 * @Description: 自定义shiro的Session管理
 * @Date: Created in 19:04 2018/8/7
 * @Modified by:
 */
public class RedisSessionDao extends AbstractSessionDAO {

    @Resource
    private JedisUtils jedisUtils;

    /**
     * session前缀
     */
    private final static String SHIRO_SESSION_PERFIX = "shiro_session";

    /**
     * 封装 redis key
     *
     * @param key
     * @return
     */
    private byte[] getKey(String key) {
        return (SHIRO_SESSION_PERFIX + key).getBytes();
    }

    private void saveSession(Session session) {
        if (session != null && session.getId() != null) {
            byte[] key = getKey(session.getId().toString());
            byte[] value = SerializationUtils.serialize(session);
            jedisUtils.set(key, value, 600);
        }

    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);
        saveSession(session);
        return sessionId;
    }

    /**
     * 获得session
     *
     * @param serializable
     * @return
     */
    @Override
    protected Session doReadSession(Serializable serializable) {
        System.out.println("read session");
        if (serializable == null) {
            return null;
        }

        byte[] key = getKey(serializable.toString());
        byte[] value = jedisUtils.get(key);

        return (Session) SerializationUtils.deserialize(value);
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        saveSession(session);
    }

    @Override
    public void delete(Session session) {
        if (session == null || session.getId() == null) {
            return;
        }
        byte[] key = getKey(session.getId().toString());
        jedisUtils.del(key);
    }

    @Override
    public Collection<Session> getActiveSessions() {
        Set<byte[]> keys = jedisUtils.getKeys(SHIRO_SESSION_PERFIX);
        Set<Session> sessionSet = new HashSet<Session>();

        if (CollectionUtils.isEmpty(keys)) {
            return null;
        }

        for (byte[] temp : keys) {
            Session session = (Session) SerializationUtils.deserialize(jedisUtils.get(temp));
            sessionSet.add(session);
        }
        return sessionSet;
    }
}
