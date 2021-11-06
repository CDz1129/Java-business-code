package com.cdz.javacode.i18n;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("i18n")
public class I18nController {
    @GetMapping("username")
    public User getUser(){
        User user = new User();
        user.setUsername("user_name");
        return user;
    }
}
