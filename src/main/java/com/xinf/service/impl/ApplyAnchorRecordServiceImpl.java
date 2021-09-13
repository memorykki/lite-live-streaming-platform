package com.xinf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinf.dao.ApplyAnchorRecordDao;
import com.xinf.entity.ApplyAnchorRecord;
import com.xinf.entity.User;
import com.xinf.service.ApplyAnchorRecordService;
import com.xinf.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

/**
 * (ApplyAnchorRecord)表服务实现类
 *
 * @author makejava
 * @since 2021-09-11 11:17:08
 */
@Service("applyAnchorRecordService")
public class ApplyAnchorRecordServiceImpl extends ServiceImpl<ApplyAnchorRecordDao, ApplyAnchorRecord> implements ApplyAnchorRecordService {

    @Resource
    UserService userService;

    @Override
    @Transactional
    public boolean updateById(ApplyAnchorRecord entity) {
        if (entity.getApplyResult() == 1) {
            Map<String, Object> map = userService.getMap(new QueryWrapper<User>().select("role_id").eq("user_id", entity.getUserId()));
            if ((Integer)map.get("role_id") < 11) {
                userService.update(new UpdateWrapper<User>().eq("user_id", entity.getUserId()).set("role_id", 11));
            }
        }
        return super.updateById(entity);
    }
}
