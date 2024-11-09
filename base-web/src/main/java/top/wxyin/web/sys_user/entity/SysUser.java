package top.wxyin.web.sys_user.entity;


import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("sys_user")
public class SysUser {
    @TableId(type = IdType.AUTO)
    private Long userId;
    private String username;
    private String password;
    private String phone;
    private String email;
    private String gender;
    private String isAdmin;
    //帐户是否过期(1未过期，0已过期)
    private boolean isAccountNonExpired = true;
    //帐户是否被锁定(1未锁定，0已锁定)
    private boolean isAccountNonLocked = true;
    //密码是否过期(1未过期，0已过期)
    private boolean isCredentialsNonExpired = true;
    //帐户是否可⽤(1可⽤，0不可⽤)
    private boolean isEnabled = true;
    private String nickName;
    @TableLogic
    private Integer deleted;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
