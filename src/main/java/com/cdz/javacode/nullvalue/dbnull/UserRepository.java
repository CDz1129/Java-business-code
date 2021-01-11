package com.cdz.javacode.nullvalue.dbnull;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * UserRepository
 *
 * @author chendezhi
 * @date 2021/1/5 16:06
 * @since 1.0.0
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    @Query(nativeQuery = true,value = "select sum(scroll) from user ")
    Long wrong1();
    @Query(nativeQuery = true,value = "select count(scroll) from user ")
    Long wrong2();
    @Query(nativeQuery = true,value = "select * from user where scroll = null ")
    Long wrong3();

    @Query(nativeQuery = true,value = "select nullif(sum(scroll),0) from user ")
    Long right1();
    //  todo JPA中 ，同源 不同库 可以使用 库名.表名进行访问，但是 不能使用JpaRepository中的方法，@Table(name = "user",schema = "demo")
    // 中的schema没有生效
    @Query(nativeQuery = true,value = "select count(*) from demo.user ")
    Long right2();
    @Query(nativeQuery = true,value = "select * from user where scroll is null ")
    Long right3();

}
