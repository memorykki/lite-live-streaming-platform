package com.xinf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinf.dao.GiftRecordDao;
import com.xinf.entity.GiftRecord;
import com.xinf.service.GiftRecordService;
import org.springframework.stereotype.Service;

/**
 * 礼物流水(GiftRecord)表服务实现类
 *
 * @author makejava
 * @since 2021-08-31 19:25:16
 */
@Service("giftRecordService")
public class GiftRecordServiceImpl extends ServiceImpl<GiftRecordDao, GiftRecord> implements GiftRecordService {

}
