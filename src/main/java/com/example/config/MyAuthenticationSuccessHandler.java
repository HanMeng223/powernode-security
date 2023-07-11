package com.example.config;

import com.alibaba.fastjson.JSON;
import com.example.util.JwtUtils;
import com.example.vo.HttpResult;
import com.example.vo.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.management.timer.Timer;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");

        SecurityUser principal =(SecurityUser) authentication.getPrincipal();

        String strUserInfo= JSON.toJSONString(principal.getSysUser());

        List<SimpleGrantedAuthority> authorities =(List<SimpleGrantedAuthority>) principal.getAuthorities();
        List<String> authList=authorities.stream().map(SimpleGrantedAuthority::getAuthority).collect(Collectors.toList());

        String jwtToken = jwtUtils.createJwt(strUserInfo, authList);
        stringRedisTemplate.opsForValue().set("logintoken:"+jwtToken,JSON.toJSONString(authentication),2, TimeUnit.HOURS);


        HttpResult httpResult=HttpResult.builder()
                .code(1)
                .msg("jwt生成成功")
                .data(jwtToken)
                .build();
        String strResponse=JSON.toJSONString(httpResult);
        PrintWriter printWriter = response.getWriter();
        printWriter.println(strResponse);
        printWriter.flush();
    }
}
