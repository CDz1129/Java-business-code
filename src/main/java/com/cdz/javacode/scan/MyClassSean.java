package com.cdz.javacode.scan;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * MyClassSean
 *
 * @author chendezhi
 * @date 2020/9/18 17:37
 * @since 1.0.0
 *
 *  扫描所有 设置包下的类 并加入spring bean中
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Import(MyClassScannerRegistrar.class)
@Documented
public @interface MyClassSean {
    String[] basePackage();
}
