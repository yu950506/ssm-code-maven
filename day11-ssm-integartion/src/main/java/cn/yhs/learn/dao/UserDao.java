package cn.yhs.learn.dao;

import cn.yhs.learn.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.learn.dao.UserDao
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/6/7 9:45
 * @Description: todo
 **/
@Repository("userDao")
public interface UserDao {
    @Select("select * from user")
    List<User> findAll();

    @Select("select * from user where id = #{id}")
    User findById(Integer id);

    @Insert("insert into user(username,birthday,sex,address) value(#{username},#{birthday},#{sex},#{address})")
    void save(User user);
}
