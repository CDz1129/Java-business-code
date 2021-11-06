package com.cdz.javacode.nullvalue.pojonull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * PojoNullController
 *
 * @author chendezhi
 * @date 2021/1/4 17:02
 * @since 1.0.0
 */
@RestController
@RequestMapping("pojo-null")
@Slf4j
public class PojoNullController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public void test() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new Jdk8Module());

        // Java8 jackjson做了option的优化，
        // 特别是在update的时候，传入的参数null到底含义是什么？
        // 是将其字段更新为 null呢？还是不操作呢？
        // 其实这个东西，在我的项目里面一直都没有出现过，为什么呢？
        //  因为我写的项目里面，首先update都会特殊谨慎处理，第二，数据库还少有设置为null的情况，字段设计的时候都是不许用null的。

        //但这个 使用 option的方式还是挺有意思的，从来没有用过

        UserDto result = objectMapper.readValue("{\"id\":\"1\", \"age\":30, \"name\":null}", UserDto.class);
        log.info("field name with null value dto:{} name:{}", result, result.getName().orElse("N/A"));
        // field name with null value dto:UserDto(id=1, name=Optional.empty, age=Optional[30]) name:N/A
        log.info("missing field name dto:{}", objectMapper.readValue("{\"id\":\"1\", \"age\":30}", UserDto.class));
        // missing field name dto:UserDto(id=1, name=null, age=Optional[30])

    }
    @PostMapping("wrong")
    public User wrong(@RequestBody User user){
        user.setNickname(String.format("guest%s",user.getName()));
        return userRepository.save(user);
    }

    @Autowired
    private UserEntityRepository userEntityRepository;
    @PostMapping("right")
    public UserEntity right(@RequestBody UserDto userDto){
        if (userDto==null||userDto.getId()==null) {
            throw new IllegalArgumentException("用户ID不能为空");
        }
        UserEntity userEntity = userEntityRepository.findById(userDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("用户不存在"));

        if (userDto.getName()!=null){
            userEntity.setName(userDto.getName().orElse(""));
        }
        userEntity.setNickname("guest"+userEntity.getName());
        if (userDto.getAge()!=null){
            userEntity.setAge(userDto.getAge().orElseThrow(()->new IllegalArgumentException("年龄不能为空")));
        }
        return userEntityRepository.save(userEntity);
    }

}
