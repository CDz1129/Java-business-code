package com.cdz.javacode.oom.usernameautocomplete;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author david.chen
 * @date 2021/12/13 18:50
 */
@Data
public class UserDTO {
    private String username;
    @EqualsAndHashCode.Exclude
    private String payload;

    public UserDTO(String username) {
        this.username = username;
        this.payload = IntStream.rangeClosed(1,10_000)
                .mapToObj(__->"a")
                .collect(Collectors.joining(""));
    }
}
