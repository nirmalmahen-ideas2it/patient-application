package com.ideas2it.training.patient.webclient;

import com.ideas2it.training.patient.config.UserFeignClientConfig;
import com.ideas2it.training.patient.entity.LoginRequest;
import com.ideas2it.training.patient.entity.LoginResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-validation-client", url = "${user-validation-client.url}", configuration = UserFeignClientConfig.class)
public interface UserValidationClient {

    @PostMapping("/validate")
    LoginResponse validateUser(@RequestBody LoginRequest request);
}
