import cn.yhs.learn.domain.Account;
import cn.yhs.learn.service.AccountService;
import cn.yhs.learn.util.ConnectionUtils;
import cn.yhs.learn.util.TransactionManager;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @ProjectName: ssm-code-maven
 * @Name: PACKAGE_NAME.SpringTest
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/6/1 13:05
 * @Description: todo
 **/
public class SpringTest {
    // 查询所有
    @Test
    public void testName() {
        ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
   /*     ConnectionUtils connectionUtils = (ConnectionUtils) context.getBean("connectionUtils");
        Connection connect = connectionUtils.getThreadLocalConnection();
        try {
            System.out.println(connect.getAutoCommit());
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
/*        ConnectionUtils connectionUtils = (ConnectionUtils) context.getBean("connectionUtils");
        Connection connect = connectionUtils.getThreadLocalConnection();
        System.out.println(connect);
        TransactionManager transactionManager = (TransactionManager) context.getBean("transactionManager");
        try {
            Connection connection = transactionManager.getConnection();
            System.out.println(connection + "=>" + connection.getAutoCommit());
            transactionManager.beginTransaction();
            Connection connection2 = transactionManager.getConnection();
            System.out.println(connection2 + "=>" + connection2.getAutoCommit());

        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        TransactionManager transactionManager = (TransactionManager) context.getBean("transactionManager");
        transactionManager.beginTransaction();
        // transactionManager.release();
        transactionManager.beginTransaction();
        try {
            System.out.println(transactionManager.getConnection().getAutoCommit());
            transactionManager.release();
            System.out.println(transactionManager.getConnection().getAutoCommit());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    // 查询所有
    @Test
    public void test() {
        ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
        AccountService accountService = (AccountService) context.getBean("accountService");
        List<Account> accountList = accountService.findAll();
        for (Account account : accountList) {
            System.out.println(account);
        }
    }

    // 查找一个
    @Test
    public void test2() {
        ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
        AccountService accountService = (AccountService) context.getBean("accountService");
        Account account = accountService.findById(1);
        System.out.println(account);
    }

    // 更新
    @Test
    public void test3() {
        ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
        AccountService accountService = (AccountService) context.getBean("accountService");
        Account account = accountService.findById(1);
        System.out.println(account);
        account.setMoney(5000.0);
        accountService.updateAccount(account);
    }


    // 转账

    @Test
    public void test4() {
        ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
        AccountService accountService = (AccountService) context.getBean("proxyService");// 使用代理类加上事务控制
        //  AccountService accountService = (AccountService) context.getBean("accountService2");
        Account source = accountService.findById(1);
        Account dest = accountService.findById(2);
        // 现在有异常
        accountService.transfer(source, dest, 200.0);

    }


    // 删除成功，说明还是有事务的，没有事务控制，是删除不成功的
    @Test
    public void test5() {
        ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
        AccountService accountService = (AccountService) context.getBean("accountService");
        accountService.deleteById(1);
    }
}
