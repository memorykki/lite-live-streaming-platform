package com.xinf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * (CoinHistory)实体类
 *
 * @author makejava
 * @since 2021-08-31 19:48:05
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoinHistory implements Serializable {
    private static final long serialVersionUID = -68743211578528953L;
    /**
     * 币流水id
     */

    @TableId(value = "serial_coin_id",type = IdType.AUTO)
    private Long serialCoinId;

    private Long userId;
    /**
     * 变动数额
     */
    private Integer changeNum;

    private String changeReason;

    private Date createTime;

}
