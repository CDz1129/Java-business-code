package com.cdz.javacode.nullvalue.dbnull;

import com.cdz.javacode.JavacodeApplicationTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.junit.jupiter.api.Assertions.*;

/**
 * UserRepositoryTest
 *
 * @author chendezhi
 * @date 2021/1/6 11:10
 * @since 1.0.0
 */
@Component
class UserRepositoryTest extends JavacodeApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    void name() {
        userRepository.right1();
    }
}