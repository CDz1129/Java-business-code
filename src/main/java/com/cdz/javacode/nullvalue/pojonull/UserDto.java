package com.cdz.javacode.nullvalue.pojonull;

import lombok.Data;

import java.util.Optional;

/**
 * UserDto
 *
 * @author chendezhi
 * @date 2021/1/5 11:14
 * @since 1.0.0
 */
@Data
public class UserDto {

    private Long id;

    private Optional<String> name;

    private Optional<Integer> age;
}
