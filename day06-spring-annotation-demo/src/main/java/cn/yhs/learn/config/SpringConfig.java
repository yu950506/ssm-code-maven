package cn.yhs.learn.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.learn.config.SpringConfig
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/5/30 10:14
 * @Description: todo
 **/

/**
 * @Configuration,相当于bean.xml配置文件用于指定当前类是一个Spring的配置类， 当创建容器的时候，会从该类加上注解，获取容器时需要使用AnnotationApplicationContext value 属性，用于指定配置类的字节码
 *
 * @ComponentScan 相当于 <context:component-scan base-package="cn.yhs.learn"></context:component-scan> 标签，用于指定要扫描的包
 *
 */
@Configuration
@ComponentScan(basePackages = "cn.yhs.learn")
//@Import({DruidJdbcConfig.class,C3p0JdbcConfig.class})
@Import(C3p0JdbcConfig.class)
public class SpringConfig {

}
