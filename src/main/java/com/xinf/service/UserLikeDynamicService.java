package com.xinf.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xinf.entity.UserLikeDynamic;

/**
 * 用户点赞记录(UserLikeDynamic)表服务接口
 *
 * @author makejava
 * @since 2021-08-31 19:25:19
 */
public interface UserLikeDynamicService extends IService<UserLikeDynamic> {
    boolean isLike(long userId, long dynamicId);

    boolean cancel(long userId, long dynamicId);
}
