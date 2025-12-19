package net.ijupiter.trading.web.system.controllers;

import lombok.extern.slf4j.Slf4j;
import net.ijupiter.trading.web.common.controllers.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * 系统模块基础控制器
 */
@Slf4j
@Controller
@RequestMapping("/system")
public class SystemController extends BaseController {

    /**
     * 系统管理首页
     */
    @GetMapping
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("system/index");

        modelAndView.addObject("activeModule", "system");
        
        return modelAndView;
    }

}