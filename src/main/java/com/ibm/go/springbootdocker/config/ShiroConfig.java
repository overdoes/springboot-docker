package com.ibm.go.springbootdocker.config;


import com.ibm.go.springbootdocker.shiro.CustomRealm;
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
     * filter chain 定义说明
     *  1.一个url 可以配置多个filter，使用逗号隔开
     *  2.多个过滤器必须全部通过才行
     *  3.部分过滤器可以指定参数 如：roles perms
     *
     *
     *  anon:任何人都可以访问  authc:必须登录才可以访问，不包括 remember me
     *  user: 登录用户才能访问，  包含 remember me
     *  perms:指定过滤规则，一般是扩展使用，不会使用原生的
     *
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
