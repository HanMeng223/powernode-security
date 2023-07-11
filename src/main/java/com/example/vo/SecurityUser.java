package com.example.vo;

import com.example.pojo.SysUser;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Data
public class SecurityUser implements UserDetails {

    private final SysUser sysUser;
    public SecurityUser(SysUser sysUser){
        this.sysUser=sysUser;
    }
    public SysUser getSysUser() {
        return sysUser;
    }

    //用于存储权限的的list
    private List<SimpleGrantedAuthority> authorityList;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorityList;
    }

    public void setAuthorityList(List<SimpleGrantedAuthority> authorityList) {
        this.authorityList = authorityList;
    }

    @Override
    public String getPassword() {
        String myPassword=sysUser.getPassword();
        sysUser.setPassword(null);
        return myPassword;
    }

    @Override
    public String getUsername() {
        return this.sysUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.sysUser.getAccountNoExpired().equals(1);
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.sysUser.getAccountNoLocked().equals(1);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.sysUser.getCredentialsNoExpired().equals(1);
    }

    @Override
    public boolean isEnabled() {
        return this.sysUser.getEnabled().equals(1);
    }
}
