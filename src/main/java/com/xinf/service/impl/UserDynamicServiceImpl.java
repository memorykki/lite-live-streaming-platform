package com.xinf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinf.dao.UserDynamicDao;
import com.xinf.entity.UserDynamic;
import com.xinf.service.UserDynamicService;
import org.springframework.stereotype.Service;

/**
 * (UserDynamic)表服务实现类
 *
 * @author makejava
 * @since 2021-08-31 19:25:19
 */
@Service("userDynamicService")
public class UserDynamicServiceImpl extends ServiceImpl<UserDynamicDao, UserDynamic> implements UserDynamicService {

}
