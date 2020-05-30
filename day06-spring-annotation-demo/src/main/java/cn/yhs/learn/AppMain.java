package cn.yhs.learn;

import cn.yhs.learn.config.JdbcConfig;
import cn.yhs.learn.config.SpringConfig;
import cn.yhs.learn.domain.User;
import cn.yhs.learn.service.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.learn.AppMain
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/5/30 10:02
 * @Description: todo
 **/
public class AppMain {

    @Test
    public void test(){
    /*    ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
        String[] names = context.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }
        UserService userService = (UserService) context.getBean("userService");
        List<User> userList = userService.findAllUser();
        for (User user : userList) {
            System.out.println(user);
        }
*/
    ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class,JdbcConfig.class);
        String[] names = context.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }
        UserService userService = (UserService) context.getBean("userService");
        List<User> userList = userService.findAllUser();
        for (User user : userList) {
            System.out.println(user);
        }
    }
}
