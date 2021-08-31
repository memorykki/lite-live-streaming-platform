package cn.memorykk.ts.litelivestreamingplatform.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.memorykk.ts.litelivestreamingplatform.dao.GiftDao;
import cn.memorykk.ts.litelivestreamingplatform.entity.Gift;
import cn.memorykk.ts.litelivestreamingplatform.service.GiftService;
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
