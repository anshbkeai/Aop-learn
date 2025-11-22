package com.example.demo.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.pojo.User;
import com.example.demo.pojo.UserDto;
import com.example.demo.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping("/auth")
    public ResponseEntity<User> auth(@RequestBody UserDto userDto) {
        //TODO: process POST request
        
        return ResponseEntity.ok().body(service.authenticateUser(userDto));
    }
    
}
