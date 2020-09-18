package com.cdz.javacode.scan;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;

/**
 * MyClassScannerRegistrar
 *
 * @author chendezhi
 * @date 2020/9/18 17:43
 * @since 1.0.0
 */
@Slf4j
public class MyClassScannerRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware {
    private static final String BASE_PACKAGE_ATTRIBUTE_NAME = "basePackage";

    private ResourceLoader resourceLoader;

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

        AnnotationAttributes annotationAttributes = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(MyClassSean.class.getName()));
        String[] scanBasePackages = annotationAttributes.getStringArray(BASE_PACKAGE_ATTRIBUTE_NAME);


        MyClassScanner myClassScanner = new MyClassScanner(registry, MyClass.class);

        if (resourceLoader!=null) {
            myClassScanner.setResourceLoader(resourceLoader);
        }

        int scan = myClassScanner.scan(scanBasePackages);
        log.info("myClassScanner扫描：[{}]",scan);
    }
}
