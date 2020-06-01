import cn.yhs.learn.service.impl.AccountServiceImpl;
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
 * @Time: 2020/6/1 20:41
 * @Description: todo
 **/

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:bean.xml")
public class SpringTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void test1() {
        String[] names = applicationContext.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }

    }

    @Test
    public void test2() {
        AccountServiceImpl accountService = (AccountServiceImpl) applicationContext.getBean("accountService");
        accountService.find();
        System.out.println("*******************");
        accountService.delete(50);
        System.out.println("*******************");
        accountService.save();
        System.out.println("*******************");
        //accountService.err();

    }

}
