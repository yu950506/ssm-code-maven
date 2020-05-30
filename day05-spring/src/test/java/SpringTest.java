import cn.yhs.learn.service.impl.AccountServiceImpl;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @ProjectName: ssm-code-maven
 * @Name: PACKAGE_NAME.Test
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/5/28 15:42
 * @Description: todo
 **/
public class SpringTest {



    @Test
    public void test(){
        ClassPathXmlApplicationContext app = new ClassPathXmlApplicationContext("bean.xml");
        //上面代码一运行，就会创建配置文件中配置的对象，立即加载
/*        for (int i = 0; i < 10; i++) {
            Object daoImpl = app.getBean("accountDaoImpl");
            System.out.println(daoImpl); // 单例的
        }*/

     /*   AccountServiceImpl accountService = app.getBean(AccountServiceImpl.class);*/


    /*    for (int i = 0; i < 5; i++) {
            Object accountService = app.getBean("accountService");
            System.out.println(accountService);
        }*/
        Object accountService = app.getBean("accountService");

        // close 是子类特有的方法，所以这里使用的是子类创建的对象
        app.close(); // 关闭容器的方法

    }
}
