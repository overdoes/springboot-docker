package com.ibm.go.springbootdocker.config;


import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {


    /**
     * 过滤器默认权限表{anon=anon  authc=authc,authcBasic=authcBasic,logout=logout,
     * noSessionCreation=noSessionCreation,perms=perms, port=port
     * rest=rest,roles=roles,ssl=ssl,user=user}
     * <p>
     * anon,authc,authcBasic,user   认证过滤器
     * perms,port,rest,roles,ssl  授权过滤器
     * <p>
     * <p>
     * <p>
     * user 和 authc 的不同，当应用开启了rememberMe时,用户下次访问时可以是一个user, 但绝不会是authc,
     * 因为authc是需要重新认证的,user表示用户不一定已通过认证,只要曾被Shiro记住过登录状态的用户就可以正常发起请求，比如rememberMe
     * 以前的一个用户登录时开启了rememberMe, 然后他关闭浏览器, 下次再访问时他就是一个user, 而不会authc
     *
     * @param securityManager 初始化 ShiroFilterFactoryBean 的时候需要注入 SecurityManage
     */


    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //必须设置securityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //设置登录路径，如果不设置 ，会默认查找web工程目录下的"/login.jsp"页面或"/login"  映射
        shiroFilterFactoryBean.setLoginUrl("/notLogin");
        //设置无权限时跳转的路径
        shiroFilterFactoryBean.setUnauthorizedUrl("/notRole");
        //设置拦截器
        Map<String, String> filterchainDefinitionMap = new HashMap<>();
        //游客，开发权限
        filterchainDefinitionMap.put("/guest/**", "anon");
        //用户，需要角色权限 “user”
        filterchainDefinitionMap.put("/user/**", "roles[user]");
        //管理员 ，需要角色权限  admin
        filterchainDefinitionMap.put("/admin/**", "roles[admin]");
        //开放登录接口
        filterchainDefinitionMap.put("/login", "anon");

        //其余接口全部拦截
        //这行代码必须放在所有权限设置的最后面， 要不然会导致所有url  被拦截
        filterchainDefinitionMap.put("/**", "authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterchainDefinitionMap);
        System.out.println("shiro拦截器工厂注入成功");
        return shiroFilterFactoryBean;

    }

    /**
     * 注入 sevurityManager
     *
     * @param CustomRealm 自定义领域
     */
    @Bean
    public SecurityManager securityManager(CustomRealm customRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(customRealm);
        return securityManager;


    }
}
