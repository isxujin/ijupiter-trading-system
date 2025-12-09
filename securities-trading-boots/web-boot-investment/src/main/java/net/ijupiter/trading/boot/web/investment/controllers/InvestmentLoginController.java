package net.ijupiter.trading.boot.web.investment.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/investment")
public class InvestmentLoginController extends InvestmentBaseController {

    @GetMapping("/login")
    public String login() {
        // 正确返回值：无需写.html后缀，直接返回"investor/login"
        return "investment/login";
        // 错误示例：return "login" / return "investor/Login" / return "investor/login.html"
    }
}