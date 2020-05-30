
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
 * @Name: PACKAGE_NAME.AccountDaoTest
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/5/26 11:44
 * @Description: todo
 **/
public class UserDaoTest {
    private SqlSession sqlSession = null;
    private InputStream in = null;
    private UserDao userDao;

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
        userDao = sqlSession.getMapper(UserDao.class);
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
        User user = userDao.findById(50);
        System.out.println(user);
    }

    @Test
    public void test2() {
        List<User> userList = userDao.findAll();
      /*  for (User user : userList) {
            System.out.println(user);
        }*/
    }

}
