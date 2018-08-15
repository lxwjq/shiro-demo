package cn.thislx.test;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * @Author: LX
 * @Description: JdbcRelam的使用  使用自己的数据库表进行查询
 * @Date: Created in 22:39 2018/7/26
 * @Modified by:
 */
public class CustomSqlJdbcRelamTest {

    //创建数据源
    private static DruidDataSource dataSource = new DruidDataSource();

    static {
        //设置数据源链接信息
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/test");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
    }

    @Test
    public void testJdbcRelam() {

        JdbcRealm jdbcRealm = new JdbcRealm();
        jdbcRealm.setDataSource(dataSource);
        String userSql = "select pwd from user_test where name = ?";
        String roleSql = "select role from user_roles_test where name = ?";
        jdbcRealm.setAuthenticationQuery(userSql);
        jdbcRealm.setUserRolesQuery(roleSql);

        // TODO 设置权限的开关  默认为false   不会查询权限数据表
        jdbcRealm.setPermissionsLookupEnabled(true);
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(jdbcRealm);


        //获取一个提交对象
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("lixiang",
                "123456");
        subject.login(token);

        System.out.println("isAutherication:" + subject.isAuthenticated());

        subject.checkRole("admin");
//
//        subject.checkPermissions("user:delete", "user:view");

    }
}
