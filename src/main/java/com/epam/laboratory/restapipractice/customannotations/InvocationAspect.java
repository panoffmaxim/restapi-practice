package com.epam.laboratory.restapipractice.customannotations;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class InvocationAspect {
    private final static Logger LOGGER = LoggerFactory.getLogger(InvocationAspect.class.getName());

    @Before("@annotation(com.epam.laboratory.restapipractice.customannotations.LogInvocation)")
    public void logBeforeMethod(JoinPoint jp) {
        String methodName = jp.getSignature().getName();
        LOGGER.info("Calling " + methodName);
    }

    @After("@annotation(com.epam.laboratory.restapipractice.customannotations.LogInvocation)")
    public void logAfterMethod(JoinPoint jp) {
        String methodName = jp.getSignature().getName();
        LOGGER.info(methodName + " completed");
    }
}
