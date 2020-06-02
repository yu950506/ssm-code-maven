package cn.yhs.learn.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.learn.config.SpringConfig
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/6/2 11:06
 * @Description: todo
 **/
@Configuration //用于加载配置
@ComponentScan(basePackages = "cn.yhs.learn") // 加了注解的基本包的扫描
@Import(C3P0JdbcConfig.class) // 导入配置文件
@EnableAspectJAutoProxy // 开启切面通知，AOP配置必须加的
public class SpringConfig {
}
