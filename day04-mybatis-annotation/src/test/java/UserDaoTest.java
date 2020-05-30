import cn.yhs.learn.dao.UserDao;
import cn.yhs.learn.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ProjectName: ssm-code-maven
 * @Name: PACKAGE_NAME.UserDaoTest
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/5/27 17:39
 * @Description: todo
 **/
public class UserDaoTest {
    private InputStream in;
    private SqlSessionFactory sqlSessionFactory;
    private SqlSession sqlSession;
    private UserDao userDao;

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
        userDao = sqlSession.getMapper(UserDao.class);
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
    public void test1FindAllUser() {
        List<User> userList = userDao.findAllUser();
        for (User user : userList) {
            System.out.println(user);
        }
    }

    @Test // todo 2 查询
    public void test2() {
        User user = userDao.findUserById(50);
        System.out.println(user);
      // 只发生了一次查询 select * from user where id = ?
        User user2 = userDao.findUserById(50);
        System.out.println(user2);
        System.out.println(user == user2);// true
    }

    @Test // todo 3 查询
    public void test3() {
        Integer total = userDao.findUserTotal();
        System.out.println(total);
    }

    @Test // todo 4 查询
    public void test4() {
        String username = "%王%";
        List<User> userList = userDao.findUserByUserName(username);
        for (User user : userList) {
            System.out.println(user);
        }
    }

    @Test // todo 5 添加
    public void test5() {
        /**
         * 设置批量添加
         */
      System.out.println("====>>>>" + sqlSession.getConfiguration().getDefaultExecutorType());
        //sqlSession.getConfiguration().setDefaultExecutorType(ExecutorType.BATCH);
        List<User> users = new ArrayList<User>();
        for (int i = 0; i < 20; i++) {
            User user = new User();
            user.setUsername("ZZZ" + i);
            user.setAddress("ZZZ" + i);
            user.setSex('女');
            user.setBirthday(new Date());
            users.add(user);
        }
        for (User user : users) {
            userDao.SaveUser(user);
        }
    }

    @Test
    public void test6() {
        User user = userDao.findUserById(50);
        user.setUsername("UPDATE");
        userDao.updateUser(user);
    }

    @Test
    public void test7(){
        List<User> userList = userDao.findAllUserWithUser();
        for (User user : userList) {
            System.out.println(user);
        }

    }
}
