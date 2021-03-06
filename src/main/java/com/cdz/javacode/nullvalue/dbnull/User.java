package com.cdz.javacode.nullvalue.dbnull;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * User
 *
 * @author chendezhi
 * @date 2021/1/5 16:04
 * @since 1.0.0
 */
@Entity(name = "user")
@Table(name = "user")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String name;
}
