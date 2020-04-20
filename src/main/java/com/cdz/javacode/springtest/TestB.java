package com.cdz.javacode.springtest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: CDz
 * Create: 2020-04-10 15:43
 **/
@Component
public class TestB {

    private TestA testA;

    @Autowired
    public TestB(@Lazy TestA testA) {
        this.testA = testA;
    }
}
