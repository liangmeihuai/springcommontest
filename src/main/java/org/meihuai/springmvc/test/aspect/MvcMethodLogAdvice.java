package org.meihuai.springmvc.test.aspect;

import com.google.gson.Gson;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
@Component
@Aspect
public class MvcMethodLogAdvice {
  private Gson gson = new Gson();
  @Around("execution(* org.meihuai.springmvc.test.controller.*.*(..))")
  public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
    Object args[] = joinPoint.getArgs();
    System.out.println("aroundAdvice args is = " + gson.toJson(args));
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Method method = signature.getMethod();
    Object result = joinPoint.proceed();
    System.out.println("aroundAdvice result is = " + gson.toJson(result));
    return result;
  }
}