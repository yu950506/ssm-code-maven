import cn.yhs.learn.controller.UserController;
import cn.yhs.learn.controller.impl.UserControllerImpl;
import cn.yhs.learn.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @ProjectName: ssm-code-maven
 * @Name: PACKAGE_NAME.DemoTest
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/5/29 15:11
 * @Description: todo
 **/

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:bean.xml")
public class DemoTest {
    @Autowired
    private UserController controller;
    @Autowired
    private ApplicationContext context;
    @Test
    public void test3() {
        String[] names = context.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }
    }

    @Test
    public void test2() {
        List<User> userList = controller.findAllUser();
        for (User user : userList) {
            System.out.println(user);
        }
    }

    @Test
    public void test() {
        ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
        UserControllerImpl userControllerImpl = (UserControllerImpl) context.getBean("userControllerImpl");
        List<User> userList = userControllerImpl.findAllUser();
        for (User user : userList) {
            System.out.println(user);
        }
    }

}
