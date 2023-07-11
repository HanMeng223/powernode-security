//package com.example.util;
//
//import java.util.Arrays;
//import java.util.List;
//
//public class TestJwtUtils {
//    public static void main(String[] args) {
//        TestJwtUtils testJwtUtils=new TestJwtUtils();
//        String token = testJwtUtils.createToken();
//        testJwtUtils.verifyToken(token);
//
//        JwtUtils jwtUtils=new JwtUtils();
//        boolean verifyToken = jwtUtils.verifyToken(token);
//        if (verifyToken){
//            List<String> authFromToken = jwtUtils.getUseruserAuthFromToken(token);
//            String userNameFromToken = jwtUtils.getUserNameFromToken(token);
//            Integer userIdFromToken = jwtUtils.getUserIdFromToken(token);
//            System.out.println(authFromToken);
//            System.out.println(userNameFromToken);
//            System.out.println(userIdFromToken);
//        }
//
//
//    }
//    public String createToken(){
//        JwtUtils jwtUtils=new JwtUtils();
//
//        List<String> authList= Arrays.asList("student:query","student:add","student:update");
//        String token = jwtUtils.createJwt(19, "obama", authList);
//        System.out.println(token);
//        return token;
//    }
//
//
//
//    public void verifyToken(String token){
//        String jwt="eyJhbHQiOiJIUzI1NiIsInR5cCI6IkpXVCIsImFsZyI6IkhTMjU2In0.eyJ1c2VyTm1hZSI6Im9iYW1hIiwidXNlckF1dGgiOlsic3R1ZGVudDpxdWVyeSIsInN0dWRlbnQ6YWRkIiwic3R1ZGVudDp1cGRhdGUiXSwiaXNzIjoidGhvbWFzIiwiZXhwIjoxNjg4Nzg0NzkwLCJpYXQiOjE2ODg3ODQ3NjAsInVzZXJJZCI6MTl9.8Kjc9bKjucsZWvqzTqTxJBtbddcBQSrhMQnv4ODNAAI";
//        JwtUtils jwtUtils=new JwtUtils();
//        boolean verifyToken = jwtUtils.verifyToken(token);
//        System.out.println(verifyToken);
//    }
//
//}
