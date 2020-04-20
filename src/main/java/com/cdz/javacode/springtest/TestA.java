package com.cdz.javacode.springtest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: CDz
 * Create: 2020-04-10 15:43
 **/
@Component
public class TestA {

    private TestB testB;

    @Autowired
    public TestA(TestB testB) {
        this.testB = testB;
    }
}
