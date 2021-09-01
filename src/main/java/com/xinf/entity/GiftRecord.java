package com.xinf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 礼物流水(GiftRecord)实体类
 *
 * @author makejava
 * @since 2021-08-31 19:48:04
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GiftRecord implements Serializable {
    private static final long serialVersionUID = 516969341620802693L;

    private Long serialGiftId;

    private Integer userId;

    private Integer roomId;

    private Integer giftId;

    private Date createTime;
}
