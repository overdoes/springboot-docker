package com.ibm.go.springbootdocker.shiro;


import com.ibm.go.springbootdocker.mapper.UserMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class CustomRealm extends AuthorizingRealm {

    private final UserMapper userMapper;


    @Autowired
    public CustomRealm(UserMapper userMapper) {
        this.userMapper = userMapper;
    }


    /**
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        System.out.println("————权限认证————");


        String userName = (String) SecurityUtils.getSubject().getPrincipal();

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        //获取该用户角色
        String role = userMapper.getRole(userName);
        Set<String> set = new HashSet<>();
        set.add(role);

        authorizationInfo.setRoles(set);

        return authorizationInfo;
    }


    /**
     * 获取身份验证信息
     * shiro  最终通过  realm 来获取应用程序中的用户 角色及权限信息
     *
     * @param authenticationToken 用户身份信息token
     * @return 返回了封装用户信息的 authenticationInfo 实例
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        System.out.println("--身份认证方法--");
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;


        //通过用户名获取用户密码
        String password = userMapper.getPassword(token.getUsername());

        if (null == password) {
            throw new AccountException("用户名不正确");
            //Credentials （证书） 密码
        } else if (!password.equals(new String((char[]) token.getCredentials()))) {
            throw new AccountException("密码不正确");
        }


        //Principal 主要的  首要的
        return new SimpleAuthenticationInfo(token.getPrincipal(), password, getName());
    }
}
