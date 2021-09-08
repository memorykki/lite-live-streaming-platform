package com.xinf.service.impl;

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
        User user = new User();
        user.setUserId(userFocus.getFocusedUserId());
        userService.update(new UpdateWrapper<User>().eq("user_id", userFocus.getFocusedUserId()).setSql("user_fans_count = user_fans_count + 1"));
        save(userFocus);
    }
}
