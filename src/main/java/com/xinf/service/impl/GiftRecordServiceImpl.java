package com.xinf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinf.dao.GiftRecordDao;
import com.xinf.entity.CoinHistory;
import com.xinf.entity.Gift;
import com.xinf.entity.GiftRecord;
import com.xinf.service.CoinHistoryService;
import com.xinf.service.GiftRecordService;
import com.xinf.service.GiftService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 礼物流水(GiftRecord)表服务实现类
 *
 * @author makejava
 * @since 2021-08-31 19:25:16
 */
@Service("giftRecordService")
public class GiftRecordServiceImpl extends ServiceImpl<GiftRecordDao, GiftRecord> implements GiftRecordService {

    @Resource
    private CoinHistoryService coinHistoryService;
    @Resource
    private GiftService giftService;

    @Override
    @Transactional
    public boolean save(GiftRecord giftRecord, String reason) {
        Map<String, Object> map = giftService.getMap(new QueryWrapper<Gift>().select("gift_value").eq("gift_id", giftRecord.getGiftId()));
        CoinHistory coinHistory = new CoinHistory();
        coinHistory.setUserId(giftRecord.getUserId());
        coinHistory.setChangeNum((Integer)map.get("gift_value"));
        coinHistory.setChangeReason(reason);
        coinHistoryService.save(coinHistory);
        return save(giftRecord);
    }
}
