package com.example.controller;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class CurrentLoginUserController {

//    Authentication继承了Principal
    @GetMapping("/getLoginUser1")
    public Authentication getLoginUser(Authentication authentication){
        return authentication;
    }

    @GetMapping("/getLoginUser2")
    public Principal getLoginUser2(Principal principal){
        return principal;
    }


    @GetMapping("/getLoginUser3")
    public Principal getLoginUser3(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return authentication;
    }

}
