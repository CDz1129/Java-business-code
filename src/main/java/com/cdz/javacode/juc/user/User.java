package com.cdz.javacode.juc.user;

import lombok.Data;
import org.hibernate.annotations.Table;
import org.springframework.data.annotation.Id;

/**
 * User
 *
 * @author chendezhi
 * @date 2020/12/2 15:48
 * @since 1.0.0
 */
@Data
public class User {
    @Id
    private Integer id;
    private String name;
}
