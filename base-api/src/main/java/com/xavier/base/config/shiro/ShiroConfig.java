package com.xavier.config.shiro;

import com.xavier.config.redis.RedisSessionDao;
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
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		/* 必须设置 SecurityManager */
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		/* 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面 */
		shiroFilterFactoryBean.setLoginUrl("/login");
		/* 登录成功后要跳转的链接 */
		shiroFilterFactoryBean.setSuccessUrl("/index");
		/* 未授权界面 */
		shiroFilterFactoryBean.setUnauthorizedUrl("/error/403");
		/* 拦截器 */
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
		/* 配置不会被拦截的链接 顺序判断 */
		filterChainDefinitionMap.put("/static/**", "anon");
		/* 放行swagger */
		filterChainDefinitionMap.put("/swagger-ui.html", "anon");
		filterChainDefinitionMap.put("/swagger/**", "anon");
		filterChainDefinitionMap.put("/webjars/**", "anon");
		filterChainDefinitionMap.put("/swagger-resources/**", "anon");
		filterChainDefinitionMap.put("/v2/**", "anon");
		/* 放行Druid */
		filterChainDefinitionMap.put("/druid/**", "anon");

		/* 配置退出过滤器,其中的具体的退出代码Shiro已经替我们实现了 */
		filterChainDefinitionMap.put("/logout", "logout");

		filterChainDefinitionMap.put("/**", "authc");

		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilterFactoryBean;
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
	 *
	 * @param redisTemplate
	 * @return
	 */
	@Bean(name = "securityManager")
	public SecurityManager securityManager(RedisTemplate redisTemplate) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		//自定义realm
		securityManager.setRealm(customRealm(redisTemplate));
		//自定义session管理 使用redis
		securityManager.setSessionManager(sessionManager(redisTemplate));

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
		//认证
		shiroRealm.setAuthenticationCachingEnabled(false);
		//授权
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
	 * 配置sessionmanager，由redis存储数据
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
	public SessionIdGenerator sessionIdGenerator(String name) {
		return new SessionIdGenerator(name);
	}

}