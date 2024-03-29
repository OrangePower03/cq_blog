package com.example.cloudCommon.domain.eitity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 角色信息表(Role)表实体类
 *
 * @author makejava
 * @since 2023-11-20 20:58:32
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_role")
public class Role implements BaseEntity {
    //角色ID
    private Long id;
    //角色名称
    private String roleName;
    //角色权限字符串
    private String roleKey;
    //显示顺序
    private Integer roleSort;
    //角色状态（0正常 1停用）
    private String status;
    //删除标志（0代表存在 1代表删除）
    private String delFlag;
    //创建者
    @TableField(fill =  FieldFill.INSERT)
    private Long createBy;
    //创建时间
    @TableField(fill =  FieldFill.INSERT)
    private Date createTime;
    //更新者
    @TableField(fill =  FieldFill.INSERT_UPDATE)
    private Long updateBy;
    //更新时间
    @TableField(fill =  FieldFill.INSERT_UPDATE)
    private Date updateTime;
    //备注
    private String remark;
}

