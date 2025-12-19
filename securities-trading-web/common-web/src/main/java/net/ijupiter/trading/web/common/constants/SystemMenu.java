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
        navigationItems.add(new MenuItem(1, null, "首页", "/dashboard", "bi bi-house-door", true));
        navigationItems.add(new MenuItem(3, null, "账户", "/accounts/dashboard", "bi bi-person-vcard"));
        navigationItems.add(new MenuItem(4, null, "交易", "/trades/dashboard", "bi bi-graph-up"));
        navigationItems.add(new MenuItem(5, null, "系统", "/settings/dashboard", "bi bi-gear"));

        sidebarItems.add(new MenuItem(1, 2, "仪表盘", "/accounts/dashboard", "bi bi-speedometer2", true));
        sidebarItems.add(new MenuItem(2, 2, "证券账户", "/accounts/products", "bi bi-box-seam"));
        sidebarItems.add(new MenuItem(3, 2, "资金账户", "/accounts/trades", "bi bi-cash-coin"));
        sidebarItems.add(new MenuItem(4, 2, "两融账户", "/accounts/accounts", "bi bi-people"));
        sidebarItems.add(new MenuItem(5, 2, "订单查询", "/accounts/orders", "bi bi-list-ul"));
        sidebarItems.add(new MenuItem(6, 2, "系统设置", "/accounts/settings", "bi bi-gear"));
    }
}
