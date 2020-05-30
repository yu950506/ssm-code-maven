package cn.yhs.learn.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.learn.config.DruidDataSourceConfig
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/5/30 13:36
 * @Description: todo
 **/
@PropertySource("classpath:druid.properties")
public class DruidDataSourceConfig {
    // Druid连接数据可相关的参数
    @Value("${druid.jdbc.driverClassName}")
    private String driverClassName;
    @Value("${druid.jdbc.url}")
    private String url;
    @Value("${druid.jdbc.username}")
    private String username;
    @Value("${druid.jdbc.password}")
    private String password;
    @Value("${druid.jdbc.maxWait}")
    private Integer maxWait;
    @Value("${druid.jdbc.initialSize}")
    private Integer initialSize;
    @Value("${druid.jdbc.maxActive}")
    private Integer maxActive;

    // 获取数据源
    @Bean(name = "dataSource")
    public DataSource getDatasource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setMaxWait(maxWait);
        dataSource.setMaxActive(maxActive);
        dataSource.setInitialSize(initialSize);
        return dataSource;
    }

    @Bean(value = "jdbcTemplate")
    public JdbcTemplate getJdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
