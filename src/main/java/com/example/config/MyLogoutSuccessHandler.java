package com.example.config;

import com.alibaba.fastjson.JSON;
import com.example.util.JwtUtils;
import com.example.vo.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    JwtUtils jwtUtils;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String authorization = request.getHeader("Authorization");
        if (StringUtils.isEmpty(authorization)){
            HttpResult httpResult = HttpResult.builder()
                    .code(0)
                    .msg("jwt不存在")
                    .build();

            printToken(request,response,httpResult);
            return;
        }


        String jwtToken = authorization.replace("bearer ", "");
        boolean reault = jwtUtils.verifyToken(jwtToken);
        if (!reault){
            HttpResult httpResult = HttpResult.builder()
                    .code(0)
                    .msg("jwt非法")
                    .build();

            printToken(request,response,httpResult);
            return;
        }


        HttpResult httpResult=HttpResult.builder()
                .code(1)
                .msg("退出成功")
                .build();
        stringRedisTemplate.delete("logintoken:"+jwtToken);
        printToken(request,response,httpResult);


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
