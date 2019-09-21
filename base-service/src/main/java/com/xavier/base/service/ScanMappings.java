package com.xavier.base.service;

import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import com.baomidou.mybatisplus.core.toolkit.EncryptUtils;
import com.xavier.base.annotations.BaseAuth;
import com.xavier.base.entity.Resource;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务mapping扫描
 * </p>
 *
 * @author NewGr8Player
 */
@Service
public class ScanMappings {

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private RequestMappingHandlerMapping handlerMapping;

    private String[] emptyArray = new String[]{""};

    /**
     * 扫描资源插入数据库
     */
    public void doScan() {
        resourceService.saveOrUpdateBatch(
                handlerMapping.getHandlerMethods()
                        .values()
                        .stream()
                        .map(this::getResources)
                        .flatMap(Collection::stream)
                        .collect(Collectors.toList())
        );
    }

    /**
     * 获取Resource
     *
     * @param handlerMethod
     * @return
     */
    public List<Resource> getResources(HandlerMethod handlerMethod) {
        BaseAuth res = handlerMethod.getMethodAnnotation(BaseAuth.class);
        if (Objects.isNull(res)) {
            return Collections.emptyList();
        }
        RequestMapping requestMappingAnnotation = handlerMethod.getBeanType().getAnnotation(RequestMapping.class);
        RequestMapping methodMappingAnnotation = handlerMethod.getMethodAnnotation(RequestMapping.class);
        if (Objects.isNull(requestMappingAnnotation) && Objects.isNull(methodMappingAnnotation)) {
            return Collections.emptyList();
        }
        ApiOperation apiOperation = handlerMethod.getMethodAnnotation(ApiOperation.class);
        String[] requestMappings = Objects.nonNull(requestMappingAnnotation) ? requestMappingAnnotation.value() : emptyArray;
        String[] methodMappings = Objects.nonNull(methodMappingAnnotation) ? methodMappingAnnotation.path() : emptyArray;
        RequestMethod[] method = Objects.nonNull(methodMappingAnnotation) ? methodMappingAnnotation.method() : new RequestMethod[0];
        requestMappings = ArrayUtils.isEmpty(requestMappings) ? emptyArray : requestMappings;
        methodMappings = ArrayUtils.isEmpty(methodMappings) ? emptyArray : methodMappings;
        Set<String> mappings = new HashSet<>(1);
        for (String reqMapping : requestMappings) {
            for (String methodMapping : methodMappings) {
                mappings.add(reqMapping + methodMapping);
            }
        }
        List<Resource> resources = new ArrayList<>(1);
        for (RequestMethod requestMethod : method) {
            for (String mapping : mappings) {
                //接口描述
                Resource resource = new Resource();
                resource.setResourceName(Objects.nonNull(apiOperation) ? apiOperation.value() : "未命名资源路径");
                resource.setMapping(mapping);
                resource.setMethod(requestMethod.name());
                resource.setAuthType(res.auth());
                resource.setPerm(resourceService.getResourcePermTag(requestMethod.name(), mapping));
                resource.setId(EncryptUtils.md5Base64(resource.getPerm()));
                resources.add(resource);
            }
        }
        return resources;
    }

}
