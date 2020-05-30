package cn.yhs.learn.dao;

import cn.yhs.learn.domain.User;

import java.util.List;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.learn.dao.UserDao
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/5/27 11:03
 * @Description: todo
 **/
public interface UserDao {
    /**
     * 根据 id 查询用户
     *
     * @param id
     * @return
     */
    User findById(Integer id);

    /**
     * 1：N查询，查询所有用户，并把下面的所哟账户信息显示出来
     *
     *
     * @return
     */
    List<User> findAll();


    void update(User user);
}
