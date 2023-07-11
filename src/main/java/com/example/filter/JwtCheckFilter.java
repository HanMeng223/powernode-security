package com.example.filter;


import com.alibaba.fastjson.JSON;
import com.example.pojo.SysUser;
import com.example.util.JwtUtils;
import com.example.vo.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtCheckFilter extends OncePerRequestFilter {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        System.out.println("jcf:url:"+requestURI);
        if (requestURI.equals("/login")){
            System.out.println("登录放行");
//            doFilter(request,response,filterChain);
            filterChain.doFilter(request,response);
            return;
        }
        String authorization = request.getHeader("Authorization");
        System.out.println("jcf:authorization:"+authorization);
        if (StringUtils.isEmpty(authorization)){
            HttpResult httpResult=HttpResult.builder()
                    .code(0)
                    .msg("Authorization为空")
                    .build();

            printToken(request,response,httpResult);
            return;

        }

        String jwtToken = authorization.replace("bearer ","");
        System.out.println("jcf:jwttoken:"+jwtToken);
        if (StringUtils.isEmpty(jwtToken)){
            HttpResult httpResult=HttpResult.builder()
                    .code(0)
                    .msg("jwt为空")
                    .build();
            printToken(request,response,httpResult);
            return;

        }

        boolean verifyToken = jwtUtils.verifyToken(jwtToken);
        if (!verifyToken){
            HttpResult httpResult=HttpResult.builder()
                    .code(0)
                    .msg("jwt非法")
                    .build();
            printToken(request,response,httpResult);
            return;
        }

        //判断redis中是否存在jwt
        String redisToken = stringRedisTemplate.opsForValue().get("logintoken:" + jwtToken);
        System.out.println("jct:redistoken:"+redisToken);
        if (StringUtils.isEmpty(redisToken)){
            HttpResult httpResult=HttpResult.builder()
                    .code(0)
                    .msg("您已经退出！！")
                    .build();
            printToken(request,response,httpResult);
            return;
        }


        String userInfoFromToken = jwtUtils.getUserInfoFromToken(jwtToken);
        List<String> authFromToken = jwtUtils.getUseruserAuthFromToken(jwtToken);
        //反序列化
        System.out.println("jcf:Json_sysuser:"+JSON.parseObject(userInfoFromToken,SysUser.class));
        SysUser sysUser = JSON.parseObject(userInfoFromToken,SysUser.class);
        System.out.println("jcf:sysUser:"+sysUser);
        List<SimpleGrantedAuthority> authorities=authFromToken.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        System.out.println("jcf:autho:"+authorities);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(sysUser,null,authorities);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        doFilter(request,response,filterChain);

    }

    private void printToken(HttpServletRequest request, HttpServletResponse response, HttpResult httpResult) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        String strResponse= JSON.toJSONString(httpResult);
        PrintWriter printWriter = response.getWriter();
        printWriter.println(strResponse);
        printWriter.flush();
        return;
    }
}
