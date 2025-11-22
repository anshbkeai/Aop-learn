package com.example.demo.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SpelAuditedEvent {

    String tagExpression();
    String maskCondition();
    int maskedParameterIndex() default -1;
}
