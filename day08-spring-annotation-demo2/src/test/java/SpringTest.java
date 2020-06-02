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
 * @Time: 2020/6/2 11:42
 * @Description: todo
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = cn.yhs.learn.config.SpringConfig.class)
public class SpringTest {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private AccountService accountService;


    @Test
    public void test(){
        String[] names = context.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }

    }

    @Test
    public void test2(){
        Double money = 200.0;
        Account source = accountService.findById(1);
        Account dest = accountService.findById(2);
        accountService.transfer(source,dest,money);
/*
TransactionManagerUtils.begin
TransactionManagerUtils.commit
TransactionManagerUtils.release
Account(id=1, uid=46, money=800.0)
TransactionManagerUtils.begin
TransactionManagerUtils.commit
TransactionManagerUtils.release
TransactionManagerUtils.begin
TransactionManagerUtils.rollback
java.lang.ArithmeticException: / by zero
TransactionManagerUtils.release
 */
    }


}
