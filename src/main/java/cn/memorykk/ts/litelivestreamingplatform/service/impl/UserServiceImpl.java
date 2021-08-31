package cn.memorykk.ts.litelivestreamingplatform.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.memorykk.ts.litelivestreamingplatform.dao.UserDao;
import cn.memorykk.ts.litelivestreamingplatform.entity.User;
import cn.memorykk.ts.litelivestreamingplatform.service.UserService;
import org.springframework.stereotype.Service;

/**
 * (User)表服务实现类
 *
 * @author makejava
 * @since 2021-08-31 19:25:18
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

}
