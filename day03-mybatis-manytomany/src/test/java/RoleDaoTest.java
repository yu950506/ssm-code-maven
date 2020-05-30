import cn.yhs.learn.dao.RoleDao;
import cn.yhs.learn.domain.Role;
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
public class RoleDaoTest {
    private InputStream in;
    private SqlSession sqlSession;
    private RoleDao roleDao;

    @Before
    public void init() {
        try {
            in = Resources.getResourceAsStream("mybatis-config.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        sqlSession = new SqlSessionFactoryBuilder().build(in).openSession();
        roleDao = sqlSession.getMapper(RoleDao.class);
    }

    @Test
    public void test(){
        List<Role> roleList = roleDao.findRoleWithUser();
        for (Role role : roleList) {
            System.out.println(role);
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
