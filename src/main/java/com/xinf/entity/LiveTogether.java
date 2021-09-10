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
 * (LiveTogether)实体类
 *
 * @author makejava
 * @since 2021-09-06 08:40:45
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LiveTogether implements Serializable {
    private static final long serialVersionUID = -62181056026484847L;
    /**
    * id
    */
    @TableId(value = "live_together_id",type = IdType.AUTO)
    private Long liveTogetherId;
    /**
     * 文件名
     */
    private String name;
    /**
    * 直播状态：0下线 1上线
    */
    private Object flag;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}
