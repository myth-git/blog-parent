package com.sise.blog.admin.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class AuthService {

    public boolean auth(HttpServletRequest request, Authentication authentication){
        return true;
    }
}
