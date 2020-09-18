package com.cdz.javacode.scan;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * MyClazz1
 *
 * @author chendezhi
 * @date 2020/9/18 17:56
 * @since 1.0.0
 */
@MyClass
@MyClassSean(basePackage = {"com.cdz.javacode.scan"})
public class MyClazz2 {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MyClazz2.class);
        MyClazz1 myClazz1 = (MyClazz1) applicationContext.getBean("myClazz1");
        System.out.println(myClazz1.test());
    }
}
