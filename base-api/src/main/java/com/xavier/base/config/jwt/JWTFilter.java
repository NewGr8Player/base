package com.xavier.base.config.jwt;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Throwables;
import com.xavier.base.common.APICons;
import com.xavier.base.entity.ResourcePerm;
import com.xavier.base.enums.ErrorCodeEnum;
import com.xavier.base.model.ErrorCode;
import com.xavier.base.service.ResourceService;
import com.xavier.base.util.ApiAssert;
import com.xavier.base.util.jwt.JWTGen;
import com.xavier.base.util.jwt.JWTVars;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.util.MimeTypeUtils;
import org.springframework.util.PathMatcher;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Predicate;

/**
 * preHandle->isAccessAllowed->isLoginAttempt->executeLogin
 */
@Slf4j
@Setter
public class JWTFilter extends BasicHttpAuthenticationFilter {

    private PathMatcher pathMatcher;
    private ResourceService resourceService;
    private UrlPathHelper urlPathHelper;
    private String contextPath;

    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) {
        //获取请求token
        String token = getToken(WebUtils.toHttp(servletRequest));
        return StringUtils.isBlank(token) ? null : new JWTToken(token);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        request.setAttribute(APICons.API_BEGIN_TIME, System.currentTimeMillis());
        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        HttpServletResponse httpResponse = WebUtils.toHttp(response);

        String token = getToken(httpRequest);
        String method = httpRequest.getMethod();
        String requestUri = urlPathHelper.getOriginatingRequestUri(httpRequest);
        if (StringUtils.isNotEmpty(contextPath)) {
            requestUri = requestUri.replaceFirst(contextPath, "");
        }
        Optional<String> optional = resourceService.getResourcePerms(method)
                .stream()
                .filter(match(method, requestUri))
                .map(ResourcePerm::getMapping)
                .min(pathMatcher.getPatternComparator(requestUri));
        request.setAttribute(APICons.API_REQURL, requestUri);
        request.setAttribute(APICons.API_METHOD, method);
        if (optional.isPresent()) {
            request.setAttribute(APICons.API_MAPPING, optional.get());
        } else {
            httpResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return false;
        }
        if (Objects.isNull(token)) {
            List<ResourcePerm> openPerms = resourceService.getOpenPerms();
            boolean match = anyMatch(openPerms, method, requestUri);
            if (!match) {
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
            return match;
        }
        if (isLoginRequest(request, response)) {
            try {
                if (executeLogin(request, response)) {
                    String uid = new JWTGen().getClaimField(getToken(request));
                    request.setAttribute(APICons.API_UID, uid);
                    Set<ResourcePerm> perms = resourceService.getUserResourcePerms(uid);
                    return anyMatch(perms, method, requestUri);
                } else {
                    httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return sendUnauthorizedFail(request, response);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;

    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) {
        HttpServletResponse httpResponse = WebUtils.toHttp(response);
        switch (httpResponse.getStatus()) {
            case HttpServletResponse.SC_NOT_FOUND:
                return sendNotFoundFail(request, response);
            case HttpServletResponse.SC_UNAUTHORIZED:
                return sendUnauthorizedFail(request, response);
            default:
                return sendForbiddenFail(request, response);
        }
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        return sendUnauthorizedFail(request, response);
    }

    /**
     * 无权限
     */
    protected boolean sendForbiddenFail(ServletRequest request, ServletResponse response) {
        writeValueAsJson(response,ErrorCodeEnum.FORBIDDEN.convert());
        return false;
    }

    /**
     * 路径不存在
     */
    protected boolean sendNotFoundFail(ServletRequest request, ServletResponse response) {
        writeValueAsJson(response,ErrorCodeEnum.NOT_FOUND.convert());
        return false;
    }

    /**
     * 未认证
     */
    protected boolean sendUnauthorizedFail(ServletRequest request, ServletResponse response) {
        writeValueAsJson(response,ErrorCodeEnum.UNAUTHORIZED.convert());
        return false;
    }


    /**
     * 是否任意匹配权限URL
     *
     * @param perms
     * @return
     */
    protected boolean anyMatch(Collection<ResourcePerm> perms, String method, String requestUri) {
        return perms.stream().anyMatch(match(method, requestUri));
    }

    /**
     * 匹配请求方法与路径
     *
     * @param method
     * @param requestUri
     * @return
     */
    private Predicate<ResourcePerm> match(String method, String requestUri) {
        return res -> res.getMethod().equalsIgnoreCase(method) && pathMatcher.match(res.getMapping(), requestUri);
    }

    /**
     * 判断用户是否想要登入。
     * 检测 header 里面是否包含 Token 字段
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        return ((HttpServletRequest) request).getHeader(JWTVars.HEADER_NAME) != null;
    }

    /**
     * 执行登陆操作
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = httpServletRequest.getHeader(JWTVars.HEADER_NAME);
        ApiAssert.isFalse(ErrorCodeEnum.UNAUTHORIZED, new JWTGen().isExpired(token));
        /* 如果没有抛出异常则代表登入成功，返回true */
        return true;
    }

    /**
     * 对跨域提供支持
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        /* 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态 */
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }

    public String getToken(ServletRequest request) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        return httpServletRequest.getHeader(JWTVars.HEADER_NAME);
    }

    /**
     * Json字符串值写入response
     *
     * @param response
     * @param obj
     */
    private void writeValueAsJson(ServletResponse response, ErrorCode obj) {
        if (response.isCommitted()) {
            log.warn("Warn: Response isCommitted, Skip the implementation of the method.");
            return;
        }
        response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        try (PrintWriter writer = response.getWriter()) {
            writer.print(JSONObject.toJSONString(obj));
            writer.flush();
        } catch (IOException e) {
            log.warn("Error: Response printJson failed, stackTrace: {}", Throwables.getStackTraceAsString(e));
        }
    }
}

