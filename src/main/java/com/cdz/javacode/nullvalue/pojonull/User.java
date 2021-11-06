package com.cdz.javacode.nullvalue.pojonull;

import lombok.Data;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * User
 *
 * @author chendezhi
 * @date 2021/1/4 16:52
 * @since 1.0.0
 *
 */
@Data
@Entity(name = "user1")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String nickname;

    private Integer age;

    private Date createDate = new Date();
}
