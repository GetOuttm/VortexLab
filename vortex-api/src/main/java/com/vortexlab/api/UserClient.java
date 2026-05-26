package com.vortexlab.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "vortex-user")
public interface UserClient {

    @GetMapping("/user/{id}")
    String getUser(@PathVariable("id") Long id);
}