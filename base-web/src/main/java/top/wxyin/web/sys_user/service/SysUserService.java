package top.wxyin.web.sys_user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.wxyin.web.sys_user.entity.SysUser;

public interface SysUserService extends IService<SysUser> {

    //新增
    void saveUser(SysUser sysUser);
    //编辑
    void editUser(SysUser sysUser);
    //删除⽤户
    void deleteUser(Long userId);

}
