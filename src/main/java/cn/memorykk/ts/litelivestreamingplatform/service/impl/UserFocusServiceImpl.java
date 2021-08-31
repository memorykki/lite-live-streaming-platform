package cn.memorykk.ts.litelivestreamingplatform.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.memorykk.ts.litelivestreamingplatform.dao.UserFocusDao;
import cn.memorykk.ts.litelivestreamingplatform.entity.UserFocus;
import cn.memorykk.ts.litelivestreamingplatform.service.UserFocusService;
import org.springframework.stereotype.Service;

/**
 * (UserFocus)表服务实现类
 *
 * @author makejava
 * @since 2021-08-31 19:25:18
 */
@Service("userFocusService")
public class UserFocusServiceImpl extends ServiceImpl<UserFocusDao, UserFocus> implements UserFocusService {

}
