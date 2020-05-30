package cn.yhs.learn.dao;

import cn.yhs.learn.domain.User;

import java.util.List;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.dao.UserDao
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/5/26 16:15
 * @Description: todo
 **/
public interface UserDao {
    /**
     * 查询用户，并附带用户的角色信息
     *
     * @return
     */
    List<User> findUserWithRole();
}
