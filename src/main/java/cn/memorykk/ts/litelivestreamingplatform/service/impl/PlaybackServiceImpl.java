package cn.memorykk.ts.litelivestreamingplatform.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.memorykk.ts.litelivestreamingplatform.dao.PlaybackDao;
import cn.memorykk.ts.litelivestreamingplatform.entity.Playback;
import cn.memorykk.ts.litelivestreamingplatform.service.PlaybackService;
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
