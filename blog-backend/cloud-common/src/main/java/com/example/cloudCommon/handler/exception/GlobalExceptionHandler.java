package com.example.cloudCommon.handler.exception;

import com.example.cloudCommon.enums.AppHttpCodeEnum;
import com.example.cloudCommon.exception.SystemException;
import com.example.cloudCommon.utils.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages ="org.example.controller")
public class GlobalExceptionHandler {
    @ExceptionHandler(SystemException.class)
    public ResponseResult systemExceptionHandler(SystemException se){
        log.error("出现异常：{}",se.getMsg());
        return ResponseResult.errorResult(se.getCode(),se.getMsg());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseResult accessDeniedExceptionHandler(AccessDeniedException ae){
        log.error("denied           {}", ae.getMessage());
        return ResponseResult.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseResult authenticationExceptionHandler(AuthenticationException authException){
        log.error("authentic error  {}",authException.getMessage());
        ResponseResult result;
        if(authException instanceof BadCredentialsException) {
            result = ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR,authException.getMessage());
        }
        else if(authException instanceof InsufficientAuthenticationException) {
            result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }
        else {
            result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR,"认证或授权失败");
        }
        return result;
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseResult exceptionHandler(Exception e){
        log.error("出现未知异常：   {}",e.getMessage());
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }
}
