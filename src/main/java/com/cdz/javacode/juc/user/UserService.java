package com.cdz.javacode.juc.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

/**
 * UserService
 *
 * @author chendezhi
 * @date 2020/12/2 16:20
 * @since 1.0.0
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User registerUser() {

        User user = new User();
        user.setName("new-user-name"+System.currentTimeMillis());
        userRepository.save(user);

        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return user;
    }


}
