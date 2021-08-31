package cn.memorykk.ts.litelivestreamingplatform.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * (Playback)实体类
 *
 * @author makejava
 * @since 2021-08-31 19:48:02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Playback implements Serializable {
    private static final long serialVersionUID = -22101039358307632L;

    private Long playbackId;

    private Long userId;

    private Date playbackTime;
}
