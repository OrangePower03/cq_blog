package com.example.cloudCommon.utils;

import com.example.cloudCommon.domain.eitity.LoginUser;
import com.example.cloudCommon.enums.AppHttpCodeEnum;
import com.example.cloudCommon.exception.SystemException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

public class SecurityUtils {
    /**
     * 获取用户
     **/
    public static LoginUser getLoginUser() {
        return (LoginUser) getAuthentication().getPrincipal();
    }

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(Objects.isNull(authentication)){
            throw new SystemException(AppHttpCodeEnum.AUTHENTICATE_ERROR);
        }
        return authentication;
    }

    /**
     * 是否是内部的代码执行的，redis定时任务刷入数据库要加的
     * @return 是否为内部代码
     */
    public static boolean isInternal() {
        return Objects.isNull(SecurityContextHolder.getContext().getAuthentication());
    }

    /**
     * 用户不能是anonymousUser
     * @return 是否为匿名用户，匿名表示未登录，不能获取到信息
     */
    public static boolean isAnonymous() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal instanceof String || principal.equals("anonymousUser");
    }

    public static  boolean isAdmin() {
        return getUserId().equals(1L);
    }

    public static Long getUserId() {
        return getLoginUser().getUser().getId();
    }

    public static String getUserRole() {
        return getLoginUser().getRole();
    }
}
