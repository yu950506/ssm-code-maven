import cn.yhs.learn.domain.Account;
import cn.yhs.learn.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @ProjectName: ssm-code-maven
 * @Name: PACKAGE_NAME.SpringTest
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/6/1 22:00
 * @Description: todo
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:bean.xml")
public class SpringTest {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private AccountService accountService;

    @Test
    public void testAllDefineNames() {
        String[] names = context.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }
    }

    @Test
    public void testFind() {
        Account account1 = accountService.findById(1);
        Account account2 = accountService.findById(2);
        System.out.println(account1);
        System.out.println(account2);

    }

    /**
     * 测试转账
     */
    @Test
    public void testTransfer() {
        Account account1 = accountService.findById(1);
        Account account2 = accountService.findById(2);
        Double money = 200.0;
        accountService.transfer(account1, account2, money);


    }


}
