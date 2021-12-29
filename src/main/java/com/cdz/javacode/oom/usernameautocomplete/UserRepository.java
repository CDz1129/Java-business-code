package com.cdz.javacode.oom.usernameautocomplete;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

/**
 * @author david.chen
 * @date 2021/12/13 18:57
 */
@Repository
public class UserRepository {

    private List<UserEntity> usernameList = IntStream.rangeClosed(1,1_000)
                    .mapToObj(i->new UserEntity((long) i,randomName()))
                    .collect(Collectors.toList());

    private String randomName() {
        return RandomUtil.randomString(6);
    }

    public List<UserEntity> findAll(){
        return usernameList;
    }

}
