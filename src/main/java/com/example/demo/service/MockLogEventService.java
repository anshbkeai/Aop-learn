package com.example.demo.service;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MockLogEventService {

    public void logEvent(String tag, String message) {
        System.out.println(tag+ " " + message);
    }
}
