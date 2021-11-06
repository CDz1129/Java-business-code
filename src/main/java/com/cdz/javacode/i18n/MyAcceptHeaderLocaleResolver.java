package com.cdz.javacode.i18n;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@Component
public class MyAcceptHeaderLocaleResolver extends AcceptHeaderLocaleResolver {

    @Value("${language.header}")
    private String languageHeader;

    @Bean
    public LocaleResolver localeResolver(){
        return new MyAcceptHeaderLocaleResolver();
    }

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String l = request.getHeader(languageHeader);
        if (StringUtils.isEmpty(l)){
            return super.resolveLocale(request);
        }
        String[] split = l.split("-");
        return new Locale(split[0],split[1]);
    }

}
