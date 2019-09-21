package com.xavier.base.api;

import com.xavier.base.annotations.BaseAuth;
import com.xavier.base.common.BaseController;
import com.xavier.base.common.responses.ApiResponses;
import com.xavier.base.entity.Combo;
import com.xavier.base.entity.Menu;
import com.xavier.base.enums.AuthTypeEnum;
import com.xavier.base.enums.MenuTypeEnum;
import com.xavier.base.service.MenuService;
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
import java.util.stream.Collectors;


/**
 * MenuApi
 *
 * @author NewGr8Player
 */
@Api(tags = {"Menu"}, description = "菜单相关接口")
@RestController
@RequestMapping(value = "/menus", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Validated
public class MenuApi extends BaseController {

    @Autowired
    private MenuService menuService;

    @BaseAuth(auth = AuthTypeEnum.AUTH)
    @ApiOperation(value = "查询所有菜单")
    @GetMapping
    public ApiResponses<List<Menu>> list() {
        return success(menuService.list());
    }

    @BaseAuth(auth = AuthTypeEnum.AUTH)
    @ApiOperation(value = "查询父级菜单(下拉框)")
    @GetMapping("/combos")
    public ApiResponses<List<Combo>> combos() {
        return success(
                menuService.lambdaQuery().select(Menu::getId, Menu::getMenuName)
                        .in(Menu::getMenuType, MenuTypeEnum.CATALOG, MenuTypeEnum.MENU)
                        .list()
                        .stream()
                        .map(e -> {
                            Combo combo = new Combo();
                            combo.setId(e.getId());
                            combo.setName(e.getMenuName());
                            return combo;
                        }).collect(Collectors.toList())
        );
    }

    @BaseAuth(auth = AuthTypeEnum.AUTH)
    @ApiOperation(value = "查询单个菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "菜单ID", required = true, paramType = "path")
    })
    @GetMapping("/{id}")
    public ApiResponses<Menu> get(@PathVariable("id") Integer id) {
        return success(menuService.getMenuDetails(id));
    }

    @BaseAuth(auth = AuthTypeEnum.AUTH)
    @ApiOperation(value = "添加菜单")
    @PostMapping
    public ApiResponses<Void> create(@RequestBody @Validated(Menu.Create.class) Menu menu) {
        menuService.saveMenu(menu, menu.getResourceIds());
        return success(HttpStatus.CREATED);
    }

    @BaseAuth(auth = AuthTypeEnum.AUTH)
    @ApiOperation(value = "修改菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "菜单ID", required = true, paramType = "path")
    })
    @PutMapping("/{id}")
    public ApiResponses<Void> update(@PathVariable("id") String id, @RequestBody @Validated(Menu.Update.class) Menu menu) {
        menu.setId(id);
        menuService.updateMenu(menu, menu.getResourceIds());
        return success();
    }

    @BaseAuth(auth = AuthTypeEnum.AUTH)
    @ApiOperation(value = "删除菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "菜单ID", required = true, paramType = "path")
    })
    @DeleteMapping("/{id}")
    public ApiResponses<Void> delete(@PathVariable("id") String id) {
        menuService.removeMenu(id);
        return success(HttpStatus.NO_CONTENT);
    }

    @BaseAuth(auth = AuthTypeEnum.AUTH)
    @ApiOperation("设置菜单状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "菜单ID", required = true, paramType = "path")
    })
    @PutMapping("/{id}/status")
    public ApiResponses<Void> updateStatus(@PathVariable("id") Integer id, @RequestBody @Validated(Menu.Status.class) Menu menu) {
        menuService.updateStatus(id, menu.getStatus());
        return success();
    }

}

