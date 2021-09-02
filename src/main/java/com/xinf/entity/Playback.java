package com.xinf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * (Playback)实体类
 *
 * @author makejava
 * @since 2021-08-31 19:48:02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Playback implements Serializable {
    private static final long serialVersionUID = -22101039358307632L;

    @TableId(value = "playback_id",type = IdType.AUTO)
    private Long playbackId;

    private Long userId;

    private Date playbackTime;
}
