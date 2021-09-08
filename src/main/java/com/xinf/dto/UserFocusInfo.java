package com.xinf.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xinf
 * @since 2021/9/8 11:42
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserFocusInfo implements Serializable {
    UserInfo userInfo;
    private Date focusTime;
}
