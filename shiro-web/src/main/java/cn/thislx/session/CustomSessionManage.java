package cn.thislx.session;

import java.io.Serializable;

import javax.servlet.ServletRequest;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.WebSessionKey;

/**
 * @Author: LX
 * @Description: 自定义SessionManager防止获取缓存多次访问redis
 * @Date: 2018/8/14 18:49
 * @Modified by:
 */
public class CustomSessionManage extends DefaultWebSessionManager {

    @Override
    protected Session retrieveSession(SessionKey sessionKey) throws UnknownSessionException {

        Serializable sessionId = sessionKey.getSessionId();
        //首先从request中获取  获取不到在访问redis 并设置到request
        ServletRequest request = null;

        if (sessionKey instanceof WebSessionKey) {
            request = ((WebSessionKey) sessionKey).getServletRequest();
        }
        if (request != null && sessionId != null) {
            Session session = (Session) request.getAttribute(sessionId.toString());
            if (session != null) {
                return session;
            }
        }

        //从redis中获取
        Session session = super.retrieveSession(sessionKey);
        if (request != null && sessionId != null) {
            //设置到request
            request.setAttribute(sessionId.toString(), session);
        }
        return session;
    }
}