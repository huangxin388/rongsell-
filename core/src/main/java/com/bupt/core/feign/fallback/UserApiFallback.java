package com.bupt.core.feign.fallback;

import com.bupt.core.feign.UserApi;
import org.springframework.stereotype.Service;

/**
 * @Author huang xin
 * @Date 2020/9/13 17:51
 * @Version 1.0
 */
@Service
public class UserApiFallback implements UserApi {
    @Override
    public String testMethod() {
        return "execute failed, jiangjichuli";
    }
}
