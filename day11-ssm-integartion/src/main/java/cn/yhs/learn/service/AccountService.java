package cn.yhs.learn.service;

import cn.yhs.learn.domain.Account;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.learn.service.AccountService
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/6/7 10:16
 * @Description: todo
 **/
public interface AccountService {
    Account findById(Integer id);

    void update(Account account);

    void transform(Account source, Account dest, Double money);
}
