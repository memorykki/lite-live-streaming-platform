package cn.memorykk.ts.litelivestreamingplatform.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.memorykk.ts.litelivestreamingplatform.dao.RoomDao;
import cn.memorykk.ts.litelivestreamingplatform.entity.Room;
import cn.memorykk.ts.litelivestreamingplatform.service.RoomService;
import org.springframework.stereotype.Service;

/**
 * (Room)表服务实现类
 *
 * @author makejava
 * @since 2021-08-31 19:25:18
 */
@Service("roomService")
public class RoomServiceImpl extends ServiceImpl<RoomDao, Room> implements RoomService {

}
