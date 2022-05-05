package com.cdz.javacode.oom.usernameautocomplete;

import lombok.Data;

/**
 * @author david.chen
 * @date 2021/12/13 19:04
 */
@Data
public class UserEntity {
    private Long id;
    private String name;

    public UserEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
