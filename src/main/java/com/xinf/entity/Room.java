package com.xinf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * (Room)实体类
 *
 * @author makejava
 * @since 2021-08-31 19:48:05
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room implements Serializable {
    private static final long serialVersionUID = -40995804493033969L;

    private Long roomId;

    private Long userId;
    /**
     * 直播间标题
     */
    private String roomTitle;
    /**
     * 直播间封面链接
     */
    private String roomPhoto;
    /**
     * 直播间公告
     */
    private String roomAnnouncement;
    /**
     * 直播间状态
     * 1.开播
     * 2.下播
     * 3. 封禁
     */
    private Integer roomStatus;
    /**
     * 直播间分类：1游戏 2.唱歌 3.跳舞 4.放映 5.整活
     */
    private Integer roomType;

    private Date createTime;

    private Date updateTime;

}
