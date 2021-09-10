package com.xinf.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xinf.entity.Gift;
import com.xinf.util.cache.MybatisRedisCache;
import org.apache.ibatis.annotations.CacheNamespace;

/**
 * (Gift)表服务接口
 *
 * @author makejava
 * @since 2021-08-31 19:25:17
 */
@CacheNamespace(implementation= MybatisRedisCache.class,eviction=MybatisRedisCache.class)
public interface GiftService extends IService<Gift> {
}
