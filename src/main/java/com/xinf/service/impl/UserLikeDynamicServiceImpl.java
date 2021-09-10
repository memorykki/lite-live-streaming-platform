package com.xinf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinf.dao.UserLikeDynamicDao;
import com.xinf.entity.UserDynamic;
import com.xinf.entity.UserLikeDynamic;
import com.xinf.service.UserDynamicService;
import com.xinf.service.UserLikeDynamicService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 用户点赞记录(UserLikeDynamic)表服务实现类
 *
 * @author makejava
 * @since 2021-08-31 19:25:19
 */
@Service("userLikeDynamicService")
public class UserLikeDynamicServiceImpl extends ServiceImpl<UserLikeDynamicDao, UserLikeDynamic> implements UserLikeDynamicService {

    @Resource
    private UserDynamicService userDynamicService;

    @Override
    public boolean isLike(long userId, long dynamicId) {
        return count(new QueryWrapper<UserLikeDynamic>().eq("user_id", userId).eq("dynamic_id", dynamicId)) > 0;
    }

    @Override
    @Transactional
    public boolean cancel(long userId, long dynamicId) {
        userDynamicService.update(new UpdateWrapper<UserDynamic>().eq("dynamic_id", dynamicId)
                .setSql("dynamic_like = dynamic_like - 1"));
        return remove(new QueryWrapper<UserLikeDynamic>().eq("dynamic_id", dynamicId).eq("user_id", userId));
    }

    @Override
    @Transactional
    public boolean save(UserLikeDynamic entity) {
        userDynamicService.update(new UpdateWrapper<UserDynamic>().eq("dynamic_id", entity.getDynamicId())
                .setSql("dynamic_like = dynamic_like + 1"));
        return super.save(entity);
    }
}
