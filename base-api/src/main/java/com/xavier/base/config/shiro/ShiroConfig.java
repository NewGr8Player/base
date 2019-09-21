package com.xavier.base.config.shiro;

import com.xavier.base.config.jwt.JWTFilter;
import com.xavier.base.service.ResourceService;
import com.xavier.base.service.UserService;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.pam.FirstSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SessionStorageEvaluator;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSessionStorageEvaluator;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.Filter;
import java.util.*;

@Configuration
public class ShiroConfig {

    @Value("${server.servlet.context-path:/}")
    private String contextPath;

    @Bean
    public DefaultWebSecurityManager securityManager(JWTRealm jwtRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setSubjectFactory(subjectFactory());
        securityManager.setSessionManager(sessionManager());
        ((DefaultSessionStorageEvaluator) ((DefaultSubjectDAO) securityManager.getSubjectDAO()).getSessionStorageEvaluator()).setSessionStorageEnabled(false);
        securityManager.setRealm(jwtRealm);
        return securityManager;
    }

    @Bean
    public DefaultWebSubjectFactory subjectFactory() {
        StatelessDefaultSubjectFactory subjectFactory = new StatelessDefaultSubjectFactory();
        return subjectFactory;

    }

    @Bean
    public DefaultSessionManager sessionManager() {
        DefaultSessionManager sessionManager = new DefaultSessionManager();
        sessionManager.setSessionValidationSchedulerEnabled(false);
        return sessionManager;
    }

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
    @Bean
    public ShiroFilterFactoryBean shiroFilter(@Autowired SecurityManager securityManager, @Autowired ResourceService resourceService) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);
        Map<String, Filter> filters = new HashMap<>();
        JWTFilter filter = new JWTFilter();
        filter.setAuthzScheme("Bearer");
        filter.setUrlPathHelper(new UrlPathHelper());
        filter.setResourceService(resourceService);
        filter.setPathMatcher(new AntPathMatcher());
        filter.setContextPath(cleanContextPath(contextPath));
        filters.put("jwt", filter);
        shiroFilter.setFilters(filters);
        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/**/**.*", "anon");
        filterMap.put("/v2/api-docs", "anon");
        filterMap.put("/swagger-resources/**", "anon");
        filterMap.put("/", "anon");
        filterMap.put("/**", "jwt");
        shiroFilter.setFilterChainDefinitionMap(filterMap);
        return shiroFilter;
    }

    @Bean
    protected SessionStorageEvaluator sessionStorageEvaluator() {
        DefaultWebSessionStorageEvaluator sessionStorageEvaluator = new DefaultWebSessionStorageEvaluator();
        sessionStorageEvaluator.setSessionStorageEnabled(false);
        return sessionStorageEvaluator;
    }

    private String cleanContextPath(String contextPath) {
        if (StringUtils.hasText(contextPath) && contextPath.endsWith("/")) {
            return contextPath.substring(0, contextPath.length() - 1);
        }
        return contextPath;
    }
}