package com.xavier.base.common;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xavier.base.common.responses.ApiResponses;
import com.xavier.base.entity.User;
import com.xavier.base.service.UserService;
import com.xavier.base.util.TypeUtils;
import com.xavier.base.util.filter.AntiSQLFilter;
import com.xavier.base.util.jwt.JWTGen;
import com.xavier.base.util.jwt.JWTVars;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Optional;

/**
 * SuperController
 *
 * @author NewGr8Player
 */
public class BaseController {

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpServletResponse response;

    @Autowired
    private UserService userService;

    @Autowired
    private JWTGen tokenService;

    /**
     * 成功返回
     *
     * @param object
     * @return
     */
    public <T> ApiResponses<T> success(T object) {
        return ApiResponses.<T>success(response, object);
    }

    /**
     * 成功返回
     *
     * @return
     */
    public ApiResponses<Void> success() {
        return success(HttpStatus.OK);
    }

    /**
     * 成功返回
     *
     * @param status
     * @param object
     * @return
     */
    public <T> ApiResponses<T> success(HttpStatus status, T object) {
        return ApiResponses.<T>success(response, status, object);
    }


    /**
     * 成功返回
     *
     * @param status
     * @return
     */
    public ApiResponses<Void> success(HttpStatus status) {
        return ApiResponses.<Void>success(response, status);
    }

    /**
     * 获取当前用户id
     */
    public String currentUid() {
        String token = request.getHeader(JWTVars.HEADER_NAME);
        return userService.lambdaQuery().eq(User::getLoginName, tokenService.getClaimField(token)).one().getId();
    }

    /**
     * 获取分页对象
     *
     * @return
     */
    protected <T> Page<T> getPage() {
        return getPage(false);
    }

    /**
     * 获取分页对象
     *
     * @param openSort
     * @return
     */
    protected <T> Page<T> getPage(boolean openSort) {
        int index = 1;
        // 页数
        Integer cursor = TypeUtils.castToInt(request.getParameter(PageCons.PAGE_PAGE), index);
        // 分页大小
        Integer limit = TypeUtils.castToInt(request.getParameter(PageCons.PAGE_ROWS), PageCons.DEFAULT_LIMIT);
        // 是否查询分页
        Boolean searchCount = TypeUtils.castToBoolean(request.getParameter(PageCons.SEARCH_COUNT), true);
        limit = limit > PageCons.MAX_LIMIT ? PageCons.MAX_LIMIT : limit;
        Page<T> page = new Page<>(cursor, limit, searchCount);
        if (openSort) {
            page.addOrder(OrderItem.ascs(getParameterSafeValues(PageCons.PAGE_ASCS)));
            page.addOrder(OrderItem.descs(getParameterSafeValues(PageCons.PAGE_DESCS)));
        }
        return page;
    }

    /**
     * 获取安全参数(SQL ORDER BY 过滤)
     *
     * @param parameter
     * @return
     */
    protected String[] getParameterSafeValues(String parameter) {
        return AntiSQLFilter.getSafeValues(request.getParameterValues(parameter));
    }
}
