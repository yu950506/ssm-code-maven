package cn.yhs.learn.dao.impl;

import cn.yhs.learn.dao.UserDao;
import cn.yhs.learn.domain.User;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.learn.dao.impl.UserDaoImpl
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/5/29 14:48
 * @Description: todo
 **/

/**
 * 使用DBUtils进行实现
 */

@Repository("userDao")
public class UserDaoImpl implements UserDao {
    /**
     * 这里使用配置文件进行对象的注入，
     * xml方式需要提供set方法
     * <p>
     * Dao层实现使用的是C3P0连接池和DBUtils组合进行查询
     */

    // todo 发现这里还是半注解，不是全注解
    @Autowired
    private QueryRunner queryRunner;

    public List<User> findAllUser() {
        System.out.println("使用的是C3P0连接池和DBUtils组合来实现");
        String sql = "select * from user;";
        try {
            List<User> userList = queryRunner.query(sql, new BeanListHandler<User>(User.class));
            return userList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User findUserById() {
        return null;
    }

    public void insertUser(User User) {

    }

    public void deleteUser(Integer id) {

    }

    public void UpdateUser(User user) {

    }
}
