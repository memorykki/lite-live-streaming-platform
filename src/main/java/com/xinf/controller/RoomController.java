package com.xinf.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinf.entity.Room;
import com.xinf.entity.UserWatchHistory;
import com.xinf.service.RoomService;
import com.xinf.service.UserWatchHistoryService;
import com.xinf.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * (Room)表控制层
 *
 * @author makejava
 * @since 2021-08-31 19:25:18
 */
@RestController
@RequestMapping("room")
public class RoomController extends ApiController {

    @Resource
    private RoomService roomService;
    @Resource
    private UserWatchHistoryService userWatchHistoryService;
    @Autowired
    RedisUtil redisUtil;

    @GetMapping("selectAllEfficient")
    public R selectAllEfficient(){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("room_status",1);
        queryWrapper.groupBy("room_type");
        List normalList = roomService.list(queryWrapper);

        Set<String> roomSet = redisUtil.zReverseRangeByScore("recommand", 0, 5);
        QueryWrapper<Room> recommandQueryWrapper = new QueryWrapper();
        recommandQueryWrapper.in("room_id",roomSet);
        List<Room> recommandList = roomService.list(recommandQueryWrapper);

        Map<String,List> map = new HashMap<>();
        map.put("normalList",normalList);
        map.put("recommandList",recommandList);
        return success(map);
    }

    /**
     * 分页查询所有数据
     *
     * @param page 分页对象
     * @param room 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<Room> page, Room room) {
        return success(this.roomService.page(page, new QueryWrapper<>(room)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param  roomId 房间号
     * @param  userId 用户id
     *
     * @return 单条数据
     */
    @RequestMapping("selectOne")
    public R selectOne(@RequestParam String roomId,
                       @RequestParam String userId) {
        QueryWrapper<UserWatchHistory> queryWrapper = new QueryWrapper();
        queryWrapper.eq("room_id",roomId);
        queryWrapper.eq("user_id",userId);
        if(userWatchHistoryService.getOne(queryWrapper)!=null){
            userWatchHistoryService.remove(queryWrapper);
        }
        userWatchHistoryService.save(new UserWatchHistory(Long.parseLong(userId),Long.parseLong(roomId)));
        return success(this.roomService.getById(roomId));
    }

    /**
     * 新增数据
     *
     * @param room 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody Room room) {
        return success(this.roomService.save(room));
    }

    /**
     * 修改数据
     *
     * @param room 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody Room room) {
        return success(this.roomService.updateById(room));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.roomService.removeByIds(idList));
    }
}
