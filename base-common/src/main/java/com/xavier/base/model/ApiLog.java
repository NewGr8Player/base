package com.xavier.base.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.xavier.base.entity.BaseEntity;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

/**
 * api请求日志详情
 *
 * @author NewGr8Player
 */
@Getter
@Builder
public class ApiLog extends BaseEntity {

    /**
     * 参数
     */
    private Map<String, String[]> parameterMap;
    /**
     * requestBody
     */
    private Object requestBody;
    /**
     * 请求路径
     */
    private String url;
    /**
     * 请求mapping
     */
    private String mapping;
    /**
     * 请求方法
     */
    private String method;
    /**
     * 日志需要打印的json字符串
     */
    private Object result;
    /**
     * IP地址
     */
    private String ip;
    /**
     * UID
     */
    private String uid;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ApiLog apiLog = (ApiLog) o;
        return Objects.equal(parameterMap, apiLog.parameterMap) &&
                Objects.equal(requestBody, apiLog.requestBody) &&
                Objects.equal(url, apiLog.url) &&
                Objects.equal(mapping, apiLog.mapping) &&
                Objects.equal(method, apiLog.method) &&
                Objects.equal(result, apiLog.result) &&
                Objects.equal(ip, apiLog.ip) &&
                Objects.equal(uid, apiLog.uid);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), parameterMap, requestBody, url, mapping, method, result, ip, uid);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("parameterMap", parameterMap)
                .add("requestBody", requestBody)
                .add("url", url)
                .add("mapping", mapping)
                .add("method", method)
                .add("result", result)
                .add("ip", ip)
                .add("uid", uid)
                .toString();
    }
}
