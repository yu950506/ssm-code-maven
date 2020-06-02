package cn.yhs.learn.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.learn.config.JdbcConfig
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/6/2 19:59
 * @Description: todo
 **/
@PropertySource(value = "classpath:jdbc.properties")
public class JdbcConfig {
    @Value("${jdbc.driverClass}")
    String driverClassName;
    @Value("${jdbc.jdbcUrl}")
    String url;
    @Value("${jdbc.user}")
    String username;
    @Value("${jdbc.password}")
    String password;

    @Bean(name = "dataSource") // 配饰数据源
    public DataSource getDataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName(driverClassName);
        driverManagerDataSource.setUrl(url);
        driverManagerDataSource.setUsername(username);
        driverManagerDataSource.setPassword(password);
        return driverManagerDataSource;
    }

    @Bean(name = "template")
    public JdbcTemplate getJdbcTemplate(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate;
    }

}
