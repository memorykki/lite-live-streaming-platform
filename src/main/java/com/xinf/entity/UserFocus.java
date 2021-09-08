package com.xinf.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * (UserFocus)实体类
 *
 * @author makejava
 * @since 2021-08-31 19:48:05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserFocus implements Serializable {
    private static final long serialVersionUID = -81887106383629794L;
    private Long focusUserId;

    private Long focusedUserId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

}
