package com.example.service.impl;


import com.example.mapper.SysMenuMapper;
import com.example.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    SysMenuMapper sysMenuMapper;


    @Override
    public List<String> queryPermissionByUserId(int userId) {

        return sysMenuMapper.queryPermissionByUserId(userId);
    }
}
