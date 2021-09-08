package com.xinf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * (User)实体类
 *
 * @author makejava
 * @since 2021-08-31 19:48:05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = 992236825819524628L;

    @TableId(value = "user_id",type = IdType.AUTO)
    private Long userId;
    /**
     * 昵称
     */
    private String userName;
    /**
     * 邮箱
     */
    private String userEmail;
    /**
     * 电话
     */
    private String userPhone;
    /**
     * 密码
     */
    private String userPasswd;
    /**
     * 头像链接
     */
    private String userHeadPhoto;
    /**
     * 用户状态：1正常, 2删除
     */
    private Integer userStatus;
    /**
     * 粉丝数量
     */
    private Integer userFansCount;
    /**
     * 活力币剩余数量
     */
    private Integer userExistCoins;
    /**
     * 活力币总数
     */
    private Integer userSumCoins;
    /**
     * 开设房间
     */
    private Long roomId;
    /**
     * 用户身份
     */
    private Integer roleId;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

}
