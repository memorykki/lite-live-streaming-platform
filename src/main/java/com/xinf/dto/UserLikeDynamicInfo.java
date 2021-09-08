package com.xinf.dto;

import com.xinf.entity.UserDynamic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xinf
 * @since 2021/9/8 22:16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLikeDynamicInfo implements Serializable {
    UserDynamic userDynamic;
    Date createTime;
}
