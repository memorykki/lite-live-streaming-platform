package cn.memorykk.ts.litelivestreamingplatform.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.memorykk.ts.litelivestreamingplatform.dao.UserWatchHistoryDao;
import cn.memorykk.ts.litelivestreamingplatform.entity.UserWatchHistory;
import cn.memorykk.ts.litelivestreamingplatform.service.UserWatchHistoryService;
import org.springframework.stereotype.Service;

/**
 * (UserWatchHistory)表服务实现类
 *
 * @author makejava
 * @since 2021-08-31 19:25:20
 */
@Service("userWatchHistoryService")
public class UserWatchHistoryServiceImpl extends ServiceImpl<UserWatchHistoryDao, UserWatchHistory> implements UserWatchHistoryService {

}
