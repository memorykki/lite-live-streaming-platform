package com.xinf.entity;

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

    private Date watchTime;
}
