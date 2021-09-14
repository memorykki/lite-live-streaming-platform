package com.xinf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinf.dao.CoinHistoryDao;
import com.xinf.entity.CoinHistory;
import com.xinf.entity.Role;
import com.xinf.entity.User;
import com.xinf.service.CoinHistoryService;
import com.xinf.service.RoleService;
import com.xinf.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * (CoinHistory)表服务实现类
 *
 * @author makejava
 * @since 2021-08-31 19:25:15
 */
@Service("coinHistoryService")
public class CoinHistoryServiceImpl extends ServiceImpl<CoinHistoryDao, CoinHistory> implements CoinHistoryService {

    @Resource
    UserService userService;

    @Resource
    RoleService roleService;

    @Override
    @Transactional
    public boolean save(CoinHistory entity) {
        String num = String.valueOf(Math.abs(entity.getChangeNum()));
        if (entity.getChangeNum() > 0) {
            userService.update(new UpdateWrapper<User>().eq("user_id", entity.getUserId())
                    .setSql("user_exist_coins = user_exist_coins + " + num)
                    .setSql("user_sum_coins = user_sum_coins + " + num));
        } else if (entity.getChangeNum() < 0){
            User user = userService.getOne(new QueryWrapper<User>().select("user_exist_coins", "role_id", "user_sum_coins")
                    .eq("user_id", entity.getUserId()));
            if (user.getUserExistCoins() < -entity.getChangeNum()) {
                throw new RuntimeException("剩余币不足以送礼");
            }
            UpdateWrapper<User> wrapper = new UpdateWrapper<User>().eq("user_id", entity.getUserId())
                    .setSql("user_exist_coins = user_exist_coins - " + num);
            if (user.getRoleId() < 10 || user.getRoleId() < 20) {
                Role role = roleService.getOne(new QueryWrapper<Role>().select("role_condition").eq("role_id", user.getRoleId() + 1));
                if (role.getRoleCondition() <= (user.getUserSumCoins() - user.getUserExistCoins() - entity.getChangeNum())) {
                    wrapper.set("role_id", user.getRoleId() + 1);
                }
            }
            userService.update(wrapper);
        }
        super.save(entity);
        return true;
    }
}
