package cn.yhs.learn.service.impl2;

import cn.yhs.learn.dao.AccountDao;
import cn.yhs.learn.domain.Account;
import cn.yhs.learn.service.AccountService;
import cn.yhs.learn.util.TransactionManager;

import java.util.List;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.learn.service.impl.AccountServiceImpl
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/6/1 12:49
 * @Description: todo
 **/

/**
 * 事务控制都是在service层进行控制
 */
public class AccountServiceImpl2 implements AccountService {

    private AccountDao accountDao;

    public void updateAccount(Account account) {
        accountDao.updateAccount(account);
    }

    public List<Account> findAll() {
        List<Account> accountList = accountDao.findAll();
        return accountList;
    }

    public Account findById(Integer id) {
        Account account = accountDao.findById(id);
        return account;
    }

    /**
     * 业务层的代码
     *
     * @param source 源账户
     * @param dest   目标账户
     * @param money  转账金额
     */
    public void transfer(Account source, Account dest, Double money) {
        System.out.println("AccountServiceImpl2.transfer");
        source.setMoney(source.getMoney() - money);
        accountDao.updateAccount(source);
        int a = 1 / 0;//java.lang.ArithmeticException: / by zero
        dest.setMoney(dest.getMoney() + money);
        accountDao.updateAccount(dest);

    }

    public void deleteById(Integer id) {
        accountDao.deleteById(id);
    }


    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }
}
