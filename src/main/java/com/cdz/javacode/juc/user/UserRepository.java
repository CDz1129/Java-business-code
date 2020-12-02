package com.cdz.javacode.juc.user;

import com.cdz.javacode.juc.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * UserRepository
 *
 * @author chendezhi
 * @date 2020/12/2 15:48
 * @since 1.0.0
 */
@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
}
