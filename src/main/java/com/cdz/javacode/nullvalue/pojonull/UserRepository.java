package com.cdz.javacode.nullvalue.pojonull;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * UserRepository
 *
 * @author chendezhi
 * @date 2021/1/4 17:03
 * @since 1.0.0
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
}
