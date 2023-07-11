package com.example.service.impl;

import com.example.pojo.SysUser;
import com.example.service.SysUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class SysUserServiceImplTest {

    @Autowired
    SysUserService sysUserService;

    @Test
    void getByUserName(){
        SysUser ddd = sysUserService.getByUserName("obama");
        System.out.println(ddd);
        assertNotNull(ddd);
    }

}