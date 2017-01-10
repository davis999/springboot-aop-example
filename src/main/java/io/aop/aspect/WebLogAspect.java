package io.aop.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Davis on 17/1/10.
 */
//@Aspect
//@Order(5)
//@Component
public class WebLogAspect {
  ThreadLocal<Long> startTime = new ThreadLocal<>();

  @Pointcut("execution(public * io.aop.controller.*.*(..))")
  public void webLog(){}

  @Before("webLog()")
  public void doBefore(JoinPoint joinPoint) throws Throwable {
    startTime.set(System.currentTimeMillis());

    // 接收到请求，记录请求内容
    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    HttpServletRequest request = attributes.getRequest();

    if (joinPoint.getArgs()[0].equals("davis")) {
      System.out.println("davis can not use the system");
      throw new RuntimeException();
    }

    // 记录下请求内容
    System.out.println("URL : " + request.getRequestURL().toString());
    System.out.println("HTTP_METHOD : " + request.getMethod());
    System.out.println("IP : " + request.getRemoteAddr());
    System.out.println("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
    System.out.println("ARGS : " + Arrays.toString(joinPoint.getArgs()));
  }

  @AfterReturning(returning = "ret", pointcut = "webLog()")
  public void doAfterReturning(Object ret) throws Throwable {
    // 处理完请求，返回内容
    System.out.println("RESPONSE : " + ret);
    System.out.println("SPEND TIME : " + (System.currentTimeMillis() - startTime.get()));
  }
}