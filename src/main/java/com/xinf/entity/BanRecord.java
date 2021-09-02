package com.xinf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * (BanRecord)实体类
 *
 * @author makejava
 * @since 2021-08-31 19:48:04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BanRecord implements Serializable {
    private static final long serialVersionUID = 814809332635743158L;

    @TableId(value = "ban_id",type = IdType.AUTO)
    private Long banId;

    private Long userId;
    /**
     * 0 直播内容 1评论
     */
    private Integer type;
    /**
     * 具体举报原因
     */
    private String subType;
    /**
     * 1待审核 2已封禁 3.情况不符
     */
    private Integer status;
    /**
     * 证据截图链接或评论内容， 根据type决定
     */
    private String evidence;

    private Date createTime;

}
