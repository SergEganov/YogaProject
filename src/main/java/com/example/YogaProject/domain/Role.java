package com.example.YogaProject.domain;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER, ADMIN, MENTOR;

    @Override
    public String getAuthority() {
        return name();
    }
}
