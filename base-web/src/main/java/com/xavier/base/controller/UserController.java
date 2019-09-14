package com.xavier.base.controller;

import com.xavier.base.common.BaseController;
import com.xavier.base.common.Page;
import com.xavier.base.entity.ResponseEntity;
import com.xavier.base.entity.User;
import com.xavier.base.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    private static final String USER_LIST_MAPPING = "/user/user-list";
    private static final String USER_ADD_MAPPING = "/user/user-add";
    private static final String USER_EDIT_MAPPING = "/user/user-edit";
    private static final String USER_VIEW_MAPPING = "/user/user-view";

    @Autowired
    private UserService userService;

    @RequiresPermissions("sys:user:view")
    @GetMapping(path = "/list")
    public ModelAndView userList() {
        return modelAndView(USER_LIST_MAPPING);
    }

    @RequiresPermissions("sys:user:edit")
    @GetMapping(path = "/add")
    public ModelAndView userAdd() {
        return modelAndView(USER_ADD_MAPPING);
    }

    @ResponseBody
    @RequiresPermissions("sys:user:view")
    @RequestMapping(path = "/queryList", method = {RequestMethod.GET, RequestMethod.POST})
    public Page<User> queryList(Page<User> userPage, User user) {
        return (Page<User>) userService.selectUserPage(userPage, user);
    }

    @ResponseBody
    @RequiresPermissions("sys:user:edit")
    @RequestMapping(path = "/save", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity queryList(@RequestBody User user) {
        String msg = "", data = "";
        try {
            userService.save(user);
        } catch (Exception e) {
            msg = "保存失败";
            data = e.getMessage();
        }
        return new ResponseEntity<>(200, msg, data);
    }

}
