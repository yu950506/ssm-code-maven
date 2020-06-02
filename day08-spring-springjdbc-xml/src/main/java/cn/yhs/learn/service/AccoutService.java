package cn.yhs.learn.service;

import cn.yhs.learn.domain.Account;

import java.util.List;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.learn.service.AccoutService
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/6/2 18:48
 * @Description: todo
 **/
public interface AccoutService {
    Integer findTotal();

    Account findById(Integer id);

    List<Account> findAll();

    void update(Account account);

    void transform(Account source, Account dest, Double money);
}
