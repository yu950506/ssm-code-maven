package cn.yhs.learn.service;

import cn.yhs.learn.domain.Account;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.learn.service.AccountService
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/6/1 21:20
 * @Description: todo
 **/
public interface AccountService {
    void update(Account account);

    void delete(Integer id);

    Account findById(Integer id);

    void transfer(Account source, Account dest, Double money);
}
