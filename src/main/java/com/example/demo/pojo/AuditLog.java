package com.example.demo.pojo;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class AuditLog {

    @Id
    private String id;
    private String action;
    private String resource;
    private String resourceId;
    private String Ip;

    private LocalDateTime localDateTime;
    private String status;
}
