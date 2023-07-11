package com.example.service;


import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysMenuService {
    List<String> queryPermissionByUserId(int userId);
}
