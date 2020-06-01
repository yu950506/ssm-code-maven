import cn.yhs.learn.service.AccountService;
import cn.yhs.learn.service.impl.AccountServiceImpl;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @ProjectName: ssm-code-maven
 * @Name: PACKAGE_NAME.AopTest
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/5/31 14:23
 * @Description: todo
 **/
public class AopTest {
    @Test
    public void test() {
        ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
        AccountService accountService = (AccountService) context.getBean("accountService");
        accountService.saveAccount();
        accountService.updateAccount();
        accountService.deleteAccount(5);
    }
}
