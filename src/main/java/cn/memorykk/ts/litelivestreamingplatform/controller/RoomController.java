package cn.memorykk.ts.litelivestreamingplatform.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.memorykk.ts.litelivestreamingplatform.entity.Room;
import cn.memorykk.ts.litelivestreamingplatform.service.RoomService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (Room)表控制层
 *
 * @author makejava
 * @since 2021-08-31 19:25:18
 */
@RestController
@RequestMapping("room")
public class RoomController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private RoomService roomService;

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
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.roomService.getById(id));
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
