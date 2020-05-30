import cn.yhs.learn.dao.UserDao;
import cn.yhs.learn.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
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
 * @Time: 2020/5/26 16:34
 * @Description: todo
 **/
public class UserDaoTest {
    private InputStream in;
    private SqlSession sqlSession;
    private UserDao userDao;

    @Before
    public void init() {
        try {
            in = Resources.getResourceAsStream("mybatis-config.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        sqlSession = new SqlSessionFactoryBuilder().build(in).openSession();
        userDao = sqlSession.getMapper(UserDao.class);
    }

    @Test
    public void test(){
        List<User> userList = userDao.findUserWithRole();
        for (User user : userList) {
            System.out.println(user);
        }
    }

















    @After
    public void close() {
        if (sqlSession != null) {
            sqlSession.commit();
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
}
