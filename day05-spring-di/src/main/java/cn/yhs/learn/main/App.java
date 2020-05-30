package cn.yhs.learn.main;

import cn.yhs.learn.domian.Account;
import cn.yhs.learn.domian.Demo;
import cn.yhs.learn.domian.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.learn.main.App
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/5/28 21:35
 * @Description: todo
 **/
public class App {
    public static void main(String[] args) {
        // 读取资源配置文件并创建对象,会把配置文件中配置的bean 都实例化
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("bean.xml");
      /*  User user = (User)applicationContext.getBean("user");
        System.out.println(user);*/
/*     Account account= (Account) applicationContext.getBean("account");
        System.out.println(account);*/
        Demo demo = (Demo) applicationContext.getBean("demo");
        System.out.println(demo);
    }
}
