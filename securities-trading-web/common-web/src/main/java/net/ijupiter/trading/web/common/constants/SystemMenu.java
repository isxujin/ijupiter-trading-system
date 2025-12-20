package net.ijupiter.trading.web.common.constants;

import lombok.Getter;
import net.ijupiter.trading.web.common.dtos.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class SystemMenu {
    @Getter
    private static final List<MenuItem> navigationItems = new ArrayList<MenuItem>();
    @Getter
    private static final List<MenuItem> sidebarItems = new ArrayList<MenuItem>();
    static {
        navigationItems.add(new MenuItem(1L, null, "首页", "/dashboard", "bi bi-house-door"));
        navigationItems.add(new MenuItem(3L, null, "账户", "/accounts/dashboard", "bi bi-person-vcard"));
        navigationItems.add(new MenuItem(4L, null, "交易", "/trades/dashboard", "bi bi-graph-up"));
        navigationItems.add(new MenuItem(5L, null, "系统", "/settings/dashboard", "bi bi-gear"));

        sidebarItems.add(new MenuItem(1L, 2L, "仪表盘", "/accounts/dashboard", "bi bi-speedometer2"));
        sidebarItems.add(new MenuItem(2L, 2L, "证券账户", "/accounts/products", "bi bi-box-seam"));
        sidebarItems.add(new MenuItem(3L, 2L, "资金账户", "/accounts/trades", "bi bi-cash-coin"));
        sidebarItems.add(new MenuItem(4L, 2L, "两融账户", "/accounts/accounts", "bi bi-people"));
        sidebarItems.add(new MenuItem(5L, 2L, "订单查询", "/accounts/orders", "bi bi-list-ul"));
        sidebarItems.add(new MenuItem(6L, 2L, "系统设置", "/accounts/settings", "bi bi-gear"));
    }
}
