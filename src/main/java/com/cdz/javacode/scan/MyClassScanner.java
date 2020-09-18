package com.cdz.javacode.scan;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.lang.annotation.Annotation;

/**
 * MyClassScanner
 *
 * @author chendezhi
 * @date 2020/9/18 17:50
 * @since 1.0.0
 */
public class MyClassScanner extends ClassPathBeanDefinitionScanner {
    public MyClassScanner(BeanDefinitionRegistry registry, Class<? extends Annotation> annoType) {
        super(registry);
        super.addIncludeFilter(new AnnotationTypeFilter(annoType));
    }
    @Override
    public int scan(String... basePackages) {
        return super.scan(basePackages);
    }
}
