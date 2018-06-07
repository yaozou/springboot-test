package com.yaozou.platform.common.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageHelper;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 数据库连接配置信息
 * @author luojianhong
 * @version $Id: DruidDBConfig.java, v 0.1 2017年10月9日 下午4:37:19 luojianhong Exp $
 */
@Configuration
@EnableTransactionManagement
@MapperScan(value = {"com.yaozou.platform.*.dao","com.yaozou.platform.*.repository"})
public class DruidDBConfig {
    
    private Logger  logger = LoggerFactory.getLogger(DruidDBConfig.class);
    
    @Value("${spring.datasource.url}")
    private String  dbUrl;

    @Value("${spring.datasource.username}")
    private String  username;

    @Value("${spring.datasource.password}")
    private String  password;

    @Value("${spring.datasource.driverClassName}")
    private String  driverClassName;

    @Value("${spring.datasource.initialSize}")
    private int     initialSize;

    @Value("${spring.datasource.minIdle}")
    private int     minIdle;

    @Value("${spring.datasource.maxActive}")
    private int     maxActive;

    @Value("${spring.datasource.maxWait}")
    private int     maxWait;

    @Value("${spring.datasource.timeBetweenEvictionRunsMillis}")
    private int     timeBetweenEvictionRunsMillis;

    @Value("${spring.datasource.minEvictableIdleTimeMillis}")
    private int     minEvictableIdleTimeMillis;

    @Value("${spring.datasource.validationQuery}")
    private String  validationQuery;

    @Value("${spring.datasource.testWhileIdle}")
    private boolean testWhileIdle;

    @Value("${spring.datasource.testOnBorrow}")
    private boolean testOnBorrow;

    @Value("${spring.datasource.testOnReturn}")
    private boolean testOnReturn;

    @Value("${spring.datasource.poolPreparedStatements}")
    private boolean poolPreparedStatements;

    @Value("${spring.datasource.maxPoolPreparedStatementPerConnectionSize}")
    private int     maxPoolPreparedStatementPerConnectionSize;

    @Value("${spring.datasource.filters}")
    private String  filters;

    @Value("{spring.datasource.connectionProperties}")
    private String  connectionProperties;

    @Bean(initMethod = "init", destroyMethod = "close") //声明其为Bean实例
    @Primary //在同样的DataSource中，首先使用被标注的DataSource
    public DataSource dataSource() {
        DruidDataSource datasource = new DruidDataSource();

        datasource.setUrl(this.dbUrl);
        datasource.setUsername(username);
        datasource.setPassword(password);
        datasource.setDriverClassName(driverClassName);

        //configuration
        datasource.setInitialSize(initialSize);
        datasource.setMinIdle(minIdle);
        datasource.setMaxActive(maxActive);
        datasource.setMaxWait(maxWait);
        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        datasource.setValidationQuery(validationQuery);
        datasource.setTestWhileIdle(testWhileIdle);
        datasource.setTestOnBorrow(testOnBorrow);
        datasource.setTestOnReturn(testOnReturn);
        datasource.setPoolPreparedStatements(poolPreparedStatements);
        datasource.setMaxPoolPreparedStatementPerConnectionSize(
            maxPoolPreparedStatementPerConnectionSize);
        try {
            datasource.setFilters(filters);
        } catch (SQLException e) {
            logger.error("druid configuration initialization filter", e);
        }
        datasource.setConnectionProperties(connectionProperties);

        return datasource;
    }


    
    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());
        //mybatis分页
        PageHelper pageHelper = new PageHelper();
        Properties props = new Properties();
        props.setProperty("dialect", "mysql");
        props.setProperty("offsetAsPageNum", "true");
        props.setProperty("rowBoundsWithCount", "true");
        props.setProperty("pageSizeZero", "true");
        props.setProperty("reasonable", "false");
        props.setProperty("params", "pageNum=start;pageSize=limit;");
        props.setProperty("returnPageInfo", "check");
        pageHelper.setProperties(props); //添加插件
        
        sqlSessionFactoryBean.setPlugins(new Interceptor[]{pageHelper});
        
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();  
        
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath*:mybatis/**/*.xml"));
      //  annotationClass
        sqlSessionFactoryBean.setTypeAliasesPackage("com.yaozou.platform");
        
        sqlSessionFactoryBean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);

        return sqlSessionFactoryBean.getObject();
    }

   @Bean
    public PlatformTransactionManager transactionManager() throws SQLException {
        return new DataSourceTransactionManager(dataSource());
    }
}
