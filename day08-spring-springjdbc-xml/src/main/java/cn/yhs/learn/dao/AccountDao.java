package cn.yhs.learn.dao;

import cn.yhs.learn.domain.Account;

import java.util.List;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.learn.dao.AccountDao
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/6/2 18:19
 * @Description: todo
 **/
public interface AccountDao {

    Integer findTotal();

    Account findById(Integer id);

    List<Account> findAll();

    void update(Account account);


}
