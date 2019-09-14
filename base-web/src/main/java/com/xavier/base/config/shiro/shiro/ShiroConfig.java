package com.xavier.base.config.shiro;

import com.xavier.base.config.jwt.JWTFilter;
import com.xavier.base.config.redis.RedisSessionDao;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    /**
     * ShiroFilterFactoryBean 处理拦截资源文件问题。
     * 注意：单独一个ShiroFilterFactoryBean配置是或报错的，以为在
     * 初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager
     * <p>
     * Filter Chain定义说明
     * 1、一个URL可以配置多个Filter，使用逗号分隔
     * 2、当设置多个过滤器时，全部验证通过，才视为通过
     * 3、部分过滤器可指定参数，如perms，roles
     */
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();

        Map<String, Filter> filterMap = new HashMap<>();
        filterMap.put("jwt", new JWTFilter());
        factoryBean.setFilters(filterMap);

        factoryBean.setSecurityManager(securityManager);
        factoryBean.setUnauthorizedUrl("/401");

        Map<String, String> filterRuleMap = new HashMap<>();
        filterRuleMap.put("/**", "jwt");
        filterRuleMap.put("/v2/**", "anon");
        filterRuleMap.put("/status/**", "anon");
        factoryBean.setFilterChainDefinitionMap(filterRuleMap);

        return factoryBean;
    }

    /**
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),
     * 需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * 配置以下两个bean(
     * DefaultAdvisorAutoProxyCreator(可选)
     * 和
     * AuthorizationAttributeSourceAdvisor
     * )即可实现此功能
     *
     * @return
     */
    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(RedisTemplate redisTemplate) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager(redisTemplate));
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * Shiro生命周期处理器
     *
     * @return
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * FilterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));
        filterRegistration.setEnabled(true);
        filterRegistration.addUrlPatterns("/*");
        filterRegistration.setDispatcherTypes(DispatcherType.REQUEST);
        return filterRegistration;
    }

    /**
     * 安全管理模块，所有的manager在此配置
     * <a href="http://shiro.apache.org/session-management.html#SessionManagement-StatelessApplications%28Sessionless%29">关闭shiro自带的session</a>
     *
     * @param redisTemplate
     * @return
     */
    @Bean(name = "securityManager")
    public SecurityManager securityManager(RedisTemplate redisTemplate) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        /* 使用自己的realm */
        securityManager.setRealm(customRealm(redisTemplate));
        /* 关闭shiro自带的session */
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);

        return securityManager;
    }

    /**
     * 认证与授权模块
     *
     * @param redisTemplate
     * @return
     */
    @Bean(name = "myShiroRealm")
    @DependsOn(value = {"lifecycleBeanPostProcessor", "shiroRedisCacheManager"})
    public CustomRealm customRealm(RedisTemplate redisTemplate) {
        CustomRealm shiroRealm = new CustomRealm();
        shiroRealm.setCacheManager(redisCacheManager(redisTemplate));
        shiroRealm.setCachingEnabled(true);
        /* 认证 */
        shiroRealm.setAuthenticationCachingEnabled(false);
        /* 授权 */
        shiroRealm.setAuthorizationCachingEnabled(false);
        return shiroRealm;
    }

    /**
     * 缓存管理器的配置
     *
     * @param redisTemplate
     * @return
     */
    @Bean(name = "shiroRedisCacheManager")
    public ShiroRedisCacheManager redisCacheManager(RedisTemplate redisTemplate) {
        ShiroRedisCacheManager redisCacheManager = new ShiroRedisCacheManager(redisTemplate);
        redisCacheManager.createCache("shiro_redis_");/* 前缀 */
        return redisCacheManager;
    }

    /**
     * 配置SessionManager，由redis存储数据
     */
    @Bean(name = "sessionManager")
    @DependsOn(value = "lifecycleBeanPostProcessor")
    public DefaultWebSessionManager sessionManager(RedisTemplate redisTemplate) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        RedisSessionDao redisSessionDao = new RedisSessionDao(redisTemplate);

        redisSessionDao.setSessionIdGenerator(sessionIdGenerator("redis"));
        sessionManager.setSessionDAO(redisSessionDao);
        sessionManager.setDeleteInvalidSessions(true);
        SimpleCookie cookie = new SimpleCookie();
        cookie.setName("redis");
        sessionManager.setSessionIdCookie(cookie);
        sessionManager.setSessionIdCookieEnabled(true);
        return sessionManager;
    }

    /**
     * 自定义的SessionId生成器
     *
     * @param name
     * @return
     */
    public ShiroSessionIdGenerator sessionIdGenerator(String name) {
        return new ShiroSessionIdGenerator(name);
    }

}