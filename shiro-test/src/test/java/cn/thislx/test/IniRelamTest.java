package cn.thislx.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * @Author: LX
 * @Description:
 * @Date: Created in 21:59 2018/7/26
 * @Modified by:
 */
public class IniRelamTest {

    IniRealm iniRealm = new IniRealm("classpath:user.ini");

    @Test
    public void testIniRelam() {

        //创建一个SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(iniRealm);

        //获取一个提交对象
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("ThisLX",
                "123456");
        subject.login(token);

        System.out.println("isAutherication:" + subject.isAuthenticated());
        subject.checkRole("admin");
        subject.checkPermission("user:view");
        subject.checkPermissions("user:view","user:delete");
    }
}
