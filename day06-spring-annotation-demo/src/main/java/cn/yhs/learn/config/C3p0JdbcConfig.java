package cn.yhs.learn.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.learn.config.C3p0JdbcConfig
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/5/30 12:47
 * @Description: todo
 **/

@PropertySource("classpath:c3p0.properties")
public class C3p0JdbcConfig {
    @Value("${c3p0.jdbc.driverClass}")
    private String driverClass;
    @Value("${c3p0.jdbc.jdbcUrl}")
    private String jdbcUrl;
    @Value("${c3p0.jdbc.user}")
    private String user;
    @Value("${c3p0.jdbc.password}")
    private String password;

    @Bean(name = "c3p0DataSource")
    public DataSource getC3p0DataSource() {
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        try {
            comboPooledDataSource.setDriverClass(driverClass);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        comboPooledDataSource.setJdbcUrl(jdbcUrl);
        comboPooledDataSource.setUser(user);
        comboPooledDataSource.setPassword(password);
        return comboPooledDataSource;
    }

    @Bean(name = "queryRunner")
    public QueryRunner getQueryRunner(DataSource dataSource) {
        return new QueryRunner(dataSource);
    }
}
