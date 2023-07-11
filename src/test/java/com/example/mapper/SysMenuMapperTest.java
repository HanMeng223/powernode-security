package com.example.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SysMenuMapperTest {


    @Autowired
    SysMenuMapper menuMapper;

    @Test
    void test(){
        List<String> strings = menuMapper.queryPermissionByUserId(1);
        for (String string:strings){
            System.out.println(string);
        }
    }


}