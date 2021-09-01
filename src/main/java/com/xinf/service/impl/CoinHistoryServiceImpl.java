package com.xinf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinf.dao.CoinHistoryDao;
import com.xinf.entity.CoinHistory;
import com.xinf.service.CoinHistoryService;
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
