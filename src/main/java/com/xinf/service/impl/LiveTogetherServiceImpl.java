package com.xinf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinf.entity.LiveTogether;
import com.xinf.dao.LiveTogetherDao;
import com.xinf.service.LiveTogetherService;
import org.springframework.stereotype.Service;

/**
 * (LiveTogether)表服务实现类
 *
 * @author makejava
 * @since 2021-09-06 08:40:45
 */
@Service("liveTogetherService")
public class LiveTogetherServiceImpl  extends ServiceImpl<LiveTogetherDao, LiveTogether> implements LiveTogetherService {
}
