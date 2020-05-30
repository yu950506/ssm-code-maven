package cn.yhs.learn.dao;

import cn.yhs.learn.domain.Account;

import java.util.List;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.learn.dao.AccountDao
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/5/27 11:26
 * @Description: todo
 **/
public interface AccountDao {
    /**
     * 查询所有账户信息，并带着用户的信息，这里是一个1:1的关系查询
     *
     * @return
     */
    List<Account> findAllAccountWithUser();

    List<Account> findByUid(Integer uid);
}
