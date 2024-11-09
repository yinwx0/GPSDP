package top.wxyin.web.sys_user.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.wxyin.web.sys_user.entity.SysUser;
import top.wxyin.web.sys_user.mapper.SysUserMapper;
import top.wxyin.web.sys_user.service.SysUserService;
import top.wxyin.web.sys_user_role.entity.SysUserRole;
import top.wxyin.web.sys_user_role.service.SysUserRoleService;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private final SysUserRoleService sysUserRoleService;

    @Transactional
    @Override
    public void saveUser(SysUser sysUser) {
        int i = this.baseMapper.insert(sysUser);
        //新增⽤户成功后，设置⽤户的⻆⾊
        if (i > 0) {
            //把前端逗号分隔的字符串转为数组
            String[] split = sysUser.getRoleId().split(",");
            if (split.length > 0) {
                List<SysUserRole> roles = new ArrayList<>();
                for (String s : split) {
                    SysUserRole userRole = new SysUserRole();
                    userRole.setUserId(sysUser.getUserId());
                    userRole.setRoleId(Long.parseLong(s));
                    roles.add(userRole);
                }
                //保存到⽤户⻆⾊表
                sysUserRoleService.saveBatch(roles);
            }
        }
    }

    @Transactional
    @Override
    public void editUser(SysUser sysUser) {
        int i = this.baseMapper.updateById(sysUser);
        //修改成功后，设置用户的角色
        if (i > 0) {
            //把前端逗号分隔的字符串转为数组
            String[] split = sysUser.getRoleId().split(",");
            //删除用户原来的角色
            QueryWrapper<SysUserRole> query = new QueryWrapper<>();
            query.lambda().eq(SysUserRole::getUserId, sysUser.getUserId());
            sysUserRoleService.remove(query);
            //重新插入
            if (split.length > 0) {
                List<SysUserRole> roles = new ArrayList<>();
                for (String s : split) {
                    SysUserRole userRole = new SysUserRole();
                    userRole.setUserId(sysUser.getUserId());
                    userRole.setRoleId(Long.parseLong(s));
                    roles.add(userRole);
                }
                //保存到用户角色表
                sysUserRoleService.saveBatch(roles);
            }
        }
    }


    @Transactional
    @Override
    public void deleteUser(Long userId) {
        int i = this.baseMapper.deleteById(userId);
        if (i > 0) {
            //删除⽤户原来的⻆⾊
            QueryWrapper<SysUserRole> query = new QueryWrapper<>();
            query.lambda().eq(SysUserRole::getUserId, userId);
            sysUserRoleService.remove(query);
        }
    }
}
