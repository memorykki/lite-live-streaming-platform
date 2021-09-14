package com.xinf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinf.dao.BanPermissionDao;
import com.xinf.entity.BanPermission;
import com.xinf.service.BanPermissionService;
import org.springframework.stereotype.Service;

/**
 * (BanPermission)表服务实现类
 *
 * @author makejava
 * @since 2021-08-31 19:25:14
 */
@Service("banPermissionService")
public class BanPermissionServiceImpl extends ServiceImpl<BanPermissionDao, BanPermission> implements BanPermissionService {

    @Override
    public boolean checkIsBaned(long userId) {
        return count(new QueryWrapper<BanPermission>().eq("user_id", userId)) > 0;
    }
}
