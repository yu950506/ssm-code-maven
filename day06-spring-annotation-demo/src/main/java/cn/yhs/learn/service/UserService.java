package cn.yhs.learn.service;

import cn.yhs.learn.domain.User;

import java.util.List;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.learn.service.UserService
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/5/29 14:47
 * @Description: todo
 **/
public interface UserService {
    /**
     * 查询素有用户
     *
     * @return
     */
    List<User> findAllUser();

    /**
     * 根据用户id查询用户
     *
     * @return
     */
    User findUserById();

    /**
     * 增加用户
     *
     * @param User
     */
    void insertUser(User User);

    /**
     * 删除用户
     *
     * @param id
     */
    void deleteUser(Integer id);

    /**
     * 修改用户
     *
     * @param user
     */
    void UpdateUser(User user);
}
