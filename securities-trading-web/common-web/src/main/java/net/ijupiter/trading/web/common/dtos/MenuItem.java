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
public class MenuItem extends net.ijupiter.trading.common.dtos.MenuItem {
    /**
     * 是否激活
     */
    private Boolean active;

    public MenuItem(Long id,Long parentId, String name, String url, String icon) {
        super();
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.url = url;
        this.icon = icon;
        this.active = false;
    }

}
