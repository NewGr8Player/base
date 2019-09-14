package com.xavier.base.controller;

import com.xavier.base.common.BaseController;
import com.xavier.base.entity.User;
import com.xavier.base.service.UserService;
import com.xavier.base.util.PasswordUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;


@Controller
@RequestMapping("")
public class LoginController extends BaseController {

    private static final String LOGIN_MAPPING = "/login/login";

    private static final String INDEX_REDIRECT = "redirect:/index";

    @Autowired
    private UserService userService;

    @GetMapping(path = {"/", "/login"})
    public ModelAndView loginPage() {
        return modelAndView(LOGIN_MAPPING, "");
    }

    @PostMapping(path = {"/login"})
    public ModelAndView loginAction(User user) {
        String viewName = LOGIN_MAPPING;
        String message = "";
        if (Objects.nonNull(user)
                && StringUtils.isNotBlank(user.getUsername())
                && StringUtils.isNotBlank(user.getPassword())
        ) {
            try {
                User dbUser = userService.findByUserName(user.getUsername());
                if (null == dbUser) {
                    message = "用户不存在!";
                } else {
                    if (PasswordUtil.passwordValidator(user.getPassword(), dbUser.getPassword())) {
                        Subject subject = SecurityUtils.getSubject();
                        subject.login(new UsernamePasswordToken(dbUser.getUsername(), dbUser.getPassword()));
                        viewName = INDEX_REDIRECT;
                    } else {
                        message = "密码错误！";
                    }
                }
            } catch (AuthenticationException authException) {
                message = "用户认证失败!";
            } catch (Exception exception) {
                message = "系统内部错误，请联系管理员!";
            }
        } else {
            message = "请正确填写用户名与密码";
        }
        return modelAndView(viewName, message);
    }
}
