package cn.yhs.learn.service.impl;

import cn.yhs.learn.dao.UserDao;
import cn.yhs.learn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.learn.service.impl.UserServiceImle
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/5/29 10:19
 * @Description: service 层的实现类
 **/
@Service
public class UserServiceImpl implements UserService {
    /**
     * @Qualifier 类似于<property       name       =       "   "           ref       =       "   "       />
     * 自动按照类型注入，当使用注解注入属性时，set方法是可以省略的，它只能注入其他bean类型，
     * 当有多个类型匹配的时候，使用要注入的对象的变量名称的作为bean的id，在spring中查找，
     * 找到了也就注入成功，找不到就报错。
     */
    // @Autowired
    //  @Qualifier(value = "userDaoImpl2") // 当有多个类型时，用于指定id,必须和@AutoWire 一起使用，但是给方法参数注入时，可以单独使用
    @Resource(name = "userDaoImpl2") // 直接按照bean的id注入，作用类似于上面两个标签@Autowired @Qualifier(value = "userDaoImpl2")组合的作用
    private UserDao userDao;

    public void save() {
        System.out.println("UserServiceImpl.save ... service层的方法");
        userDao.save();
    }
}
