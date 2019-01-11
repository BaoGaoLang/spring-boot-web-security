package com.bao.spring.bean;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @Title: User
 * @Description:
 * @Author: BaoGaoLang
 * @Date: 2017/12/8 16:48
 */
public class User  implements UserDetails{

    private Long id;
    private String username;
    private String password;
    private boolean enabled;
    private Collection<? extends GrantedAuthority> authorities;
    public User(Long id, String username, String password, boolean enabled) {
        super();
        this.id = id;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
    }

    public User(Long id, String username, String password, boolean enabled,
                Collection<? extends GrantedAuthority> authorities) {
        super();
        this.id = id;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.authorities = authorities;
    }
    public Long getId(){
        return this.id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public String getUsername() {
        return username;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String toString() {
        System.out.print("MyUserDetails [id=" + id + ", username=" + username
                + ", password=" + password + ", enabled=" + enabled
                + ", authorities=" + authorities + "]");
        return "MyUserDetails [id=" + id + ", username=" + username
                + ", password=" + password + ", enabled=" + enabled
                + ", authorities=" + authorities + "]";
    }

}
