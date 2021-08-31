package cn.memorykk.ts.litelivestreamingplatform.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.memorykk.ts.litelivestreamingplatform.dao.VodDao;
import cn.memorykk.ts.litelivestreamingplatform.entity.Vod;
import cn.memorykk.ts.litelivestreamingplatform.service.VodService;
import org.springframework.stereotype.Service;

/**
 * (Vod)表服务实现类
 *
 * @author makejava
 * @since 2021-08-31 19:25:20
 */
@Service("vodService")
public class VodServiceImpl extends ServiceImpl<VodDao, Vod> implements VodService {

}
