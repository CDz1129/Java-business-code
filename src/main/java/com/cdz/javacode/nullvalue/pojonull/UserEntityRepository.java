package com.cdz.javacode.nullvalue.pojonull;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * UserEntityRepository
 *
 * @author chendezhi
 * @date 2021/1/5 14:14
 * @since 1.0.0
 */
@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity,Long> {
}
