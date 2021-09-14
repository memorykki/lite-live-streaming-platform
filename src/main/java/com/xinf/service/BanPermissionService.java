package com.xinf.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xinf.entity.BanPermission;

/**
 * (BanPermission)表服务接口
 *
 * @author makejava
 * @since 2021-08-31 19:25:13
 */
public interface BanPermissionService extends IService<BanPermission> {
    boolean checkIsBaned(long userId);
}
