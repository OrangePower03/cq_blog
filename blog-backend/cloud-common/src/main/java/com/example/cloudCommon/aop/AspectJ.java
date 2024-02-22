package com.example.cloudCommon.aop;

import com.example.cloudCommon.annotation.Log;
import com.example.cloudCommon.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
@Aspect
@Component
public class AspectJ {
    @Pointcut("@annotation(com.example.cloudCommon.annotation.Log)")
    public void pointcut(){
    }

    @Around("pointcut()")
    public Object aopLog(ProceedingJoinPoint pjp) throws Throwable {

        log.info("start============================");
        before(pjp);
        Object res=pjp.proceed();
        after();
        log.info("end============================");
        return res;
    }

    private void before(ProceedingJoinPoint pjp) {
        HttpServletRequest request = WebUtils.getRequest();
        String url = request.getRequestURL().toString();
        log.info("url:             {}",url);
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod();
        Log annotation = method.getAnnotation(Log.class);
        log.info("BusinessName     {}",annotation.value());
        log.info("Request Method   {}",request.getMethod());
        log.info("Class Method     {}",method.getName());
        log.info("IP               {}",request.getRemoteAddr());
        Object[] args = pjp.getArgs();
        log.info("Request Args     {}", Arrays.stream(args).collect(Collectors.toList()));
    }

    private void after() {
        HttpServletResponse response = WebUtils.getResponse();
        System.out.println("1111");
        log.info("Response status  {}",response.getStatus());
    }
}
