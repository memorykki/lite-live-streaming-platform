package com.xinf.dto;

import com.xinf.entity.Room;
import com.xinf.entity.UserDynamic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author xinf
 * @since 2021/9/7 15:16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomInfo {
    // 房间信息：id、标题、类型
    private Room room;
    // 直播间房主信息
    private UserInfo user;
    // 房间热度
    private long hot;
    // 房主动态
    private List<UserDynamic> userDynamics;
}
