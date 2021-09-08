package com.xinf.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户点赞记录(UserLikeDynamic)实体类
 *
 * @author makejava
 * @since 2021-08-31 19:48:05
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLikeDynamic implements Serializable {
    private static final long serialVersionUID = -11058590489695148L;

    private Long userId;

    private Long dynamicId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    public UserLikeDynamic(Long userId, Long dynamicId) {
        this.userId = userId;
        this.dynamicId = dynamicId;
    }
}
