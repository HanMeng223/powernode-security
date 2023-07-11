package com.example.service.impl;

import com.example.pojo.SysUser;
import com.example.service.SysMenuService;
import com.example.service.SysUserService;
import com.example.vo.SecurityUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SecurityUserDetailsService implements UserDetailsService {
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysMenuService sysMenuService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser=sysUserService.getByUserName(username);

        if (null==sysUser){
            throw new UsernameNotFoundException("该用户名不存在");
        }

        List<String> userPermission = sysMenuService.queryPermissionByUserId(sysUser.getUserId());

        //方式一将List<String>转换成List<SimpleGrantedAuthority>
//        List<SimpleGrantedAuthority> authorities=new ArrayList<>();
//        for (String s:userPermission){
//            SimpleGrantedAuthority simpleGrantedAuthority=new SimpleGrantedAuthority(s);
//            authorities.add(simpleGrantedAuthority);
//        }



        //方式二使用stream流
        List<SimpleGrantedAuthority> authorities = userPermission.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());


        SecurityUser securityUser=new SecurityUser(sysUser);
        securityUser.setAuthorityList(authorities);
//        System.out.println(securityUser);

        return securityUser;


    }
}
