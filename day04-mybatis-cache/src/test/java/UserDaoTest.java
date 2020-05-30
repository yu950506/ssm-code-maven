import cn.yhs.learn.dao.UserDao;
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
    private SqlSessionFactory sqlSessionFactory;
    private UserDao userDao;

    @Before
    public void init() {
        try {
            in = Resources.getResourceAsStream("mybatis-config.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
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
        /* 默认时候的是一级缓存，两次查询的都是一个对象，与数据库只建立了一次连接*/
        // sqlSession.clearCache(); // 清楚缓存之后就发送了两个查询语句
        // 方式二 清除一级缓存
        sqlSession.close();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        User user1 = mapper.findById(50);
        System.out.println(user1);
        System.out.println(user == user1);
    }

    @Test
    public void test2() {
        List<User> userList = userDao.findAll();
      /*  for (User user : userList) {
            System.out.println(user);
        }*/
    }

    @Test
    public void test3() {
        User user = userDao.findById(50);
        System.out.println(user);
        /**
         * 一级缓存是sqlsession范围的缓存，当调用sqlsession的修改，添加，删除，，commit,close()
         * 等方法的时候，会清楚缓存，
         * 这样做的目的是为了在缓存中存储罪行的信息，防止数据脏读的发生
         */
        user.setUsername("update update");
        userDao.update(user);
        User user1 = userDao.findById(50);
        System.out.println(user1);
        System.out.println(user == user1);
    }

}
