package com.xinf.util;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.IntFunction;

/**
 * @author xinf
 * @since 2021/9/8 14:51
 */
@Slf4j
public class BeanUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        BeanUtil.applicationContext = applicationContext;
    }

    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    public static<T> Object getBean(String name, Class<T> beanClass) {
        return applicationContext.getBean(name, beanClass);
    }

    public static<T, E> Page<T> transPage(Page<E> page, T[] res) {
        Page<T> ans = new Page<>();
        ans.setTotal(page.getTotal());
        ans.setCurrent(page.getCurrent());
        ans.setSize(page.getSize());
        ans.setRecords(Arrays.asList(res));
        return ans;
    }

    public static<T, E> Page<T> transPage(Page<E> page, Function<E, T> fun, IntFunction<T[]> generator) {
        T[] res = page.getRecords().parallelStream().map(e -> fun.apply(e)).toArray(generator);
        return transPage(page, res);
    }
}
