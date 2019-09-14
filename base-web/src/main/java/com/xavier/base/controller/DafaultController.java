package com.xavier.base.controller;

import com.xavier.base.common.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/default")
public class DafaultController extends BaseController {

    private static final String DEFAULT_MAPPING = "/default/default";

    @RequestMapping(path = {""}, method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView defaultPage() {
        return modelAndView(DEFAULT_MAPPING);
    }
}
