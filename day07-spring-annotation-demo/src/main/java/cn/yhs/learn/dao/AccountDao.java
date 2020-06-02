package cn.yhs.learn.dao;

import cn.yhs.learn.domain.Account;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.learn.dao.AccoutDao
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/6/1 21:18
 * @Description: todo
 **/
public interface AccountDao {

    void update(Account account);

    void delete(Integer id);

    Account findById(Integer id);

}
