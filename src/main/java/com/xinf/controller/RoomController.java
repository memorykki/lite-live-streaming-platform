package com.xinf.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinf.dto.RoomInfo;
import com.xinf.entity.Room;
import com.xinf.entity.UserWatchHistory;
import com.xinf.service.RoomService;
import com.xinf.service.UserFocusService;
import com.xinf.service.UserService;
import com.xinf.service.UserWatchHistoryService;
import com.xinf.util.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * (Room)表控制层
 *
 * @author makejava
 * @since 2021-08-31 19:25:18
 */
@Api(value = "房间controller", tags = { "房间访问接口" })
@RestController
@RequestMapping("room")
public class RoomController extends ApiController {

    @Resource
    private UserService userService;
    @Resource
    private RoomService roomService;
    @Resource
    private UserWatchHistoryService userWatchHistoryService;
    @Resource
    private UserFocusService userFocusService;
    @Autowired
    private RedisUtil redisUtil;

    /**
     *  获取直播排行
     * @return
     */
    @GetMapping("selectRankingList")
    @ApiOperation(value = "获取排行榜中的房间信息")
    public R selectRankingList(){

        Set<String> roomSet = redisUtil.zReverseRangeByScore("recommand", 0, 5);
        if (roomSet.isEmpty()) {
            return failed("房间热度没有！");
        }
        QueryWrapper<Room> recommandQueryWrapper = new QueryWrapper();
        recommandQueryWrapper.select("room_id", "user_id", "room_title", "room_photo", "room_status", "room_type");
        recommandQueryWrapper.in("room_id", roomSet);
        List<Room> roomList = roomService.list(recommandQueryWrapper);
        return success(roomList);
    }

    /**
     *  获取类别下直播房间信息, 默认分页大小为10，第一页
     * @param classify
     * @param pageSize
     * @param pageCurrent
     * @return
     */
    @GetMapping("selectClassifyList")
    @ApiOperation(value = "获取某一类别下的房间信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "classify", value = "类别编号， e.q.游戏-1")})
    public R selectClassifyList(int classify,
             @RequestParam(defaultValue = "10") long pageSize, @RequestParam(defaultValue = "1") long pageCurrent) {
        Page page = new Page(pageCurrent, pageSize, true);
        QueryWrapper<Room> queryWrapper = new QueryWrapper();
        queryWrapper.select("room_id", "user_id", "room_title", "room_photo", "room_status", "room_type");
        queryWrapper.eq("room_status",1).eq("room_type", classify);
        Page roomList = roomService.page(page, queryWrapper);
        return success(roomList);
    }


    /**
     *  点击直播间后获取信息，如房间信息、直播信息、房间热度。 动态另外获取
     * @param roomId
     * @param userId
     * @return
     */
    @GetMapping("getRoomInfo")
    @ApiOperation(value = "点击直播间后获取详细信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "roomId", value = "房间标识"),
                @ApiImplicitParam(name = "userId", value = "当前用户当前标识"),
                @ApiImplicitParam(name = "anchorId", value = "主播身份标识")})
    public R getRoomInfo(long roomId, long anchorId, long userId) {
        RoomInfo roomInfo = new RoomInfo();
        // 房间信息
        QueryWrapper<Room> roomQueryWrapper = new QueryWrapper();
        roomQueryWrapper.select("room_announcement", "create_time").eq("room_id", roomId);
        Room room = roomService.getOne(roomQueryWrapper);
        room.setRoomId(roomId);
        roomInfo.setRoom(room);

        // 热度
        roomInfo.setHot(redisUtil.zScore("recommand", roomId).longValue());

        // 主播信息
        roomInfo.setUser(userService.getUserInfo(anchorId));

        roomInfo.setFocus(userFocusService.isFocus(userId, anchorId));
        return success(roomInfo);
    }

    /**
     * 分页查询所有数据
     *
     * @param room 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Room room,
                       @RequestParam(defaultValue = "10") long pageSize, @RequestParam(defaultValue = "1") long pageCurrent) {
        Page page = new Page(pageCurrent, pageSize, true);
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
