package com.xavier.base.config.filter;

import com.xavier.base.config.wrapper.RequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 过滤器
 *
 * @author NewGr8Player
 */
@Slf4j
@Component
@WebFilter(filterName = "baseFilter", urlPatterns = "/*")
public class BaseFilter implements Filter {

    @Override
    public void init(FilterConfig config) {
        log.trace("{} init.", config.getFilterName());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse res,
                         FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        chain.doFilter(new RequestWrapper(req), res);
    }

    @Override
    public void destroy() {
        log.trace("BaseFilter destroy.");
    }

}
