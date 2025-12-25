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
        navigationItems.add(new MenuItem(1L, null, "首页", "/dashboard", "bi bi-house-door",true));
        navigationItems.add(new MenuItem(2L, null, "客户", "/customer/dashboard", "bi bi-person-vcard"));
        navigationItems.add(new MenuItem(3L, null, "资金", "/funding/dashboard", "bi bi-cash-coin"));
        navigationItems.add(new MenuItem(4L, null, "证券", "/securities/dashboard", "bi bi-journals"));
        navigationItems.add(new MenuItem(5L, null, "交易", "/trading-engine/dashboard", "bi bi-graph-up"));
        navigationItems.add(new MenuItem(6L, null, "清算", "/settlement/dashboard", "bi bi-android2"));
        navigationItems.add(new MenuItem(7L, null, "系统", "/system/dashboard", "bi bi-gear-wide-connected"));
        navigationItems.add(new MenuItem(8L, null, "综合查询", "/query/dashboard", "bi bi-eyeglasses"));

        sidebarItems.add(new MenuItem(11L, 1L, "仪表盘", "/", "bi bi-speedometer2",true));
        sidebarItems.add(new MenuItem(21L, 2L, "仪表盘", "/customer/dashboard", "bi bi-speedometer2",true));
        sidebarItems.add(new MenuItem(22L, 2L, "客户账户", "/customer/account", "bi bi-piggy-bank"));
        sidebarItems.add(new MenuItem(31L, 3L, "仪表盘", "/funding/dashboard", "bi bi-speedometer2",true));
        sidebarItems.add(new MenuItem(32L, 3L, "资金账户", "/funding/account", "bi bi-piggy-bank"));
        sidebarItems.add(new MenuItem(33L, 3L, "出入金", "/funding/transfer", "bi bi-arrow-left-right"));
        sidebarItems.add(new MenuItem(34L, 3L, "流水查询", "/funding/transfer", "bi bi-receipt"));
        sidebarItems.add(new MenuItem(41L, 4L, "仪表盘", "/securities/dashboard", "bi bi-speedometer2",true));
        sidebarItems.add(new MenuItem(42L, 4L, "证券账户", "/securities/account", "bi bi-piggy-bank"));
        sidebarItems.add(new MenuItem(43L, 4L, "转托管", "/securities/transfer", "bi bi-arrow-left-right"));
        sidebarItems.add(new MenuItem(44L, 4L, "流水查询", "/securities/transfer", "bi bi-receipt"));
        sidebarItems.add(new MenuItem(51L, 5L, "仪表盘", "/trading-engine/dashboard", "bi bi-speedometer2",true));
        sidebarItems.add(new MenuItem(52L, 5L, "买入", "/trading-engine/buy", "bi bi-box-arrow-in-right"));
        sidebarItems.add(new MenuItem(52L, 5L, "卖出", "/trading-engine/sell", "bi bi-box-arrow-right"));
        sidebarItems.add(new MenuItem(61L, 6L, "仪表盘", "/settlement/dashboard", "bi bi-speedometer2",true));
        sidebarItems.add(new MenuItem(52L, 5L, "清算", "/trading-engine/sell", "bi bi-android"));
        sidebarItems.add(new MenuItem(71L, 7L, "仪表盘", "/system/dashboard", "bi bi-speedometer2",true));
        sidebarItems.add(new MenuItem(72L, 7L, "用户管理", "/system/user/list", "bi bi-person-gear"));
        sidebarItems.add(new MenuItem(73L, 7L, "角色管理", "/system/role/list", "bi bi-person-rolodex"));
        sidebarItems.add(new MenuItem(74L, 7L, "权限管理", "/system/permission/list", "bi bi-menu-button-wide-fill"));
        sidebarItems.add(new MenuItem(75L, 7L, "字典管理", "/system/dictionary/list", "bi bi-book"));
        sidebarItems.add(new MenuItem(76L, 7L, "参数管理", "/system/parameter/list", "bi bi-journal-text"));
        sidebarItems.add(new MenuItem(81L, 8L, "仪表盘", "/query/dashboard", "bi bi-speedometer2",true));
    }
}
