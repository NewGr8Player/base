package com.xavier.base.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xavier.base.common.BaseController;
import com.xavier.base.common.responses.ApiResponses;
import com.xavier.base.annotations.BaseAuth;
import com.xavier.base.entity.Role;
import com.xavier.base.enums.AuthTypeEnum;
import com.xavier.base.service.RoleMenuService;
import com.xavier.base.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * RoleApi
 *
 * @author NewGr8Player
 */
@Api(tags = {"Role"}, description = "角色相关接口")
@RestController
@RequestMapping(value = "/roles", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Validated
public class RoleApi extends BaseController {

    @Autowired
    private RoleService roleService;
    @Autowired
    private RoleMenuService roleMenuService;

    @BaseAuth(auth = AuthTypeEnum.AUTH)
    @ApiOperation(value = "查询所有角色(分页)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleName", value = "需要查询的角色名", paramType = "query")
    })
    @GetMapping
    public ApiResponses<IPage<Role>> page(@RequestParam(value = "roleName", required = false) String roleName) {
        return success(roleService.pageRole(this.<Role>getPage(), roleName));
    }

    @BaseAuth(auth = AuthTypeEnum.AUTH)
    @ApiOperation(value = "查询所有角色")
    @GetMapping("/roles")
    public ApiResponses<List<Role>> list() {
        return success(roleService.list());
    }

    @BaseAuth(auth = AuthTypeEnum.AUTH)
    @ApiOperation(value = "查询单个角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "角色ID", required = true, paramType = "path")
    })
    @GetMapping("/{id}")
    public ApiResponses<Role> get(@PathVariable("id") Integer id) {
        Role role = roleService.getById(id);
        return success(role);
    }

    @BaseAuth(auth = AuthTypeEnum.AUTH)
    @ApiOperation(value = "添加角色")
    @PostMapping
    public ApiResponses<Void> create(@RequestBody @Validated(Role.Create.class) Role role) {
        roleService.save(role);
        return success(HttpStatus.CREATED);
    }

    @BaseAuth(auth = AuthTypeEnum.AUTH)
    @ApiOperation(value = "修改角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "角色ID", required = true, paramType = "path")
    })
    @PutMapping("/{id}")
    public ApiResponses<Void> update(@PathVariable("id") String id, @RequestBody @Validated(Role.Update.class) Role role) {
        role.setId(id);
        roleService.updateById(role);
        return success();
    }

    @BaseAuth(auth = AuthTypeEnum.AUTH)
    @ApiOperation(value = "删除角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "角色ID", required = true, paramType = "path")
    })
    @DeleteMapping("/{id}")
    public ApiResponses<Void> delete(@PathVariable("id") Integer id) {
        roleService.removeById(id);
        return success(HttpStatus.NO_CONTENT);
    }

    @BaseAuth(auth = AuthTypeEnum.AUTH)
    @ApiOperation(value = "修改角色菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "角色ID", required = true, paramType = "path")
    })
    @PutMapping("/{id}/menus")
    public ApiResponses<Void> menus(@PathVariable("id") String id, @RequestBody @NotEmpty(message = "菜单ID不能为空") List<String> menuIds) {
        roleMenuService.saveRoleMenu(id, menuIds);
        return success();
    }
}

