package com.redis.demo.aspect;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.redis.demo.exception.LockException;
import com.redis.demo.service.DistributedLockService;

@Aspect
@Component
public class DistributedLockAspect {

    @Autowired
    private DistributedLockService lockService;

    @Pointcut("execution(* com.redis.demo.controller.*.*(..))")
    public void controllerMethods() {
    }

    @Before("controllerMethods()")
    public void beforeControllerMethod(JoinPoint joinPoint) {
        Long entityId = extractEntityId(joinPoint);
        if (entityId != null) {
            if (!lockService.acquireLock(entityId)) {
                throw new LockException("Unable to acquire lock for entity with ID: Aspect " + entityId);
            }
        }
    }

    @After("controllerMethods()")
    public void afterControllerMethod(JoinPoint joinPoint) {
        Long entityId = extractEntityId(joinPoint);
        if (entityId != null) {
            lockService.releaseLock(entityId);
        }
    }

    private Long extractEntityId(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof Integer) {
				return (Long) arg;
            }
        }
        return null;
    }
}
