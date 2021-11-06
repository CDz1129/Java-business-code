package com.cdz.javacode.i18n;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;

@Component
@Aspect
@Slf4j
public class I18nAop {
    @Autowired
    private MessageSource messageSource;
    /**
     * 切入点
     */
    @Pointcut("execution(public * com.cdz.javacode.i18n.*Controller.*(..))")
    public void i18nProperty() {
    }

    /**
     * 前置操作
     *
     * @param point 切入点
     */
    @Before("i18nProperty()")
    public void beforeLog(JoinPoint point) {
    }

    /**
     * 环绕操作
     *
     * @param point 切入点
     * @return 原方法返回值
     * @throws Throwable 异常信息
     */
    @Around("i18nProperty()")
    public Object aroundLog(ProceedingJoinPoint point) throws Throwable {
        Object result = point.proceed();
        Class<?> aClass = result.getClass();
        for (Field declaredField : aClass.getDeclaredFields()) {
            //找到有 @I18nProperty 的字段
            I18nProperty annotation = declaredField.getAnnotation(I18nProperty.class);
            if (annotation != null) {
                //私有可编辑
                declaredField.setAccessible(true);
                //获取此字段内容
                String o = (String) declaredField.get(result);
                if (!StringUtils.isEmpty(o)) {
                    String message = messageSource.getMessage(o, null, LocaleContextHolder.getLocale());
                    //set回去
                    declaredField.set(result,message);
                }
            }
        }

        log.info("【返回值】：{}", JSONUtil.toJsonStr(result));
        return result;
    }

    /**
     * 后置操作
     */
    @AfterReturning("i18nProperty()")
    public void afterReturning() {
    }
}
