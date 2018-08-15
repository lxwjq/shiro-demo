package cn.thislx.controller;

import cn.thislx.core.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: LX
 * @Description:
 * @Date: Created in 21:44 2018/8/2
 * @Modified by:
 */
@Controller
public class LoginController {


    @RequestMapping(value = "/loginCheck", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String login(User user) {
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(), user.getPassword());
        try {
            //记住我
            token.setRememberMe(true);
            subject.login(token);
            subject.checkRole("admin");
            return "成功";
        } catch (AuthenticationException e) {
            return e.getMessage();
        }
    }

    @RequestMapping(value = "/testRole", method = RequestMethod.GET)
    @ResponseBody
    public String testRole() {
        return "testRole";
    }

    @RequestMapping(value = "/testRole1", method = RequestMethod.GET)
    @ResponseBody
    public String testRole1() {
        return "testRole1";
    }


    @RequestMapping(value = "/testPermission", method = RequestMethod.GET)
    @ResponseBody
    public String testPermission() {
        return "testPermission";
    }

    @RequestMapping(value = "/testPermission1", method = RequestMethod.GET)
    @ResponseBody
    public String testPermission1() {
        return "testPermission1";
    }

}
