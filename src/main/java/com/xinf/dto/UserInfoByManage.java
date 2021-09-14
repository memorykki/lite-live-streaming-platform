package com.xinf.dto;

import com.xinf.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xinf
 * @since 2021/9/14 9:10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfoByManage implements Serializable {
    User user;
    // 1 普通 2 主播 3管理员
    Integer roleType;
    String roleName;
    String roleIdentification;
    Boolean isBaned;
}
