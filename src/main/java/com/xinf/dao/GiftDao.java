package com.xinf.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinf.entity.Gift;
import com.xinf.util.cache.MybatisRedisCache;
import org.apache.ibatis.annotations.CacheNamespace;

/**
 * (Gift)表数据库访问层
 *
 * @author makejava
 * @since 2021-08-31 19:25:17
 */
@CacheNamespace(implementation= MybatisRedisCache.class,eviction=MybatisRedisCache.class)
public interface GiftDao extends BaseMapper<Gift> {

}
