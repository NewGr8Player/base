package com.xavier.base.common;

import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

public class BaseController {

    protected ModelAndView modelAndView(String viewName) {
        ModelAndView modelAndView = commonInfo();
        modelAndView.setViewName(viewName);
        return modelAndView;
    }

    protected ModelAndView modelAndView(String viewName, String message) {
        ModelAndView modelAndView = commonInfo();
        modelAndView.setViewName(viewName);
        modelAndView.addObject("message", message);
        return modelAndView;
    }

    protected ModelAndView modelAndView(String viewName, Map<String, Object> messages) {
        ModelAndView modelAndView = commonInfo();
        modelAndView.setViewName(viewName);
        modelAndView.addAllObjects(messages);
        return modelAndView;
    }

    private ModelAndView commonInfo() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("SITE_NAME", CommonInfo.SITE_NAME);
        modelAndView.addObject("AUTHOR_NAME", CommonInfo.AUTHOR_NAME);
        modelAndView.addObject("AUTHOR_SITE", CommonInfo.AUTHOR_SITE);
        return modelAndView;
    }
}
