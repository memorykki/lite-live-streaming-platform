package com.xinf.dto;

import com.xinf.entity.Role;
import com.xinf.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xinf
 * @since 2021/9/1 16:18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo implements Serializable {
    private String token;
    private User user;
    private Role role;

    public UserInfo(User user, Role role) {
        this.user = user;
        this.role = role;
    }
}
