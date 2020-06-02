package cn.yhs.learn.service.impl;

import cn.yhs.learn.dao.AccountDao;
import cn.yhs.learn.domain.Account;
import cn.yhs.learn.service.AccoutService;

import java.util.List;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.learn.service.impl.AccountServiceImpl
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/6/2 18:48
 * @Description: todo
 **/
public class AccountServiceImpl implements AccoutService {
    private AccountDao accountDao;

    public Integer findTotal() {
        return accountDao.findTotal();
    }

    public Account findById(Integer id) {
        return accountDao.findById(id);
    }

    public List<Account> findAll() {
        return accountDao.findAll();
    }

    public void update(Account account) {
        accountDao.update(account);
    }

    /**
     * 转账的方法
     *
     * @param source 原账户
     * @param dest   目标账户
     * @param money  转账金额
     */
    public void transform(Account source, Account dest, Double money) {
        source.setMoney(source.getMoney() - money);
        accountDao.update(source);
        // 人为制造异常
        int i = 1 / 0;
        dest.setMoney(dest.getMoney() + money);
        accountDao.update(dest);

    }

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }
}
