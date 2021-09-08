package com.xinf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinf.dao.UserFocusDao;
import com.xinf.entity.User;
import com.xinf.entity.UserFocus;
import com.xinf.service.UserFocusService;
import com.xinf.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * (UserFocus)表服务实现类
 *
 * @author makejava
 * @since 2021-08-31 19:25:18
 */
@Service("userFocusService")
public class UserFocusServiceImpl extends ServiceImpl<UserFocusDao, UserFocus> implements UserFocusService {

    @Resource
    private UserService userService;

    @Override
    @Transactional
    public void add(UserFocus userFocus) {
        save(userFocus);
        userService.update(new UpdateWrapper<User>().eq("user_id", userFocus.getFocusedUserId())
                .setSql("user_fans_count = user_fans_count + 1"));
    }

    @Override
    @Transactional
    public void remove(UserFocus userFocus) {
        super.remove(new QueryWrapper<UserFocus>(userFocus));
        userService.update(new UpdateWrapper<User>().eq("user_id", userFocus.getFocusedUserId())
                .setSql("user_fans_count = user_fans_count - 1"));
    }

    @Override
    public boolean isFocus(long focusId, long focusedId) {
        UserFocus userFocus = new UserFocus(focusId, focusedId);
        return count(new QueryWrapper<>(userFocus)) > 0;
    }
}
