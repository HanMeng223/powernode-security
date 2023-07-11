package com.example.mapper;

import com.example.pojo.SysUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class SysUserMapperTest {

    @Autowired
    SysUserMapper sysUserMapper;

    @Test
    void getBUName(){
        SysUser obama = sysUserMapper.getByUserName("obama");
        System.out.println(obama);
        assertNotNull(obama);

    }
}