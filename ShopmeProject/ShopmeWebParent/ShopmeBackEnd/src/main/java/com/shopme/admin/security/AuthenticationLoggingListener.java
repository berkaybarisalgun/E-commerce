package com.shopme.admin.security;

import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationLoggingListener {

    @EventListener
    public void onAuthenticationSuccess(AuthenticationSuccessEvent event) {
        Authentication authentication = event.getAuthentication();
        String username = authentication.getName();
        authentication.getAuthorities().forEach(authority -> {
            System.out.println("User: " + username + " has authority: " + authority.getAuthority());
        });
    }
}
