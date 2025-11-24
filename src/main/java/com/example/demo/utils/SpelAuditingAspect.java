package com.example.demo.utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.MDC;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import com.example.demo.pojo.User;
import com.example.demo.pojo.UserDto;
import com.example.demo.service.MockLogEventService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class SpelAuditingAspect {

    private final ExpressionParser parser = new SpelExpressionParser();
    private final MockLogEventService mockLogEventService;
    @Around("@annotation(auditedEvent)")
    public Object userAround(ProceedingJoinPoint proceedingJoinPoint , SpelAuditedEvent auditedEvent) throws Throwable {
        Object ret = null; Throwable err = null;
        try {
            log.info("Enert in the Aspect");
            ret = proceedingJoinPoint.proceed();
            return ret;
        } catch (Throwable e) {
            
            err = e;
            return e;

        }
        finally{
            var ctx = new StandardEvaluationContext();
            var args = proceedingJoinPoint.getArgs();
            var names = proceedingJoinPoint.getSignature().toShortString();

            log.info("From the Aspect -> " + names + " " + ctx.toString());

            for(int i = 0 ;i < args.length; i++) ctx.setVariable("p"+i, args[i]);

            log.info(ctx.getRootObject().toString());
            log.info(MDC.get("correlationId"));
            //log.info("p2 value in context = {} (type: {})", ctx.lookupVariable("p2"), ctx.lookupVariable("p2").getClass());


            String tagExpression = auditedEvent.tagExpression().isBlank() ? null :
                                                 String.valueOf(parser.parseExpression(auditedEvent.tagExpression()).getValue(ctx));
            String maskedCondition = auditedEvent.maskCondition().isBlank() ? null :
                                                 String.valueOf(parser.parseExpression(auditedEvent.maskCondition()).getValue(ctx));
           
            log.info("Evaluated tagExpression: {}", tagExpression);
            log.info("Evaluated maskCondition: {}", maskedCondition);

            if(maskedCondition.equals("true")) {
                mockLogEventService.logEvent(tagExpression, "The password is logged as ***MASKED***.");
            }
            else {
                if(args[0] instanceof UserDto) {
                    UserDto u = (UserDto)args[0];
                    mockLogEventService.logEvent(tagExpression, "The password is logged " + u.getPassword());
                }
            }





        }
    }
}
