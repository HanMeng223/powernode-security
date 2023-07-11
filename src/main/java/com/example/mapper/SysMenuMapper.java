package com.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SysMenuMapper {

    @Select("select distinct sm.code from sys_role_user sru\n" +
            "    inner join sys_role_menu srm on sru.rid=srm.rid\n" +
            "    inner join sys_menu sm on srm.mid=sm.id where sru.uid=1")
    List<String> queryPermissionByUserId(@Param("userId") int userId);

}
