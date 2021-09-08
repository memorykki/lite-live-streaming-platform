package com.xinf.dto;

import com.xinf.entity.Role;
import com.xinf.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(description= "用户信息")
public class UserInfo implements Serializable {
    //@ApiModelProperty(value = "token")
    //private String token;
    @ApiModelProperty(value = "用户信息")
    private User user;
    @ApiModelProperty(value = "用户角色信息")
    private Role role;
}
