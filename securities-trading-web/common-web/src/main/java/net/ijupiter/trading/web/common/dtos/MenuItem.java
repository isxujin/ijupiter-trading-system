package net.ijupiter.trading.web.common.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 菜单项声明
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuItem {
    /**
     * 菜单项标识
     */
    private Integer id;
    /**
     * 父菜单项标识
     */
    private Integer parentId;
    /**
     * 菜单项名称
     */
    private String name;
    /**
     * 菜单项URL
     */
    private String url;
    /**
     * 菜单项图标
     */
    private String icon;
    /**
     * 是否激活
     */
    private boolean active;

    public MenuItem(Integer id,Integer parentId, String name, String url, String icon) {
        this(id,parentId, name, url, icon, false);
    }

}
