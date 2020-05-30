package cn.yhs.learn.controller.impl;

import cn.yhs.learn.controller.UserController;
import cn.yhs.learn.domain.User;
import cn.yhs.learn.service.UserService;

import java.util.List;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.learn.controller.impl.UserControllerImpl
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/5/29 14:57
 * @Description: todo
 **/
public class UserControllerImpl implements UserController {
    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public List<User> findAllUser() {
        return userService.findAllUser();
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
