package com.xinf.dto;

import com.xinf.entity.BanPermission;
import com.xinf.entity.BanRecord;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xinf
 * @since 2021/9/8 21:54
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BanInfo implements Serializable {
    BanPermission banPermission;
    BanRecord banRecord;
}
