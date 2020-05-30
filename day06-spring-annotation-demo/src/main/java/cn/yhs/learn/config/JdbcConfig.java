package cn.yhs.learn.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;


/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.learn.config.JdbcConfig
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/5/30 10:24
 * @Description: todo
 **/
// @Configuration
public class JdbcConfig {
     /*   <bean class="org.apache.commons.dbutils.QueryRunner" id="queryRunner" scope="prototype">
        <constructor-arg name="ds" ref="dataSource"></constructor-arg>
    </bean>

    <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
        <property name="driverClass" value="com.mysql.jdbc.Driver"></property>
        <property name="jdbcUrl" value="jdbc:mysql:///dm"></property>
        <property name="user" value="root"></property>
        <property name="password" value="123456"></property>
    </bean>*/

    /**
     * 配置文件中用下面这两个注解进行替换
     *
     * @param dataSources
     * @return
     */
    /**
     * @Bean 只能作用在方法上，表名使用此方法创建一个对象并且放入Spring容器
     * name 用于给当前创建的对象取一个名称，,没有写默认是改方法名称，getQueryRunner
     */
   // @Bean(name = "queryRunner")
    public QueryRunner getQueryRunner(DataSource dataSources) {
        return new QueryRunner(dataSources);
    }

/*
    @Bean(name = "dataSource")
    public DataSource getDataSource() {
        */
/**
         *     <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
         *         <property name="driverClass" value="com.mysql.jdbc.Driver"></property>
         *         <property name="jdbcUrl" value="jdbc:mysql:///dm"></property>
         *         <property name="user" value="root"></property>
         *         <property name="password" value="123456"></property>
         *     </bean>
         *//*

        try {
            ComboPooledDataSource pooledDataSource = new ComboPooledDataSource();
            pooledDataSource.setUser("root");
            pooledDataSource.setPassword("123456");
            pooledDataSource.setDriverClass("com.mysql.jdbc.Driver");
            pooledDataSource.setJdbcUrl("jdbc:mysql:///dm");
            return pooledDataSource;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
*/


    // 配置一下Spring jdbc
    //@Bean(name = "jdbcTemplate")
    public JdbcTemplate getJdbcTemplate(DataSource druidDataSource) {
        return new JdbcTemplate(druidDataSource);
    }
//    @Bean(name = "druidDataSource")
    public DataSource getDruidSource() {
        /**
         *         <property name="driverClassName" value="com.mysql.jdbc.Driver"></property>
         *         <property name="url" value="jdbc:mysql:///dm"></property>
         *         <property name="username" value="root"></property>
         *         <property name="password" value="123456"></property>
         *         <property name="maxWait" value="2000"></property>
         *         <property name="initialSize" value="10"></property>
         *         <property name="maxActive" value="10"></property>
         */
        DruidDataSource ds = new DruidDataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUrl("jdbc:mysql:///dm");
        ds.setUsername("root");
        ds.setPassword("123456");
        ds.setMaxActive(10);
        ds.setInitialSize(10);
        ds.setMaxWait(2000);
        return ds;
    }


}
