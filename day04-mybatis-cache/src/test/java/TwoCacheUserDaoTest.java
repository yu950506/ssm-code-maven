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
public class TwoCacheUserDaoTest {

    private InputStream in = null;
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


    @Test
    public void test() {
        /**
         * 二级缓存是建立在sqlSessionFactory 上的
         * 必须要实现序列化接口  Caused by: java.io.NotSerializableException: cn.yhs.learn.domain.User
         */
        SqlSession sqlSession1 = sqlSessionFactory.openSession();
        UserDao mapper1 = sqlSession1.getMapper(UserDao.class);
        User user1 = mapper1.findById(50);
        System.out.println(user1);
        sqlSession1.close();
        /**
         * 只查询了一次，将数据序列化，再查询的时候将数据反序列化，达到了与数据库交互的次数
         */
        SqlSession sqlSession2 = sqlSessionFactory.openSession();
        UserDao mapper2 = sqlSession2.getMapper(UserDao.class);
        User user2 = mapper2.findById(50);
        System.out.println(user2);
        sqlSession2.close();


        System.out.println(user1 == user2);


        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
