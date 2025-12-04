package net.ijupiter.trading.web.common.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Web应用错误控制器
 * 重定向到common/error页面，实现统一错误处理
 */
@Slf4j
@Controller
public class WebErrorController implements ErrorController {

    private static final String ERROR_PATH = "/error";

    @GetMapping(value = ERROR_PATH)
    public RedirectView handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object message = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        
        // 构建重定向URL
        String redirectUrl = "/common/error";
        if (status != null) {
            redirectUrl += "?status=" + status;
        }
        if (message != null) {
            redirectUrl += (status != null ? "&" : "?") + "message=" + message;
        }
        
        log.debug("Redirecting error to: {}", redirectUrl);
        
        RedirectView redirectView = new RedirectView(redirectUrl);
        redirectView.setExposeModelAttributes(false);
        return redirectView;
    }
}