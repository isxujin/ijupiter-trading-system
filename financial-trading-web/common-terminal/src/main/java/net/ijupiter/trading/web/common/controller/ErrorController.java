package net.ijupiter.trading.web.common.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * 通用错误控制器
 * 直接处理错误页面请求，避免Spring Boot默认错误处理机制与Thymeleaf的冲突
 */
@Slf4j
@Controller
@RequestMapping("/common")
public class ErrorController extends BaseController {

    /**
     * 处理错误页面请求
     */
    @GetMapping("/error")
    public ModelAndView handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object exception = request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
        Object message = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        
        // 如果是直接访问/common/error，使用查询参数
        if (status == null) {
            status = request.getParameter("status");
        }
        if (message == null) {
            message = request.getParameter("message");
        }
        
        ModelAndView modelAndView = new ModelAndView("common/error");
        
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            log.error("Error occurred with status code: {}, message: {}", statusCode, message);
            
            modelAndView.addObject("status", statusCode);
            modelAndView.addObject("timestamp", LocalDateTime.now());
            
            // 根据不同的错误状态码设置不同的错误消息
            switch (statusCode) {
                case 404:
                    modelAndView.addObject("error", "页面未找到");
                    modelAndView.addObject("message", message != null ? message : "请求的页面不存在或已被移除");
                    break;
                case 403:
                    modelAndView.addObject("error", "访问被拒绝");
                    modelAndView.addObject("message", message != null ? message : "您没有权限访问此页面");
                    break;
                case 401:
                    modelAndView.addObject("error", "未授权访问");
                    modelAndView.addObject("message", message != null ? message : "请先登录后再访问此页面");
                    break;
                case 500:
                default:
                    modelAndView.addObject("error", "服务器内部错误");
                    modelAndView.addObject("message", message != null ? message : "抱歉，系统遇到了一些问题，请稍后再试");
                    break;
            }
        } else {
            modelAndView.addObject("status", 500);
            modelAndView.addObject("error", "未知错误");
            modelAndView.addObject("message", "发生了未知错误，请联系系统管理员");
            modelAndView.addObject("timestamp", LocalDateTime.now());
        }
        
        if (exception != null) {
            log.error("Exception details:", (Throwable) exception);
        }

        return modelAndView;
    }
}