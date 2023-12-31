package com.fish.common.util;

import org.apache.shiro.SecurityUtils;

import com.fish.common.constant.Base;
import com.fish.entity.User;

/**
 * @author fish
 * <p>
 * 2018年1月25日
 */
public class UserUtils {

    public static User getCurrentUser() {
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute(Base.CURRENT_USER);
        return user;
    }
}
