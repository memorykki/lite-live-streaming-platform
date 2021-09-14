package com.xinf.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xinf
 * @since 2021/9/14 14:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfoForRoomRank implements Serializable {
    Long userId;
    String userName;
    String userHeadPhoto;
    Integer roleId;
    String roleName;
    String roleIdentification;
    Integer sendGiftValue;
}
