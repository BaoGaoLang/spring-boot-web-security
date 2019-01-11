package com.bao.spring.service;

import com.bao.spring.bean.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * @Title: CustomizeUserDetailsService
 * @Description:
 * @Author: BaoGaoLang
 * @Date: 2017/12/8 16:45
 */

@Component("userDetailsService")
public class CustomizeUserDetailsService implements UserDetailsService
{

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final String sqlLoadUser;
    private final String sqlLoadAuthorities;
    private final RowMapper<User> myUserDetailsRowMapper;
    private final RowMapper<GrantedAuthority> authorityRowMapper;

    private static Logger logger = LoggerFactory.getLogger(CustomizeUserDetailsService.class);

    public CustomizeUserDetailsService() {
        super();
        sqlLoadUser = "SELECT id,username,password,enabled FROM users WHERE username=?";
        sqlLoadAuthorities = "SELECT role FROM user_roles WHERE username=?";
        myUserDetailsRowMapper = (rs, rowNum) -> new User(rs.getLong(1), rs.getString(2),
                rs.getString(3), rs.getBoolean(4));
        authorityRowMapper = (rs, rowNum) -> new SimpleGrantedAuthority(rs.getString(1));
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        logger.info("用户名:{}", username);
        try {
            User userFromQuery = jdbcTemplate.queryForObject(sqlLoadUser,
                    myUserDetailsRowMapper, username);
            logger.info("查询得到用户：{}", userFromQuery);
            List<GrantedAuthority> authorities = jdbcTemplate.query(
                    sqlLoadAuthorities, authorityRowMapper, username);
            logger.info("得到其权限：{}", authorities);
            return new User(userFromQuery.getId(), userFromQuery.getUsername(),
                    userFromQuery.getPassword(), userFromQuery.isEnabled(),
                    authorities);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            logger.info("查询结果集为空:{}", username+"========"+e.getMessage());
            throw new UsernameNotFoundException("用户名或密码不正确");
        }
    }
}
