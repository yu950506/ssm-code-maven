
import cn.yhs.learn.dao.AccountDao;
import cn.yhs.learn.dao.UserDao;
import cn.yhs.learn.domain.Account;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @ProjectName: ssm-code-maven
 * @Name: PACKAGE_NAME.AccountDaoTest
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/5/26 11:44
 * @Description: todo
 **/
public class AccountDaoTest {
    private SqlSession sqlSession = null;
    private InputStream in = null;
    private AccountDao accountDao;

    @Before
    public void init() {
        try {
            in = Resources.getResourceAsStream("mybatis-config.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
        // 修改自动提交
        sqlSession = sqlSessionFactory.openSession(true);
        // 使用动态代理创建一个UserDao的实现类
        accountDao = sqlSession.getMapper(AccountDao.class);
    }

    @After
    public void close() {
        if (sqlSession != null) {
            sqlSession.close();
        }
        if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void test() {
        List<Account> account = accountDao.findAllAccountWithUser();
        /* 只查询账户，不使用用户就不会加载用户信息*/
      /*  for (Account account1 : account) {
            System.out.println(account1);
        }*/
    }

}
