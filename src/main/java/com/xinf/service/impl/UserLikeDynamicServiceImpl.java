package com.xinf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinf.dao.UserLikeDynamicDao;
import com.xinf.entity.UserLikeDynamic;
import com.xinf.service.UserLikeDynamicService;
import org.springframework.stereotype.Service;

/**
 * 用户点赞记录(UserLikeDynamic)表服务实现类
 *
 * @author makejava
 * @since 2021-08-31 19:25:19
 */
@Service("userLikeDynamicService")
public class UserLikeDynamicServiceImpl extends ServiceImpl<UserLikeDynamicDao, UserLikeDynamic> implements UserLikeDynamicService {

}
