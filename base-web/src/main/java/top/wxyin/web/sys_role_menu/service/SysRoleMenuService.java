package top.wxyin.web.sys_role_menu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.wxyin.web.sys_role_menu.entity.SysRoleMenu;
import top.wxyin.web.sys_role_menu.entity.SaveMenuParm;

public interface SysRoleMenuService extends IService<SysRoleMenu> {
    void saveRoleMenu(SaveMenuParm parm);
}