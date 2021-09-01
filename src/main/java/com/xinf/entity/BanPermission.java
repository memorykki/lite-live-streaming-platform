package com.xinf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (BanPermission)实体类
 *
 * @author makejava
 * @since 2021-08-31 19:48:04
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BanPermission implements Serializable {
    private static final long serialVersionUID = 312969470787569458L;

    private Long userId;
    /**
     * 举报表主键，对应举报原因等
     */
    private Long banId;

    private Integer livingPermission;

    private Integer chatPermission;

    private Integer sendGiftPermission;
}
