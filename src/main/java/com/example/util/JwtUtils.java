package com.example.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class JwtUtils {

    @Value("${my.secretKey}")
    private String secret;

    public String createJwt(String userInfo, List<String> authList){
        Date date = new Date();
        Date date1=new Date(date.getTime()+1000*60*60*2);//过期时间两小时
//        Date date1=new Date(date.getTime()+1000*30);//过期时间两小时

        Map<String,Object> headerClaims=new HashMap<>();
        headerClaims.put("alt","HS256");
        headerClaims.put("typ","JWT");
        return JWT.create().withHeader(headerClaims)
                .withIssuer("thomas")//签发人
                .withIssuedAt(date)//签发时间
                .withExpiresAt(date1)//过期时间
                .withClaim("user_info",userInfo)//自定义声明
                .withClaim("userAuth",authList)
                .sign(Algorithm.HMAC256(secret));//
    }

        public boolean verifyToken(String jwtToken){
            try {
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret)).build();
                DecodedJWT decodedJWT = jwtVerifier.verify(jwtToken);
                System.out.println("token验证正确");
//                Integer userId = decodedJWT.getClaim("userId").asInt();
//                String username = decodedJWT.getClaim("userNmae").asString();
//                List<String> userAuth = decodedJWT.getClaim("userAuth").asList(String.class);
                return  true;
            } catch (Exception e) {
                System.out.println("token验证不正确!!");
                return false;
            }
        }


//        public Integer getUserIdFromToken(String jwtToken){
//            try {
//                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret)).build();
//                DecodedJWT decodedJWT = jwtVerifier.verify(jwtToken);
//                return decodedJWT.getClaim("userId").asInt();
//            } catch (Exception e) {
//                return -1;
//                }
//            }



        public String getUserNameFromToken(String jwtToken){
            try {
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret)).build();
                DecodedJWT decodedJWT = jwtVerifier.verify(jwtToken);
                return decodedJWT.getClaim("userNmae").asString();
            } catch (Exception e) {
                return "";
            }
        }

        public String getUserInfoFromToken(String jwtToken){
            try {
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret)).build();
                DecodedJWT decodedJWT = jwtVerifier.verify(jwtToken);
                return decodedJWT.getClaim("user_info").asString();
            } catch (Exception e) {
                return "";
            }
        }




        public List<String> getUseruserAuthFromToken(String jwtToken){
            try {
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret)).build();
                DecodedJWT decodedJWT = jwtVerifier.verify(jwtToken);
                return decodedJWT.getClaim("userAuth").asList(String.class);
            } catch (Exception e) {
                return null;
            }
        }

}

