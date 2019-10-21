package com.ibm.go.springbootdocker.shiro;


import com.ibm.go.springbootdocker.mapper.UserMapper;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class CustomRealm  extends AuthorizingRealm {

    private  final UserMapper userMapper;


    @Autowired
    public CustomRealm(UserMapper userMapper){
        this.userMapper = userMapper;
    }


    /**
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }


    /**
     * 获取身份验证信息
     *  shiro  最终通过  realm 来获取应用程序中的用户 角色及权限信息
     * @param authenticationToken     用户身份信息token
     * @return   返回了封装用户信息的 authenticationInfo 实例
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        System.out.println("--身份认证方法--");
        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;


        //通过用户名获取用户密码
        String password = userMapper.getPassword(token.getUsername());

        if(null == password){
            throw  new AccountException("")
        }




        return null;
    }
}
