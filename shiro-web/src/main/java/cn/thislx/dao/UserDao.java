package cn.thislx.dao;

import cn.thislx.core.User;

import java.util.List;
import java.util.Set;

/**
 * @Author: LX
 * @Description:
 * @Date: Created in 14:53 2018/8/3
 * @Modified by:
 */
public interface UserDao {

    User findByUserName(String userName);

    List<String> findPermissionByUserName(String userName);
}
