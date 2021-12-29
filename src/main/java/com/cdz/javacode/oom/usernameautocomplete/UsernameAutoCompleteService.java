package com.cdz.javacode.oom.usernameautocomplete;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author david.chen
 * @date 2021/12/13 18:55
 */
@Service
@Slf4j
public class UsernameAutoCompleteService {

    private ConcurrentHashMap<String, List<UserDTO>> autoCompleteIndex = new ConcurrentHashMap<>();

    @Autowired
    private UserRepository userRepository;

    //错误的初始化方法，会导致OOM的出现
    @PostConstruct
    public void wrong(){
        List<UserEntity> list = userRepository.findAll();
        list.forEach(userEntity -> {
            int length = userEntity.getName().length();
            for (int i = 0; i < length; i++) {
                String key = userEntity.getName().substring(0, i + 1);
                autoCompleteIndex.computeIfAbsent(key,s->new ArrayList<>())
                        //这里每次都需要NEW一个对象出来，是很有问题的——
                        // 解决办法——使用set来存储DTO对象
                        .add(new UserDTO(userEntity.getName()));
            }
        });
        log.info("autocompleteIndex size :{} count:{}",autoCompleteIndex.size(),
                autoCompleteIndex.entrySet().stream().map(item->item.getValue().size()).reduce(Integer::sum));
    }

    @PostConstruct
    public void right(){
        Set<UserDTO> cache = userRepository.findAll().stream().map(e -> new UserDTO(e.getName())).collect(Collectors.toSet());
        cache.stream().forEach(userDTO -> {
            int length = userDTO.getUsername().length();
            for (int i = 0; i < length; i++) {
                String key = userDTO.getUsername().substring(0, i + 1);
                autoCompleteIndex.computeIfAbsent(key,s->new ArrayList<>())
                        // 解决办法——使用set来存储DTO对象
                        .add(userDTO);
            }
        });
    }

}
