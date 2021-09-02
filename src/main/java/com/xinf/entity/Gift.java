package com.xinf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (Gift)实体类
 *
 * @author makejava
 * @since 2021-08-31 19:48:05
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Gift implements Serializable {
    private static final long serialVersionUID = 160016009972492047L;

    @TableId(value = "gift_id",type = IdType.AUTO)
    private Integer giftId;

    private String giftName;

    private String giftPhoto;

    private Integer giftValue;
    /**
     * 礼物状态：1.开启，2.关闭
     */
    private Integer giftStatus;
}
