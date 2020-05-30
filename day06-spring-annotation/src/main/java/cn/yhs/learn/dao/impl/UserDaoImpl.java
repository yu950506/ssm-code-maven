package cn.yhs.learn.dao.impl;

import cn.yhs.learn.dao.UserDao;
import org.springframework.stereotype.Repository;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.learn.dao.impl.UserDaoImpl
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/5/29 10:19
 * @Description: todo
 **/
@Repository("userDaoImpl")
public class UserDaoImpl implements UserDao {
    public void save() {
        System.out.println("UserDaoImpl.save ... 保存用户");
    }
}
