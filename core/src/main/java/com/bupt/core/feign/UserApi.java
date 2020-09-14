package com.bupt.core.feign;

import com.bupt.core.feign.fallback.UserApiFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author huang xin
 * @Date 2020/9/12 19:50
 * @Version 1.0
 */
@FeignClient(name = "user-module", fallback = UserApiFallback.class)
public interface UserApi {

    @GetMapping("/test/hello")
    String testMethod();
}
