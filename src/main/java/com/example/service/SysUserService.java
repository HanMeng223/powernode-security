package com.example.service;

import com.example.pojo.SysUser;
import org.apache.ibatis.annotations.Param;

public interface SysUserService {
    SysUser getByUserName( String username);

}
