package com.example.study.elasticsearch.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;


/**
 * @author wangfei
 * @date 2019/1/21 10:47
 */
@Configuration
@MapperScan(basePackages = {"com.jd.jrmserver.base.dao.globalSearch.search"}, sqlSessionFactoryRef = "sourceSqlSessionFactory", sqlSessionTemplateRef = "sourceSqlSessionTemplate")
public class DataSourceConfig {

    @Value("${mybatis_config_file}")
    private String myBatisConfigFilePath;

    @Value("${source_mapper_path}")
    private String mapperPath;

    @Value("${source_entity_package}")
    private String entityPackage;

    @Bean("sourceDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return new DruidDataSource();
    }

    @Bean("sourceSqlSessionFactory")
    @Primary
    public SqlSessionFactory sessionFactory(
            @Qualifier("sourceDataSource") DataSource dataSource) throws Exception {
        return getSessionFactory(dataSource, myBatisConfigFilePath, mapperPath, entityPackage);
    }

    static SqlSessionFactory getSessionFactory(@Qualifier("sourceDataSource") DataSource dataSource, String myBatisConfigFilePath,
                                               String mapperPath,
                                               String entityPackage)
            throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        //设置配置文件路径
        sqlSessionFactoryBean.setConfigLocation(new ClassPathResource(myBatisConfigFilePath));
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        String packageSearchPath = PathMatchingResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + mapperPath;
        //设置mapper文件路径
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources(packageSearchPath));
        //设置数据源
        sqlSessionFactoryBean.setDataSource(dataSource);
        //设置类型别名包
        sqlSessionFactoryBean.setTypeAliasesPackage(entityPackage);
        return sqlSessionFactoryBean.getObject();
    }

    @Bean("sourceDataSourceTransactionManager")
    @Primary
    public DataSourceTransactionManager dataSourceTransactionManager(
            @Qualifier("sourceDataSource") DataSource dataSource) {
        DataSourceTransactionManager manager = new DataSourceTransactionManager();
        manager.setDataSource(dataSource);
        return manager;
    }

    @Bean("sourceSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sourceSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}

