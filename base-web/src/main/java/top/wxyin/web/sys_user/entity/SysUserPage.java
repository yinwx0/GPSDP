package top.wxyin.web.sys_user.entity;


import lombok.Data;

@Data
public class SysUserPage {
    private String phone;
    private String nickName;
    //当前第⼏⻚
    private Long currentPage;
    //没有查询的条数
    private Long pageSize;
}
