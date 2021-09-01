package com.xinf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinf.dao.BanRecordDao;
import com.xinf.entity.BanRecord;
import com.xinf.service.BanRecordService;
import org.springframework.stereotype.Service;

/**
 * (BanRecord)表服务实现类
 *
 * @author makejava
 * @since 2021-08-31 19:25:16
 */
@Service("banRecordService")
public class BanRecordServiceImpl extends ServiceImpl<BanRecordDao, BanRecord> implements BanRecordService {

}
