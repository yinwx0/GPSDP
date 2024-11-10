package top.wxyin.web.sys_menu.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@TableName("sys_menu")
public class SysMenu {
    @TableId(type = IdType.AUTO)
    private Long menuId;
    private Long parentId;
    private String title;
    private String code;
    private String name;
    private String path;
    private String url;
    private String type;
    private String icon;
    private String parentName;
    private Integer orderNum;
    //下级菜单  children字段不属于sys_menu表，需要排除
    @TableField(exist = false)
    private List<SysMenu> children = new ArrayList<>();
    @TableField(exist = false)
    private Long value;
    @TableField(exist = false)
    private String label;
    @TableLogic
    private Integer deleted;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}