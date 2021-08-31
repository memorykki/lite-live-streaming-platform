package cn.memorykk.ts.litelivestreamingplatform.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.memorykk.ts.litelivestreamingplatform.dao.CoinHistoryDao;
import cn.memorykk.ts.litelivestreamingplatform.entity.CoinHistory;
import cn.memorykk.ts.litelivestreamingplatform.service.CoinHistoryService;
import org.springframework.stereotype.Service;

/**
 * (CoinHistory)表服务实现类
 *
 * @author makejava
 * @since 2021-08-31 19:25:15
 */
@Service("coinHistoryService")
public class CoinHistoryServiceImpl extends ServiceImpl<CoinHistoryDao, CoinHistory> implements CoinHistoryService {

}
