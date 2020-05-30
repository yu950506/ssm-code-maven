package cn.yhs.learn;

import cn.yhs.learn.domain.User;
import cn.yhs.learn.service.UserService;
import cn.yhs.learn.service.impl.UserServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.learn.MainApp
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/5/29 10:23
 * @Description: todo
 **/
public class MainApp {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
       /* UserService userService = context.getBean(UserServiceImpl.class); // 自动根据类型去找，
        System.out.println(userService);
        userService.save();*/
        User user = (User) context.getBean("user");
        System.out.println(user);
    }
}
