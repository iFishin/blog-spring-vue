package com.fish.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.support.spring.annotation.FastJsonFilter;
import com.alibaba.fastjson.support.spring.annotation.FastJsonView;
import com.fish.common.annotation.LogAnnotation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fish.common.constant.Base;
import com.fish.common.constant.ResultCode;
import com.fish.common.result.Result;
import com.fish.common.util.UserUtils;
import com.fish.entity.User;
import com.fish.service.UserService;

/**
 * 用户api
 *
 * @author fish
 * <p>
 * 2018年1月23日
 */
@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    @LogAnnotation(module = "用户", operation = "获取所有用户")
    @RequiresRoles(Base.ROLE_ADMIN)
    public Result listUsers() {
        List<User> users = userService.findAll();

        return Result.success(users);
    }

    @GetMapping("/{account}")
    @LogAnnotation(module = "用户", operation = "根据id获取用户")
    @RequiresRoles(Base.ROLE_ADMIN)
    public Result getUserById(@PathVariable("account") String account) {

        Result r = new Result();

        if (null == account) {
            r.setResultCode(ResultCode.PARAM_IS_BLANK);
            return r;
        }

        User user = userService.getUserByAccount(account);

        r.setResultCode(ResultCode.SUCCESS);
        r.setData(user);
        return r;
    }

    @GetMapping("/currentUser")
    @FastJsonView(
            include = {@FastJsonFilter(clazz = User.class, props = {"id", "account", "nickname", "avatar"})})
    @LogAnnotation(module = "用户", operation = "获取当前登录用户")
    public Result getCurrentUser(HttpServletRequest request) {

        Result r = new Result();

        User currentUser = UserUtils.getCurrentUser();

        r.setResultCode(ResultCode.SUCCESS);
        r.setData(currentUser);
        return r;
    }

    @PostMapping("/create")
    @RequiresRoles(Base.ROLE_ADMIN)
    @LogAnnotation(module = "用户", operation = "添加用户")
    public Result saveUser(@Validated @RequestBody User user) {

        Long userId = userService.saveUser(user);

        Result r = Result.success();
        r.simple().put("userId", userId);
        return r;
    }

    @PostMapping("/update")
    @RequiresRoles(Base.ROLE_ADMIN)
    @LogAnnotation(module = "用户", operation = "修改用户")
    public Result updateUser(@RequestBody  User user) {
        System.out.println(user);
        Result r = new Result();
        if (user.getId() == null) {
            r.setResultCode(ResultCode.USER_NOT_EXIST);
            return r;
        }

        Long userId = userService.updateUser(user);

        r.setResultCode(ResultCode.SUCCESS);
        r.simple().put("userId", userId);
        return null;
    }

    @GetMapping("/delete/{id}")
    @RequiresRoles(Base.ROLE_ADMIN)
    @LogAnnotation(module = "用户", operation = "删除用户")
    public Result deleteUserById(@PathVariable("id") Long id) {
        System.out.println(id);
        Result r = new Result();

        if (null == id) {
            r.setResultCode(ResultCode.PARAM_IS_BLANK);
            return r;
        }

        userService.deleteUserById(id);

        r.setResultCode(ResultCode.SUCCESS);
        return r;
    }

}
