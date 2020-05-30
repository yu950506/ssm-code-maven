package cn.yhs.learn.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.learn.config.SpringConfig
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/5/30 13:36
 * @Description: todo
 **/
@Configuration
@ComponentScan("cn.yhs.learn")
@Import(DruidDataSourceConfig.class)
public class SpringConfig {
}
