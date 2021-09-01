package com.xinf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinf.dao.PlaybackDao;
import com.xinf.entity.Playback;
import com.xinf.service.PlaybackService;
import org.springframework.stereotype.Service;

/**
 * (Playback)表服务实现类
 *
 * @author makejava
 * @since 2021-08-31 19:25:17
 */
@Service("playbackService")
public class PlaybackServiceImpl extends ServiceImpl<PlaybackDao, Playback> implements PlaybackService {

}
