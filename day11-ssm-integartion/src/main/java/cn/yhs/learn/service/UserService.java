package cn.yhs.learn.service;

import cn.yhs.learn.domain.User;

import java.util.List;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.learn.service.UserService
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/6/7 9:53
 * @Description: todo
 **/
public interface UserService {

    List<User> findAll();

    User findById(Integer id);

    void save(User user);

}
