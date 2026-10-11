package com.neu.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
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

        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        // Map 结果中的 DATE 列按 LocalDate 返回，避免 JSON 中出现按 UTC 换算后差一天的日期
        configuration.getTypeHandlerRegistry().register(Object.class, JdbcType.DATE, new SqlDateAsLocalDateTypeHandler());
        factoryBean.setConfiguration(configuration);
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


