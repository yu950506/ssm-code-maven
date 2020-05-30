import cn.yhs.learn.config.SpringConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @ProjectName: ssm-code-maven
 * @Name: PACKAGE_NAME.SpringTest
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/5/30 13:42
 * @Description: todo
 **/

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
/**
 *  @RunWith(SpringJUnit4ClassRunner.class) 替换原来运行期
 *  @ContextConfiguration(classes = SpringConfig.class) 用于加载applicationContext 对象
 *  location ： xml 方式：用于指定xml配置文件的位置，需要用classpath:指明
 *  classes : 注解方式 ： 用于指定注解的配置类
 */
public class SpringTest {
    @Autowired
    private DataSource dataSource;
    @Test
    public void test2() {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(connection);
    }

    @Test
    public void test1() {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        String[] names = context.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }

    }
}
