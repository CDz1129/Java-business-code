package com.cdz.javacode.nullvalue.avoidnullpointerexception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * AvoidNullPointerExceptionController
 *
 * @author chendezhi
 * @date 2020/12/24 9:57
 * @since 1.0.0
 */
@RestController
@RequestMapping("null-pointer")
@Slf4j
public class AvoidNullPointerExceptionController {


    @GetMapping("wrong")
    public int wrong(@RequestParam(value = "test", defaultValue = "1111") String test) {
        return wrongMethod(test.charAt(0) == '1' ? null : new FooService(),
                test.charAt(1) == '1' ? null : 1,
                test.charAt(2) == '1' ? null : "ok",
                test.charAt(3) == '1' ? null : "ok").size();
    }

    @GetMapping("right")
    public int right(@RequestParam(value = "test", defaultValue = "1111") String test) {
        return rightMethod(test.charAt(0) == '1' ? null : new FooService(),
                test.charAt(1) == '1' ? null : 1,
                test.charAt(2) == '1' ? null : "ok",
                test.charAt(3) == '1' ? null : "ok").size();
    }

    private List<String> wrongMethod(FooService fooService, Integer i, String s, String s1) {
        log.info("result: {} {} {} {}", i + 1,
                s.equals("OK"),
                s.equals(s1),
                new ConcurrentHashMap<String, String>().put(null, null));
        if (fooService.getBarService().bar().equals("OK")){
            log.info("OK");
        }
        return null;
    }

    private List<String> rightMethod(FooService fooService, Integer i, String s, String s1) {
        log.info("result: {} {} {} {}", Optional.ofNullable(i).orElse(0) + 1,
                "OK".equals(s),
                Objects.equals(s,s1),
                new HashMap<>().put(null, null));
        Optional.ofNullable(fooService)
                .map(FooService::getBarService)
                .filter(barService -> "OK".equals(barService.bar()))
                //这里没有打印 OK 因为barService为null
                //如何查看？Arthas -x 参数设置为 2 代表参数打印的深度为 2 层
                // watch com.cdz.javacode.nullvalue.avoidnullpointerexception.AvoidNullPointerExceptionController rightMethod params -x 2
                .ifPresent(result -> log.info("OK"));
        return new ArrayList<>();
    }


    class FooService {
        @Getter
        private BarService barService;
    }

    class BarService {
        public String bar() {
            return "OK";
        }
    }

}
