package com.xinf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinf.dao.GiftDao;
import com.xinf.entity.Gift;
import com.xinf.service.GiftService;
import org.springframework.stereotype.Service;

/**
 * (Gift)表服务实现类
 *
 * @author makejava
 * @since 2021-08-31 19:25:17
 */
@Service("giftService")
public class GiftServiceImpl extends ServiceImpl<GiftDao, Gift> implements GiftService {

}
