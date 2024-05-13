package org.lwl.demo.common.config;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.lwl.demo.common.OaConstants;
import org.lwl.demo.common.currentuser.CurrentUser;
import org.lwl.demo.common.token.TokenUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

@Configuration
public class CurrentUserConfig {

    @Bean
    public CurrentUser currentUser(HttpServletRequest request,TokenUtils tokenUtils){

        return new CurrentUser(){
            @Override
            public String getUserId() {
                CurrentUser target = (CurrentUser)request.getAttribute(OaConstants.currentUserName);
                return target == null ? null : target.getUserId();
            }

            @Override
            public String getUserName() {
                CurrentUser target = (CurrentUser)request.getAttribute(OaConstants.currentUserName);
                return target == null ? null : target.getUserName();
            }
        };



    }
}