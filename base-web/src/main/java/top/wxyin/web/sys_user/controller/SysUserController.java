package top.wxyin.web.sys_user.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import top.wxyin.result.ResultVo;
import top.wxyin.utils.ResultUtils;
import top.wxyin.web.sys_user.entity.SysUser;
import top.wxyin.web.sys_user.entity.SysUserPage;
import top.wxyin.web.sys_user.service.SysUserService;
import top.wxyin.web.sys_user_role.entity.SysUserRole;
import top.wxyin.web.sys_user_role.service.SysUserRoleService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequestMapping("/api/sysUser")
@RestController
@AllArgsConstructor
public class SysUserController {

    private final SysUserService sysUserService;

    private final SysUserRoleService sysUserRoleService;

    //新增
    @PostMapping
    @Operation(summary = " 新增⽤户 ")
    public ResultVo<?> add(@RequestBody SysUser sysUser) {
        sysUserService.saveUser(sysUser);
        return ResultUtils.success(" 新增成功!");
    }

    //编辑
    @PutMapping
    @Operation(summary = " 编辑⽤户 ")
    public ResultVo<?> edit(@RequestBody SysUser sysUser) {
        sysUserService.editUser(sysUser);
        return ResultUtils.success(" 编辑成功!");
    }

    //删除
    @Operation(summary = " 删除⽤户 ")
    @DeleteMapping("/{userId}")
    public ResultVo<?> delete(@PathVariable("userId") Long userId) {
        sysUserService.deleteUser(userId);
        return ResultUtils.success(" 删除成功!");
    }

    //列表
    @PostMapping("/list")
    @Operation(summary = "⽤户列表 ")
    public ResultVo<?> list(@RequestBody SysUserPage parm) {
        //构造分⻚对象
        IPage<SysUser> page = new Page<>(parm.getCurrentPage(), parm.getPageSize());
        //构造查询条件
        QueryWrapper<SysUser> query = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(parm.getNickName())) {
            query.lambda().like(SysUser::getNickName, parm.getNickName());
        }
        if (StringUtils.isNotEmpty(parm.getPhone())) {
            query.lambda().like(SysUser::getPhone, parm.getPhone());
        }
        query.lambda().orderByDesc(SysUser::getCreateTime);

        //查询列表
        IPage<SysUser> list = sysUserService.page(page, query);
        return ResultUtils.success(" 查询成功 ", list);
    }

    //根据⽤户id查询⽤户的⻆⾊
    @GetMapping("/getRoleList")
    @Operation(summary = " 根据⽤户 id 查询⽤户的⻆⾊ ")
    public ResultVo<?> getRoleList(Long userId) {
        QueryWrapper<SysUserRole> query = new QueryWrapper<>();
        query.lambda().eq(SysUserRole::getUserId, userId);
        List<SysUserRole> list = sysUserRoleService.list(query);
        //⻆⾊id
        List<Long> roleList = new ArrayList<>();
        Optional.ofNullable(list).orElse(new ArrayList<>())
                .forEach(item -> {
                    roleList.add(item.getRoleId());
                });
        return ResultUtils.success(" 查询成功 !", roleList);
    }

    //重置密码
    @PostMapping("/resetPassword")
    @Operation(summary = " 重置密码 ")
    public ResultVo<?> resetPassword(@RequestBody SysUser sysUser) {
        UpdateWrapper<SysUser> query = new UpdateWrapper<>();
        query.lambda().eq(SysUser::getUserId, sysUser.getUserId())
                .set(SysUser::getPassword, "666666");
        if (sysUserService.update(query)) {
            return ResultUtils.success(" 密码重置成功!");
        }
            return ResultUtils.error(" 密码重置失败!");
    }
}


