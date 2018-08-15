package cn.thislx.relam;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.*;

/**
 * @Author: LX
 * @Description: 自定义Relam
 * @Date: Created in 19:30 2018/8/2
 * @Modified by:
 */
public class CustomRelam extends AuthorizingRealm {

    private Map<String, String> map = new HashMap<String, String>(16);

    {
        map.put("ThisLx", "b5d194426d3942195e530609ba47c760");
        super.setName("customRelam");
    }

    /**
     * @param principalCollection:
     * @Author: LX
     * @Description: 用于授权
     * @Date: 2018/8/2 19:38
     * @Modified by:
     */
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String userName = principalCollection.getPrimaryPrincipal().toString();

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        simpleAuthorizationInfo.setRoles(findRolesByUserName(userName));
        simpleAuthorizationInfo.setStringPermissions(findPermissionByUserName(userName));

        return simpleAuthorizationInfo;
    }


    /**
     * @param userName
     * @Author: LX
     * @Description: 根据名称查询权限
     * @Date: 2018/8/2 20:01
     * @Modified by:
     */
    private Set<String> findPermissionByUserName(String userName) {
        Set<String> permissions = new HashSet<String>();
        permissions.add("user:delete");
        permissions.add("user:find");
        return permissions;
    }

    /**
     * @Author: LX
     * @Description: 根据名称查询角色
     * @Date: 2018/8/2 20:01
     * @Modified by:
     */
    private Set<String> findRolesByUserName(String userName) {
        Set<String> roles = new HashSet<String>();
        roles.add("admin");
        roles.add("user");
        return roles;
    }


    /**
     * @param authenticationToken: 主体传递的认证信息
     * @Author: LX
     * @Description:用于认证
     * @Date: 2018/8/2 19:38
     * @Modified by:
     */
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //获取登录的用户名
        String userName = authenticationToken.getPrincipal().toString();

        //下面执行业务逻辑查询密码
        String passWord = findMsgByUserName(userName);

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo("ThisLx",
                passWord, "customRelam");

        //加盐
        authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes("ThisLx"));
        return authenticationInfo;
    }

    /**
     * @param userName:用户名
     * @Author: LX
     * @Description: 根据用户名查询密码
     * @Date: 2018/8/2 19:41
     * @Modified by:
     */
    private String findMsgByUserName(String userName) {
        return map.get(userName);
    }

    public static void main(String[] args) {
        //加密+加盐
        Md5Hash md5Hash = new Md5Hash("123456", "ThisLx");
        System.out.println(md5Hash);
    }
}
