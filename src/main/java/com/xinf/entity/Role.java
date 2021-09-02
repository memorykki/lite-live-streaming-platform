package com.xinf.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (Role)实体类
 *
 * @author makejava
 * @since 2021-08-31 19:48:04
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role implements Serializable {
    private static final long serialVersionUID = 350741871646717036L;
    /**
     * 身份id：0-10 观众 11-20 主播 999 管理员
     */
    @TableId(value = "role_id")
    private Integer roleId;
    /**
     * 身份名称
     */
    private String roleName;
    /**
     * 角色条件：达到活力币流动线
     */
    private Integer roleCondition;
    /**
     * 角色标识小图片链接
     */
    private String roleIdentification;
    /**
     * 开播权限, 1正常 0无权限 ，下同
     */
    private Integer livingPermission;
    /**
     * 聊天权限
     */
    private Integer chatPermission;
    /**
     * 发礼权限
     */
    private Integer sendGiftPermission;

}
