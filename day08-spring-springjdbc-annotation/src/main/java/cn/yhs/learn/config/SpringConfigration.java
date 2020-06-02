package cn.yhs.learn.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.learn.config.SpringConfigration
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/6/2 19:58
 * @Description: todo
 **/
@Configuration
@ComponentScan(basePackages = "cn.yhs.learn")
@Import({JdbcConfig.class, TransactionManager.class})
@EnableTransactionManagement // 开启事务管理
public class SpringConfigration {
}
