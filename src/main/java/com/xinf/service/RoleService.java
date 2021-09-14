package com.xinf.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xinf.entity.Role;

import java.util.Map;

/**
 * (Role)表服务接口
 *
 * @author makejava
 * @since 2021-08-31 19:25:17
 */
public interface RoleService extends IService<Role> {
    Map<String, Object> getSimpleInfo(int roleId);
}
