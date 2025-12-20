package net.ijupiter.trading.common.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 菜单项声明
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuItem extends BaseDTO<MenuItem> {
    /**
     * 父菜单项标识
     */
    protected Long parentId;
    /**
     * 菜单项名称
     */
    protected String name;
    /**
     * 菜单项URL
     */
    protected String url;
    /**
     * 菜单项图标
     */
    protected String icon;
}
