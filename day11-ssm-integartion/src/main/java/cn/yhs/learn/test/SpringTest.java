package cn.yhs.learn.test;

import cn.yhs.learn.domain.Account;
import cn.yhs.learn.domain.User;
import cn.yhs.learn.service.AccountService;
import cn.yhs.learn.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.learn.test.SpringTest
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/6/7 10:04
 * @Description: todo
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class SpringTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @Test
    public void test1() {
        String[] names = applicationContext.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println("name = " + name);
        }
    }

    @Test
    public void test2() {

        User user = userService.findById(50);
        System.out.println("user = " + user);

    }

    @Test
    public void test3() {
        Account source = accountService.findById(1);
        Account dest = accountService.findById(2);
        System.out.println(source);
        System.out.println(dest);
        source.setMoney(1000.0);
        dest.setMoney(1000.0);
        accountService.update(source);
        accountService.update(dest);
        System.out.println("更新后");
        Account source1 = accountService.findById(1);
        Account dest2 = accountService.findById(2);
        System.out.println(source1);
        System.out.println(dest2);

    }

    @Test
    public void test4() {
        Account source = accountService.findById(1);
        Account dest = accountService.findById(2);
        Double money = 500.0;
        accountService.transform(source, dest, money);

    }


}
