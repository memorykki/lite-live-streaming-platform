package com.xinf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinf.dao.BanRecordDao;
import com.xinf.entity.ApplyAnchorRecord;
import com.xinf.dao.ApplyAnchorRecordDao;
import com.xinf.entity.BanRecord;
import com.xinf.service.ApplyAnchorRecordService;
import com.xinf.service.BanRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (ApplyAnchorRecord)表服务实现类
 *
 * @author makejava
 * @since 2021-09-06 08:40:50
 */
@Service("applyAnchorRecordService")
public class ApplyAnchorRecordServiceImpl extends ServiceImpl<ApplyAnchorRecordDao, ApplyAnchorRecord> implements ApplyAnchorRecordService {
}