package com.xinf.dto;

import com.xinf.entity.Room;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author xinf
 * @since 2021/9/7 15:16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description= "详细房间信息")
public class RoomInfo implements Serializable {
    // 房间信息：id、标题、类型
    @ApiModelProperty("房间信息")
    private Room room;
    // 直播间房主信息
    @ApiModelProperty("直播间房主信息")
    private UserInfo user;
    // 房间热度
    @ApiModelProperty("房间热度")
    private long hot;

    @ApiModelProperty("是否关注主播")
    private boolean isFocus;

    @ApiModelProperty("房间内送礼排行")
    private List<UserInfo> userRankList;
}
