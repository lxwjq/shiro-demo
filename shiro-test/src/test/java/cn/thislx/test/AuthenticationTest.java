package cn.thislx.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

/**
 * @Author: LX
 * @Description: 认证
 * @Date: Created in 21:45 2018/7/26
 * @Modified by:
 */
public class AuthenticationTest {

    // 创建一个Relam
    SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();

    @Before
    public void before() {
        simpleAccountRealm.addAccount("ThisLX", "123456", "admin", "user");
    }

    /**
     * @Author: LX
     * @Description: shiro认证与授权
     * @Date: 2018/7/26 21:55
     * @Modified by:
     */
    @Test
    public void testAuthentication() {

        //1、构建securityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(simpleAccountRealm);

        //2、获得提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("ThisLX",
                "123456");

        // 登陆操作
        subject.login(token);

        //验证是否认证
        System.out.println("isAuthenticatin:" + subject.isAuthenticated());

        //登出操作
        /*subject.logout();
        System.out.println("isAuthenticatin:" + subject.isAuthenticated());*/

        //检查是否授权
        subject.checkRole("admin");
        subject.checkRoles("admin", "user");
    }


}
