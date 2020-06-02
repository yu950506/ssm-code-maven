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
 * @Name: cn.yhs.learn.config.C3P0JdbcConfig
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/6/2 11:07
 * @Description: todo
 **/
@PropertySource(value = "classpath:c3p0.properties")
//用于加载properties文件中的配置， value属性用于指定文件所在位置，需要在前面加classpath
public class C3P0JdbcConfig {
    @Value("${c3p0.jdbc.driverClass}") // 读取相应properties文件中的key,并将value注入进去
    private String driverClass;
    @Value("${c3p0.jdbc.jdbcUrl}")
    private String jdbcUrl;
    @Value("${c3p0.jdbc.user}")
    private String user;
    @Value("${c3p0.jdbc.password}")
    private String password;

    @Bean(name = "dataSource")//该注解只能写在方法上，表名使用此方法创建一个对象，并且放入Spring容器,不写name属性默认是当前方法名
    public DataSource getC3p0DataSource() {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        try {
            dataSource.setDriverClass(driverClass);
            dataSource.setJdbcUrl(jdbcUrl);
            dataSource.setUser(user);
            dataSource.setPassword(password);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        return dataSource;
    }

    @Bean(name = "queryRunner")
    public QueryRunner getQueryRunner(DataSource dataSource) {
        QueryRunner queryRunner = new QueryRunner(dataSource);
        return queryRunner;
    }

}
