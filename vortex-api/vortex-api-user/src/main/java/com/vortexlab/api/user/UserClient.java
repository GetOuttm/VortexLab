package com.vortexlab.api.user;

import com.vortexlab.api.user.dto.LoginUserDto;
import com.vortexlab.api.user.dto.UserDTO;
import com.vortexlab.api.user.dto.UserLoginDTO;
import com.vortexlab.common.core.response.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 用户服务 Feign 远程调用客户端。
 * <p>
 * 供 vortex-order 等微服务通过服务名调用 vortex-user 的用户查询接口。
 * </p>
 */
@FeignClient(name = "vortex-user")
public interface UserClient {

    @GetMapping("/user/{id}}")
    UserDTO getUserById(@PathVariable("id") Long id);
}
