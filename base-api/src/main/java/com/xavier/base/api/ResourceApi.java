package com.xavier.base.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xavier.base.common.BaseController;
import com.xavier.base.common.responses.ApiResponses;
import com.xavier.base.annotations.BaseAuth;
import com.xavier.base.entity.Resource;
import com.xavier.base.enums.AuthTypeEnum;
import com.xavier.base.service.ResourceService;
import com.xavier.base.service.ScanMappings;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * ResourceApi
 *
 * @author NewGr8Player
 */
@Api(tags = {"Resource"}, description = "资源操作相关接口")
@RestController
@RequestMapping(value = "/resources", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Validated
public class ResourceApi extends BaseController {

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private ScanMappings scanMappings;

    @BaseAuth(auth = AuthTypeEnum.AUTH)
    @ApiOperation(value = "查询所有资源(分页)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "resourceName", value = "需要查询的资源名", paramType = "query"),
            @ApiImplicitParam(name = "method", value = "需要查询的请求方式", paramType = "query"),
            @ApiImplicitParam(name = "authType", value = "权限认证类型", paramType = "query")
    })
    @GetMapping
    public ApiResponses<IPage<Resource>> page(@RequestParam(value = "resourceName", required = false) String resourceName,
                                              @RequestParam(value = "method", required = false) String method,
                                              @RequestParam(value = "authType", required = false) AuthTypeEnum authType
    ) {
        return success(
                resourceService.lambdaQuery()
                        .like(StringUtils.isNotEmpty(resourceName), Resource::getResourceName, resourceName)
                        .eq(StringUtils.isNotEmpty(method), Resource::getMethod, method)
                        .eq(Objects.nonNull(authType), Resource::getAuthType, authType)
                        .page(this.<Resource>getPage())
        );
    }

    @BaseAuth(auth = AuthTypeEnum.AUTH)
    @ApiOperation(value = "查询所有资源")
    @GetMapping("/resources")
    public ApiResponses<List<Resource>> list() {
        return success(resourceService.list());
    }

    @BaseAuth(auth = AuthTypeEnum.AUTH)
    @ApiOperation(value = "刷新资源")
    @PutMapping
    public ApiResponses<Void> refresh() {
        scanMappings.doScan();
        return success();
    }


}

