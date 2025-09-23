package com.example.demo.utils;

import java.time.LocalDateTime;
import java.util.Enumeration;
import java.util.UUID;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.condition.RequestConditionHolder;

import com.example.demo.pojo.AuditLog;
import com.example.demo.service.AuditLogService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class auditlogAspect {

    private final AuditLogService auditLogService;
    public auditlogAspect(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    //Aspect Around Advice that work in the @Audit and fetch about the. Diffreent Parm
    @Around("@annotation(audit)")
    public Object logAuditAspect(ProceedingJoinPoint joinPoint , Audit audit) throws Throwable {

        /*
         * THis Below Code abput getting the clEINT IP IS more Understadnding require so revisite it 
         */
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes != null ? servletRequestAttributes.getRequest() : null;

       AuditLog auditLog = new AuditLog();
       auditLog.setId(UUID.randomUUID().toString()); 
       auditLog.setAction(audit.event());
       String ip = request != null ? getClientIp(request) : "UNKNOWN";
        auditLog.setIp(ip);
        auditLog.setLocalDateTime(LocalDateTime.now());

       try {
            Object arr[] = joinPoint.getArgs();
             Object result = joinPoint.proceed();
              
              auditLog.setResource(joinPoint.getSignature().getName());
                auditLog.setStatus("SUCESS");

              //save in tje Db 
              

            return result;
       } catch (Exception e) {
        // TODO: handle exception
         auditLog.setStatus("SUCESS");
        throw e;
       }
       finally {
        //sabe on db
            auditLogService.savetoDb(auditLog);
            
       }
    }

     private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        // Enumeration<String> headerNames = request.getHeaderNames();
        // while (headerNames.hasMoreElements()) {
        //     String headerName = headerNames.nextElement();
        //     String headerValue = request.getHeader(headerName);
        //     log.info("Header: {} = {}", headerName, headerValue);
        // }
        if (ip == null || ip.isEmpty()) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
