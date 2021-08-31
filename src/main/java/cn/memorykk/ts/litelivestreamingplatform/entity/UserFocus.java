package cn.memorykk.ts.litelivestreamingplatform.entity;

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

    private Date createTime;

    private Date updateTime;

}
