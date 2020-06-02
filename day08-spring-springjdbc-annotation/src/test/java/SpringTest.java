import cn.yhs.learn.config.SpringConfigration;
import cn.yhs.learn.domain.Account;
import cn.yhs.learn.service.AccoutService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @ProjectName: ssm-code-maven
 * @Name: PACKAGE_NAME.SpingTest
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/6/2 18:53
 * @Description: todo
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfigration.class)
public class SpringTest {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private AccoutService accoutService;

    @Test
    public void test0() {
        String[] names = context.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println("name = " + name);
        }
    }


    @Test
    public void test1() {
        List<Account> accountList = accoutService.findAll();
        for (Account account : accountList) {
            System.out.println(account);
        }
    }

    @Test
    public void test2() {
        Integer total = accoutService.findTotal();
        System.out.println(total);
    }

    @Test
    public void test3() {
        Account account = accoutService.findById(1);
        System.out.println(account);
    }

    @Test
    public void test4() {
        Account source = accoutService.findById(1);
        source.setMoney(source.getMoney() + 100);
        accoutService.update(source);


    }

    // 测试转账
    @Test
    public void test5() {
        Account source = accoutService.findById(1);
        Account dest = accoutService.findById(2);
        Double money = 500.0;
        accoutService.transform(source, dest, money);
    }

}
