package net.ijupiter.trading.boot.web.investor.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/investor")
public class ProductController extends InvestorBaseController {

    // 假设你的分页查询逻辑
    @GetMapping("/products/list")
    public String products(
            // 接收前端分页参数，默认值1（避免null）
            @RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage,
            Model model
    ) {
        // 1. 校验参数（防止前端传非数字值）
        if (currentPage == null || currentPage < 1) {
            currentPage = 1;
        }

        // 2. 业务逻辑：查询分页数据（示例）
        // Page<Product> productPage = productService.queryPage(currentPage, pageSize);

        // 3. 注入分页参数到模板（关键：确保非null）
        model.addAttribute("currentPage", currentPage);
        // 其他分页参数（如总页数、每页条数等）按需注入
        model.addAttribute("totalPages", 5); // 模拟总页数，实际应从分页查询结果获取
        model.addAttribute("pageSize", 10); // 每页显示条数

        return "investor/products";
    }
}