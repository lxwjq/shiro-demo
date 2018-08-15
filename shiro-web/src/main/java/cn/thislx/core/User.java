package cn.thislx.core;

import lombok.Data;
import lombok.ToString;

/**
 * @Author: LX
 * @Description:
 * @Date: Created in 21:48 2018/8/2
 * @Modified by:
 */
@Data
@ToString
public class User {

    private String id;

    private String userName;

    private String password;
}
