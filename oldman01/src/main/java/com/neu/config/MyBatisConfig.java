package com.neu.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration(proxyBeanMethods = false)
public class MyBatisConfig {

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml")
        );
        factoryBean.setTypeAliasesPackage("com.neu");
        return factoryBean.getObject();
    }

    @Bean
    public static MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
        configurer.setBasePackage("com.neu.health.mapper,com.neu.xitong.mapper,com.neu.kanban.mapper,com.neu.laoren.mapper,com.neu.huli.mapper,com.neu.chuangwei.mapper,com.neu.zanzhu.mapper,com.neu.huliguanli.mapper,com.neu.huliyuan.mapper,com.neu.admin.mapper,com.neu.jiashu.mapper,com.neu.gongkai.mapper,com.neu.yaopin.mapper");
        configurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        return configurer;
    }
}


