package cn.yhs.learn.dao.impl;

import cn.yhs.learn.dao.UserDao;
import cn.yhs.learn.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.learn.dao.impl.UserDaoImplDruid
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/5/29 15:29
 * @Description: todo
 **/

/**
 * 使用Spring JDBC 和 Druid 两种组合来实现
 */
//@Repository(value = "userDaoSpringJDBC")
public class UserDaoImplDruid implements UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;



    public List<User> findAllUser() {
        String sql = "select * from user;";
        try {
            System.out.println("使用Spring JDBC 和 Druid 两种组合来实现");
            List<User> userList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<User>(User.class));
            return userList;
        } catch (Exception e) {
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
