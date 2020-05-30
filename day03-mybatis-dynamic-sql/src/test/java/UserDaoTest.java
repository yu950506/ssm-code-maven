import cn.yhs.learn.dao.UserDao;
import cn.yhs.learn.domain.User;
import com.sun.xml.internal.ws.api.model.ExceptionType;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ProjectName: ssm-code-maven
 * @Name: PACKAGE_NAME.Test
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/5/26 9:35
 * @Description: todo
 **/
public class UserDaoTest {
    private SqlSession sqlSession = null;
    private InputStream in = null;
    private UserDao userDao = null;

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
    public void test1() {
        User user = new User();
   /*     user.setAddress("%北京%");
        user.setUsername("%王%");*/
        List<User> userList = userDao.findByCondition(user);
        for (User user1 : userList) {
            System.out.println(user1);
        }

    }

    @Test
    public void test2FindAll() {
        List<User> userList = userDao.findAll();
        for (User user1 : userList) {
            System.out.println(user1);
        }
    }

    @Test
    public void test2FindByIds() {
        List<Integer> ids = new ArrayList<Integer>();
        ids.add(41);
        ids.add(42);
        ids.add(43);
        ids.add(44);
        List<User> userList = userDao.findByIds(ids);
        for (User user1 : userList) {
            System.out.println(user1);
        }
    }

    @Test
    public void test3InsertAll() {
        List<User> list = new ArrayList<User>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setUsername("李四" + i);
            user.setBirthday(new Date());
            user.setSex('女');
            user.setAddress("中国上海");
            list.add(user);
        }
        System.out.println(list);
       int i = userDao.insertAll(list);
        System.out.println(i);

    }



    @Test
    public void test4DeleteAll() {
        List<Integer> ids = new ArrayList<Integer>();
            ids.add(62);
            ids.add(63);
            ids.add(64);
            ids.add(65);
            ids.add(66);
            ids.add(67);
        int i = userDao.deleteAll(ids);
        System.out.println(i);
    }

    @Test
    public void test1n(){
        List<User> userList = userDao.findAllUserAccount();
        for (User user : userList) {
            System.out.println(user);
        }
    }


}
