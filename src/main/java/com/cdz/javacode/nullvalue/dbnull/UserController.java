package com.cdz.javacode.nullvalue.dbnull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserController
 *
 * @author chendezhi
 * @date 2021/1/6 11:12
 * @since 1.0.0
 */
@RestController
@RequestMapping("db-null-user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("wrong1")
    public Long wrong1(){
        return userRepository.wrong1();
    }
    @GetMapping("wrong2")
    public Long wrong2(){
        return userRepository.wrong2();
    }
    @GetMapping("wrong3")
    public Long wrong3(){
        return userRepository.wrong3();
    }
    @GetMapping("right1")
    public Long right1(){
        return userRepository.right1();
    }
    @GetMapping("right2")
    public Long right2(){
        return userRepository.right2();
    }
    @GetMapping("right3")
    public Long right3(){
        return userRepository.right3();
    }
}
