import cn.yhs.learn.dao.AccountDao;
import cn.yhs.learn.dao.UserDao;
import cn.yhs.learn.domain.Account;
import cn.yhs.learn.domain.User;
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
 * @Name: PACKAGE_NAME.UserDaoTest
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/5/27 17:39
 * @Description: todo
 **/
public class AccountTest {
    private InputStream in;
    private SqlSessionFactory sqlSessionFactory;
    private SqlSession sqlSession;
    private AccountDao accountDao;

    @Before
    public void init() {
        try {
            in = Resources.getResourceAsStream("mybatis-config.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
        sqlSession = sqlSessionFactory.openSession(true);// 开启自动提交
        //  sqlSession.getConfiguration().setDefaultExecutorType(ExecutorType.BATCH);
        accountDao = sqlSession.getMapper(AccountDao.class);
    }

    @After
    public void close() {
        sqlSession.close();
        if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test // todo 1 查询所有用户
    public void test1FindAllAccount() {
        List<Account> list = accountDao.findAllAccount();
        for (Account account : list) {
            System.out.println(account);
        }
    }

    @Test
    public void test(){
        List<Account> allByUid = accountDao.findAllByUid(41);
        System.out.println(allByUid);
    }



}
