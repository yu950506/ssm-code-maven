package cn.yhs.learn.service;

import cn.yhs.learn.domain.Account;

import java.util.List;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.learn.service.AccountService
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/6/1 12:45
 * @Description: todo
 **/
public interface AccountService {

    void updateAccount(Account account);

    List<Account> findAll();

    Account findById(Integer id);

    /**
     *  转账的方法 源账户给目标账户转账
     * @param source 源账户
     * @param dest 目标账户
     * @param money 转账金额
     */
    void transfer(Account source,Account dest,Double money);

    void deleteById(Integer id);

}
