package cn.yhs.learn.service.impl;

import cn.yhs.learn.dao.AccountDao;
import cn.yhs.learn.domain.Account;
import cn.yhs.learn.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.learn.service.impl.AccountService
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/6/7 10:17
 * @Description: todo
 **/
@Service("accountService")
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountDao accountDao;

    @Override
    public Account findById(Integer id) {
        return accountDao.findById(id);
    }

    @Override
    public void update(Account account) {
        accountDao.update(account);
    }

    /*模拟转账的方法*/
    @Override
    public void transform(Account source, Account dest, Double money) {
        source.setMoney(source.getMoney() - money);
        accountDao.update(source);
        int i = 1 / 0;
        dest.setMoney(dest.getMoney() + money);
        accountDao.update(dest);
    }
}
