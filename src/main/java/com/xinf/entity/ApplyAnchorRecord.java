package com.xinf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * (ApplyAnchorRecord)实体类
 *
 * @author makejava
 * @since 2021-09-06 08:40:50
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplyAnchorRecord implements Serializable {
    private static final long serialVersionUID = -95633030193977674L;
    @TableId(value = "user_id",type = IdType.AUTO)
    private Long userId;
    /**
    * 申请时间
    */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date applyTime;
    /**
    * 申请原因
    */
    private String applyReason;
    /**
    * 申请证明
    */
    private String applyEvidence;
    /**
    * 申请结果：0待审核 1通过 2不通过
    */
    private Object applyResult;


}
