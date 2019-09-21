package com.xavier.base.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xavier.base.common.BaseController;
import com.xavier.base.common.responses.ApiResponses;
import com.xavier.base.annotations.BaseAuth;
import com.xavier.base.entity.User;
import com.xavier.base.enums.AuthTypeEnum;
import com.xavier.base.enums.ErrorCodeEnum;
import com.xavier.base.enums.StatusEnum;
import com.xavier.base.service.UserService;
import com.xavier.base.util.ApiAssert;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.digest.Md5Crypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * UserApi
 *
 * @author NewGr8Player
 */
@Api(tags = {"User"}, description = "用户操作相关接口")
@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Validated
public class UserApi extends BaseController {

    @Autowired
    private UserService userService;

    @BaseAuth(auth = AuthTypeEnum.AUTH)
    @ApiOperation("查询所有用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginName", value = "需要检查的账号", paramType = "query"),
            @ApiImplicitParam(name = "nickname", value = "需要检查的账号", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "需要检查的账号", paramType = "query")
    })
    @GetMapping
    public ApiResponses<IPage<User>> page(@RequestParam(value = "loginName", required = false) String loginName,
                                          @RequestParam(value = "nickname", required = false) String nickname,
                                          @RequestParam(value = "status", required = false) StatusEnum status) {
        return success(
                userService.lambdaQuery().likeRight(StringUtils.isNotEmpty(loginName), User::getLoginName, loginName)
                        .likeRight(StringUtils.isNotEmpty(nickname), User::getNickname, nickname)
                        .eq(Objects.nonNull(status), User::getStatus, status)
                        .page(this.<User>getPage())
        );
    }

    @BaseAuth(auth = AuthTypeEnum.AUTH)
    @ApiOperation("查询单个用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path")
    })
    @GetMapping("/{id}")
    public ApiResponses<User> get(@PathVariable("id") String id) {
        User user = userService.getById(id);
        ApiAssert.notNull(ErrorCodeEnum.USER_NOT_FOUND, user);
        List<String> roleIds = userService.getRoleIds(user.getId());
        user.setRoleIds(roleIds);
        return success(user);
    }

    @BaseAuth(auth = AuthTypeEnum.AUTH)
    @ApiOperation("重置用户密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path")
    })
    @PutMapping("/{id}/password")
    public ApiResponses<Void> resetPwd(@PathVariable("id") Integer id) {
        userService.resetPwd(id);
        return success();
    }

    @BaseAuth(auth = AuthTypeEnum.AUTH)
    @ApiOperation("设置用户状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path")
    })
    @PutMapping("/{id}/status")
    public ApiResponses<Void> updateStatus(@PathVariable("id") Integer id, @RequestBody @Validated(User.Status.class) User user) {
        userService.updateStatus(id, user.getStatus());
        return success();
    }

    @BaseAuth(auth = AuthTypeEnum.AUTH)
    @ApiOperation("创建用户")
    @PostMapping
    public ApiResponses<Void> create(@RequestBody @Validated(User.Create.class) User user) {
        int count = userService.lambdaQuery().eq(User::getLoginName, user.getLoginName()).count();
        ApiAssert.isTrue(ErrorCodeEnum.USERNAME_ALREADY_EXISTS, count == 0);
        /* 没设置密码 设置默认密码 */
        if (StringUtils.isEmpty(user.getPassword())) {
            user.setPassword(Md5Crypt.apr1Crypt(user.getLoginName(), user.getLoginName()));
        }
        //默认禁用
        user.setStatus(StatusEnum.DISABLE);
        userService.save(user);
        userService.saveUserRoles(user.getId(), user.getRoleIds());
        return success(HttpStatus.CREATED);
    }

    @BaseAuth(auth = AuthTypeEnum.AUTH)
    @ApiOperation("修改用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path")
    })
    @PutMapping("/{id}")
    public ApiResponses<Void> update(@PathVariable("id") String id, @RequestBody @Validated(User.Update.class) User user) {
        userService.updateById(user);
        userService.saveUserRoles(id, user.getRoleIds());
        return success();
    }

}

