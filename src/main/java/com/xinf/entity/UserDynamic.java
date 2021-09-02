package com.xinf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * (UserDynamic)实体类
 *
 * @author makejava
 * @since 2021-08-31 19:48:05
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDynamic implements Serializable {
    private static final long serialVersionUID = -54017594261077080L;

    @TableId(value = "dynamic_id",type = IdType.AUTO)
    private Long dynamicId;

    private Long userId;
    /**
     * 类型：1文字 2.图片 3.视频
     */
    private Integer dynamicType;

    private String dynamicContent;
    /**
     * 点赞数
     */
    private Integer dynamicLike;

    private Date createTime;

    private Date updateTime;
}
