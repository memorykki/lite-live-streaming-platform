package com.xinf.config;

import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xinf
 * @since 2021/9/8 10:24
 */
@Configuration
public class DruidConfig {

    @Bean
    public Slf4jLogFilter logFilter(){
        Slf4jLogFilter filter = new Slf4jLogFilter();
        filter.setResultSetLogEnabled(false);
        filter.setConnectionLogEnabled(false);
        filter.setStatementParameterClearLogEnable(false);
        filter.setStatementCreateAfterLogEnabled(false);
        filter.setStatementCloseAfterLogEnabled(false);
        filter.setStatementParameterSetLogEnabled(false);
        filter.setStatementPrepareAfterLogEnabled(false);
        return  filter;
    }


    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        //添加过滤规则.
        filterRegistrationBean.addUrlPatterns("/*");
        //添加不需要忽略的格式信息.
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }
}
