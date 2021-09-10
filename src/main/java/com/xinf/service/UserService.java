package com.xinf.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xinf.dto.UserInfo;
import com.xinf.entity.User;

import java.util.Map;

/**
 * (User)表服务接口
 *
 * @author makejava
 * @since 2021-08-31 19:25:18
 */
public interface UserService extends IService<User> {

    public UserInfo getUserInfo(long userId);

    public boolean registerUser(User user, int code, String auth);

    public Map<String, Object> login(String auth, String password);

    public void logout();

    public int sendEmail(String emailAddress);

    public int sendSms(String phone);
}
