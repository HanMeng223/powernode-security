package com.example.mapper;


import com.example.pojo.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SysUserMapper {


    @Select("select user_id,username,password,sex,address,enabled,account_no_expired,credentials_no_expired,account_no_locked from sys_user where username=#{username}")
    SysUser getByUserName(@Param("username") String username);

}
