package com.example.demo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.RequiredArgsConstructor;

@Data

@AllArgsConstructor
@RequiredArgsConstructor
public class User {

    private String id;
    private String userName;
    private String password;
    private String email;

}
