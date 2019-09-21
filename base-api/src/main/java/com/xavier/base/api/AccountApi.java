package com.xavier.base.api;

import com.xavier.base.annotations.BaseAuth;
import com.xavier.base.common.BaseController;
import com.xavier.base.common.responses.ApiResponses;
import com.xavier.base.entity.MenuTree;
import com.xavier.base.entity.Password;
import com.xavier.base.entity.Token;
import com.xavier.base.entity.User;
import com.xavier.base.enums.AuthTypeEnum;
import com.xavier.base.service.MenuService;
import com.xavier.base.service.UserService;
import com.xavier.base.util.IpUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * AccountApi
 *
 * @author NewGr8Player
 */
@Api(tags = {"Account"}, description = "账号操作相关接口")
@RestController
@RequestMapping(value = "/account", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Validated
public class AccountApi extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private MenuService menuService;

    @BaseAuth(auth = AuthTypeEnum.OPEN)
    @ApiOperation("获取Token")
    @PostMapping("/token")
    public ApiResponses<Token> getToken(@RequestBody @Validated User login) {
        User user = userService.login(login.getLoginName(), login.getPassword(), IpUtils.getIpAddr(request));
        Token token = userService.getToken(user);
        return success(token);
    }

    @BaseAuth(auth = AuthTypeEnum.LOGIN)
    @ApiOperation("清除Token")
    @DeleteMapping("/token")
    public ApiResponses<Void> removeToken() {
        return success(HttpStatus.NO_CONTENT);
    }

    @BaseAuth(auth = AuthTypeEnum.LOGIN)
    @ApiOperation("修改密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "oldPassword", value = "原密码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "newPassword", value = "新密码", required = true, dataType = "String"),
    })
    @PutMapping("/password")
    public ApiResponses<Void> updatePassword(@RequestBody @Validated Password password) {
        userService.updatePassword(currentUid(), password.getOldPassword(), password.getNewPassword());
        return success();
    }

    @BaseAuth(auth = AuthTypeEnum.LOGIN)
    @ApiOperation("获取账户详情")
    @GetMapping("/info")
    public ApiResponses<User> accountInfo() {
        String uid = currentUid();
        User userDetails = userService.getUserDetails(uid);
        return success(userDetails);
    }

    @BaseAuth(auth = AuthTypeEnum.LOGIN)
    @ApiOperation("修改账户信息")
    @PutMapping("/info")
    public ApiResponses<Void> accountInfo(@RequestBody @Validated User user) {
        String uid = currentUid();
        user.setId(uid);
        userService.updateById(user);
        return success();
    }

    @BaseAuth(auth = AuthTypeEnum.LOGIN)
    @ApiOperation("获取账户菜单")
    @GetMapping("/menus")
    public ApiResponses<List<MenuTree>> menus() {
        List<MenuTree > menuTrees = menuService.getUserPermMenus(currentUid());
        return success(menuTrees);
    }

    @BaseAuth(auth = AuthTypeEnum.LOGIN)
    @ApiOperation("获取账户按钮")
    @GetMapping("/buttons/aliases")
    public ApiResponses<Set<String>> buttonsAliases() {
        return success(menuService.getUserPermButtonAliases(currentUid()));
    }
}

