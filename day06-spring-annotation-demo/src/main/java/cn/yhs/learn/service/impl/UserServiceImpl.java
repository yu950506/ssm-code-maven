package cn.yhs.learn.service.impl;

import cn.yhs.learn.dao.UserDao;
import cn.yhs.learn.domain.User;
import cn.yhs.learn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.learn.service.impl.UserServiceImp
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/5/29 14:55
 * @Description: todo
 **/

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    @Qualifier(value = "userDao")
    private UserDao userDao;


    public List<User> findAllUser() {
        /**
         * 这里调用Dao层的方法
         */
        return userDao.findAllUser();
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
