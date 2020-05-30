package cn.yhs.learn.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.annotations.Insert;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.learn.config.DruidJdbcConfig
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/5/30 11:11
 * @Description: todo
 **/
//@Configuration
//@PropertySource(value = "classpath:druid.properties")
public class DruidJdbcConfig {
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


    @Bean(name = "druidDataSource")
    public DataSource getDruidDataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(driverClassName);
        druidDataSource.setUrl(url);
        druidDataSource.setUsername(username);
        druidDataSource.setPassword(password);
        druidDataSource.setMaxWait(maxWait);
        druidDataSource.setInitialSize(initialSize);
        druidDataSource.setMaxActive(maxActive);
        return druidDataSource;
    }


    @Bean(name = "jdbcTemplate")
    public JdbcTemplate getJdbcTemplate(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate;
    }

}
