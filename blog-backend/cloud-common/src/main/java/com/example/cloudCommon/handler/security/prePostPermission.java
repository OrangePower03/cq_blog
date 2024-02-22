package com.example.cloudCommon.handler.security;

import com.example.cloudCommon.enums.SystemConstants;
import com.example.cloudCommon.utils.SecurityUtils;
import org.springframework.stereotype.Component;

@Component("ppp")
public class prePostPermission {
    public boolean hasRole(String role) {
        String userRole = SecurityUtils.getUserRole();
        if(userRole.equals(SystemConstants.ADMIN)) {
            return true;
        }
        else {
            return userRole.equals(role);
        }
    }

}
