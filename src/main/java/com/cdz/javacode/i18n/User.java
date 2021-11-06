package com.cdz.javacode.i18n;

import lombok.Data;

@Data
public class User {
    @I18nProperty
    private String username;
    private String password;
}
