package com.example.demo.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.demo.pojo.User;
import com.example.demo.pojo.UserDto;
import com.example.demo.utils.SpelAuditedEvent;

import jakarta.annotation.PostConstruct;

@Service
public class UserService {

    private Map<String,User> map ;
    @PostConstruct
    public void init() {
        map = new HashMap<>();
        String user = "USER-";
        String email = "EMAIL-";
        String pass = "PASS-";
        for(int i = 0 ;i < 10;i++) {
            User u = new User(""+i, user+i, pass+i, email+i);
            map.put(user+i , u);
        }
    }

    @SpelAuditedEvent(
        tagExpression = "'USER_LOGIN:' + #p0.username",
        maskCondition = "#p0.ssoLogin == false", // Mask password if NOT SSO
        maskedParameterIndex = 1 // Index of 'password'
    )
    public User authenticateUser(UserDto userDto) {
        return map.get(userDto.getUsername());
    }
}
