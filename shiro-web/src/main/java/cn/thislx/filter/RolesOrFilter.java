package cn.thislx.filter;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.ArrayList;

/**
 * @Author: LX
 * @Description: shiro自定义filter
 * @Date: Created in 18:23 2018/8/7
 * @Modified by:
 */
public class RolesOrFilter extends AuthorizationFilter {
    @Override

    protected boolean isAccessAllowed(
            ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        Subject subject = getSubject(servletRequest, servletResponse);

        String[] roles = (String[]) o;

        //没有权限限制直接返回true
        if (ArrayUtils.isEmpty(roles)) {
            return true;
        }

        for (String tempRole : roles) {
            if (subject.hasRole(tempRole)) {
                return true;
            }
        }
        return false;
    }
}
