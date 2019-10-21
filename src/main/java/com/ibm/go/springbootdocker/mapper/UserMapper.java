package com.ibm.go.springbootdocker.mapper;


import org.springframework.stereotype.Repository;

/**
 * @author  zjl321xj@gmail.com
 * @Date
 */
@Repository
public interface UserMapper {


    /**
     * 获取密码
     * @param username 用户名
     * @return
     */
    String getPassword(String username);


    /**
     * 获取用户权限
     * @param username  用户名
     * @return
     */
    String  getRole(String username);
}
