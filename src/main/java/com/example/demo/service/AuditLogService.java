package com.example.demo.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.demo.pojo.AuditLog;
import com.example.demo.repo.auditLog;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuditLogService {

    private final auditLog auditLog;
    public AuditLogService(auditLog auditLog) {
        this.auditLog = auditLog;
    }

    @Async("auditDbExec")
    public void savetoDb(AuditLog audit) {
        log.info("The Thread -{} is Executing " , Thread.currentThread().getName());
        auditLog.save(audit);

    }
}
