package cn.memorykk.ts.litelivestreamingplatform.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (Vod)实体类
 *
 * @author makejava
 * @since 2021-08-31 19:48:05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vod implements Serializable {
    private static final long serialVersionUID = 662118058812299643L;
    /**
     * 影片id， 通过拼接获取影片link
     */
    private Long vodId;

    private String vodName;

    private String vodIntroduction;

}
