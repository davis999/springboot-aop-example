package io.aop.aspect;

import com.google.common.collect.Lists;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.PrimitiveIterator;
import java.util.stream.Stream;

/**
 * Created by Davis on 17/1/10.
 */
@Aspect
@Component
public class MyAspect {
  @Before("@annotation(CurrencyMonitor)")
  public void logAction(JoinPoint joinPoint)
      throws Throwable {
    String[] fields = getCurrencyFields(joinPoint);
    for (int i = 0; i < fields.length; i++) {
      System.out.println(fields[i]);
    }
    List<Object> objects = getFields(joinPoint, fields);
  }

  private String[] getCurrencyFields(JoinPoint joinPoint) {
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Method method = signature.getMethod();
    return method.getAnnotation(CurrencyMonitor.class).fields();
  }

  private List<Object> getFields(JoinPoint joinPoint, String[] names) {
    List<Object> objects = Lists.newArrayList();

    Stream.of(names).forEach(
        name -> {
          objects.add(getFieldByName(joinPoint, name));
        }
    );

    return objects;
  }

  private Object getFieldByName(JoinPoint joinPoint, String name) {
    List namePath = getNamePath(name);

    Object object = joinPoint.getArgs()[0];

    Object realObj = getFieldByNamePath(object, namePath);

    return realObj;
  }

  private Object getFieldByNamePath(Object object, List<String> namePath) {
    String name = namePath.get(0);


    return null;
  }

  private List<String> getNamePath(String name) {
    List<String> result = Lists.newArrayList(name);
    if (name.contains(".")) {
      result = Lists.newArrayList(name.split("."));
    }
    return result;
  }
}
