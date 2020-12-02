package com.cdz.javacode.juc;

import com.cdz.javacode.juc.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * L04HikariConnetPoll
 *
 * @author chendezhi
 * @date 2020/12/2 15:44
 * @since 1.0.0
 *
 * 测试设置多大线程池
 */
@RequestMapping("04l-hikari-connect")
@RestController
public class L04HikariConnectPoll {

    @Autowired
    private UserService userService;
    @PostMapping
    public void registerUser(){
       userService.registerUser();
    }
}
