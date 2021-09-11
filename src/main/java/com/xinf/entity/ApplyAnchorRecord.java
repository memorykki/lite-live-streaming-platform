package com.xinf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * (ApplyAnchorRecord)表实体类
 *
 * @author makejava
 * @since 2021-09-11 11:17:08
 */
@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplyAnchorRecord extends Model<ApplyAnchorRecord> {

    @TableId(value = "user_id",type = IdType.AUTO)
    private Long userId;
    //申请时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date applyTime;
    //申请原因
    private String applyReason;
    //申请证明
    private String applyEvidence;
    //申请结果：0待审核 1通过 2不通过
    private Integer applyResult;
}
