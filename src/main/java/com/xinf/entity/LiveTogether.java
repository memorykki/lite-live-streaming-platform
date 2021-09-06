package com.xinf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.io.Serializable;

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
     * 推送路径
     */
    private String pushPath;
    /**
    * 直播状态：0下线 1上线
    */
    private Object flag;
    
    private Date updateTime;
    
    private Date createTime;

}