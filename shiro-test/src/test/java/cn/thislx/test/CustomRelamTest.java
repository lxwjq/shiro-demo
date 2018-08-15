package cn.thislx.test;

import cn.thislx.relam.CustomRelam;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * @Author: LX
 * @Description:自定义Relam测试
 * @Date: Created in 19:49 2018/8/2
 * @Modified by:
 */
public class CustomRelamTest {

    @Test
    public void testCustomRelam() {
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        CustomRelam customRelam = new CustomRelam();

        //对数据进行加密处理
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        //设置加密的算法
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        //设置加密的次数
        hashedCredentialsMatcher.setHashIterations(1);
        customRelam.setCredentialsMatcher(hashedCredentialsMatcher);

        defaultSecurityManager.setRealm(customRelam);

        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("ThisLx", "123456");
        subject.login(token);

        System.out.println("isAutherication:" + subject.isAuthenticated());

        subject.checkRoles("admin", "user");
        subject.checkPermissions("user:delete", "user:find");
    }
}
