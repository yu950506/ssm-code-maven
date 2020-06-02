package cn.yhs.learn.service.impl;

import cn.yhs.learn.dao.AccountDao;
import cn.yhs.learn.domain.Account;
import cn.yhs.learn.service.AccoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.learn.service.impl.AccountServiceImpl
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/6/2 18:48
 * @Description: todo
 **/
@Service(value = "accountService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
/*
@Transactional该注解的属性和 xml 中的属性含义一致。该注解可以出现在接口上，类上和方法上。
                出现接口上，表示该接口的所有实现类都有事务支持。
                出现在类上，表示类中所有方法有事务支持
                出现在方法上，表示方法有事务支持。
                以上三个位置的优先级：方法 > 类 > 接口
 */
public class AccountServiceImpl implements AccoutService {
    @Autowired
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
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED) // 开启事务
    public void transform(Account source, Account dest, Double money) {
        source.setMoney(source.getMoney() - money);
        accountDao.update(source);
        // 人为制造异常
       // int i = 1 / 0;
        dest.setMoney(dest.getMoney() + money);
        accountDao.update(dest);

    }

}
