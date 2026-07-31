package com.vortexlab.user.ontroller;

import com.vortexlab.api.user.UserClient;
import com.vortexlab.api.user.dto.UserDTO;
import com.vortexlab.user.entity.User;
import com.vortexlab.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController implements UserClient {

    private final UserService userService;

    @Override
    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Long id) {
        User user = userService.getById(id);
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUserName(user.getUserName());
        dto.setNickName(user.getNickName());
        return dto;
    }
}
