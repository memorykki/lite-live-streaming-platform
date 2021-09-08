package com.xinf.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * (UserWatchHistory)实体类
 *
 * @author makejava
 * @since 2021-08-31 19:48:05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserWatchHistory implements Serializable {
    private static final long serialVersionUID = -45101865706180853L;

    private Long userId;

    private Long roomId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date watchTime;

    public UserWatchHistory(long _userId, long _roomId) {
        userId = _userId;
        roomId = _roomId;
    }
}
