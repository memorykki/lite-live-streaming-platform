package cn.memorykk.ts.litelivestreamingplatform.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.memorykk.ts.litelivestreamingplatform.dao.UserLikeDynamicDao;
import cn.memorykk.ts.litelivestreamingplatform.entity.UserLikeDynamic;
import cn.memorykk.ts.litelivestreamingplatform.service.UserLikeDynamicService;
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
