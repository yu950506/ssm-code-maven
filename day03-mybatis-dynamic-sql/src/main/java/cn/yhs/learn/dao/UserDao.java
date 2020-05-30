package cn.yhs.learn.dao;

import cn.yhs.learn.domain.User;

import java.util.List;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.learn.dao.UserDao
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/5/26 9:14
 * @Description: todo
 **/
public interface UserDao {
    /**
     * 按照user里面可能存在的条件进行查询
     *
     * @param user
     * @return
     */
    List<User> findByCondition(User user);

    /**
     * 查询所有
     *
     * @return
     */
    List<User> findAll();

    /**
     * 按照 id 范围进行查询
     *
     * @param ids
     * @return
     */
    List<User> findByIds(List<Integer> ids);

    /**
     * 使用foreach 完成批量插入
     *
     * @param users
     * @return
     */
    int insertAll(List<User> users);


    int deleteAll(List<Integer> ids);

    /**
     * 查询一对多的关系
     * @return
     */
    List<User> findAllUserAccount();

}
