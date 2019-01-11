package com.bao.spring.config;

import com.bao.spring.myfilter.MyBeforeLoginFilter;
import com.sun.deploy.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.annotation.Resource;
import javax.sql.DataSource;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

/**
 * @Title: SpringSecurityConfig
 * @Description:
 * @Author: BaoGaoLang
 * @Date: 2017/12/7 14:50
 */

@Configuration
public class SpringSecurityConfig  extends WebSecurityConfigurerAdapter {
    @Autowired
    private AccessDeniedHandler accessDeniedHandler;
    @Resource
    private DataSource dataSource;

    @Resource
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/home", "/about").permitAll()//请求路径允许访问
                .antMatchers("/admin/**").hasAnyRole("ADMIN")//需ADMIN权限并要校验才能访问
                .antMatchers("/user/**").hasAnyRole("USER")//需USER权限并要校验才能访问
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")//定义登录的页面"/login"，允许访问
                .permitAll()
                .and()
                .logout()
                .permitAll()//默认的"/logout", 允许访问
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler).and().rememberMe()
                .key("unique-and-secret")
                .rememberMeCookieName("remember-me-cookie-name")
                .tokenValiditySeconds(24 * 60 * 60);
        // HttpSecurity 有三个常用方法来配置：
        // addFilterBefore(Filter filter, Class<? extends Filter> beforeFilter)
        // 在 beforeFilter 之前添加 filter
        // addFilterAfter(Filter filter, Class<? extends Filter> afterFilter)
        // 在 afterFilter 之后添加 filter
        // addFilterAt(Filter filter, Class<? extends Filter> atFilter)
        // 在 atFilter 相同位置添加 filter， 此 filter 不覆盖 filter
        http.addFilterBefore(new MyBeforeLoginFilter(), AbstractPreAuthenticatedProcessingFilter.class);
    }

    // create two users, admin and user
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user").password("password").roles("USER")
                .and()
                .withUser("admin").password("password").roles("ADMIN");
    }


    //JDBC Authentication
    /*@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        System.out.println("====123456789=====");
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(
                        "select username,password, enabled from users where username=?")
                .authoritiesByUsernameQuery(
                        "select username,role from user_roles where username=?");
        auth.userDetailsService(userDetailsService);//.passwordEncoder(passwordEncoder());
    }*/

    //LDAP Authentication
/*    @Autowired
    private DataSource dataSource;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .ldapAuthentication()
                .userDnPatterns("uid={0},ou=people")
                .groupSearchBase("ou=groups");
    }*/


    /**
     * 密码加密
     */
    @Bean
    public Md5PasswordEncoder passwordEncoder(){
        return new Md5PasswordEncoder();
    }

}
