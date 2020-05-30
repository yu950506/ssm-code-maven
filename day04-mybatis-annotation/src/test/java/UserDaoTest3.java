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
public class UserDaoTest3 {
    private InputStream in;
    private SqlSessionFactory sqlSessionFactory;


    @Before
    public void init() {
        try {
            in = Resources.getResourceAsStream("mybatis-config.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);

    }

    @After
    public void close() {
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
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        User user1 = mapper.findUserById(50);
        System.out.println(user1);
        sqlSession.close(); // 释放一级缓存
        /**
         * 发送了两次sql语句,开启缓存之后就只有一条sql
         */
        SqlSession sqlSession2 = sqlSessionFactory.openSession();
        UserDao mapper2 = sqlSession2.getMapper(UserDao.class);
        User user2 = mapper2.findUserById(50);
        System.out.println(user2);
        System.out.println(user1 == user2);// false，不一样是因为序列化成文本，再反序列化成一个新的对象
        sqlSession2.close();


    }

}
