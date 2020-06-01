package cn.yhs.learn.service.impl;

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
public class AccountServiceImpl implements AccountService {
    private AccountDao accountDao;
    private TransactionManager transactionManager; // 对每一个方法添加事务操作

    public void setTransactionManager(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public void updateAccount(Account account) {
        try {
            // 每一个操作都应该加上
            //1. 开启事务
            transactionManager.beginTransaction();
            //2. 执行造作
            accountDao.updateAccount(account);
            //3. 提交事务
            transactionManager.commit();
            //4. 返回结果,没有就不返回

        } catch (Exception e) {
            //5. 回滚事务
            transactionManager.rollback();
            e.printStackTrace();
        } finally {
            //6. 释放资源连接
            transactionManager.release();
        }

    }

    public List<Account> findAll() {
        try {
            // 每一个操作都应该加上
            //1. 开启事务
            transactionManager.beginTransaction();
            //2. 执行造作
            List<Account> accountList = accountDao.findAll();
            //3. 提交事务
            transactionManager.commit();
            //4. 返回结果,没有就不返回
            return accountList;
        } catch (Exception e) {
            //5. 回滚事务
            transactionManager.rollback();
            e.printStackTrace();
            return null;
        } finally {
            //6. 释放资源连接
            transactionManager.release();
        }


    }

    public Account findById(Integer id) {
        try {
            // 每一个操作都应该加上
            //1. 开启事务
            transactionManager.beginTransaction();
            //2. 执行造作
            Account account = accountDao.findById(id);
            //3. 提交事务
            transactionManager.commit();
            //4. 返回结果,没有就不返回
            return account;
        } catch (Exception e) {
            //5. 回滚事务
            transactionManager.rollback();
            e.printStackTrace();
            return null;
        } finally {
            //6. 释放资源连接
            transactionManager.release();
        }
    }

    /**
     * 业务层的代码
     *
     * @param source 源账户
     * @param dest   目标账户
     * @param money  转账金额
     */
    public void transfer(Account source, Account dest, Double money) {
        /*
        // 这里只演示事务，不考虑其他因素
        // 1. 源账户减钱，并更新
        source.setMoney(source.getMoney() - money);
        updateAccount(source);// 与数据库交互一次，获取一次连接，每一个连接都是一个独立的事务
        // todo  制造异常，演示不满足事务的一致性，为了满足，只能是同一个连接

        int a = 1 / 0;//java.lang.ArithmeticException: / by zero

        // 2. 目标账户加钱，并更新
        dest.setMoney(dest.getMoney() + money);
        updateAccount(dest);// 与数据库又交互一次，获取一次连接
*/
        try {
            // 每一个操作都应该加上
            //1. 开启事务
            transactionManager.beginTransaction();
            //2. 执行造作
            source.setMoney(source.getMoney() - money);
            //updateAccount(source);// 与数据库交互一次，获取一次连接，每一个连接都是一个独立的事务
            accountDao.updateAccount(source);
            // todo  制造异常，演示不满足事务的一致性，为了满足，只能是同一个连接
            int a = 1 / 0;//java.lang.ArithmeticException: / by zero
            // 2. 目标账户加钱，并更新
            dest.setMoney(dest.getMoney() + money);
            // updateAccount(dest);// 与数据库又交互一次，获取一次连接
            accountDao.updateAccount(dest);
            //3. 提交事务
            transactionManager.commit();
            //4. 返回结果
        } catch (Exception e) {
            //5. 回滚事务
            transactionManager.rollback();
            e.printStackTrace();
        } finally {
            //6. 释放资源连接
            transactionManager.release();
        }


    }

    public void deleteById(Integer id) {
        try {
            // 每一个操作都应该加上
            //1. 开启事务
            transactionManager.beginTransaction();
            //2. 执行造作
            accountDao.deleteById(id);
            //3. 提交事务
            transactionManager.commit();
            //4. 返回结果

        } catch (Exception e) {
            //5. 回滚事务
            transactionManager.rollback();
            e.printStackTrace();
        } finally {
            //6. 释放资源连接
            transactionManager.release();
        }
    }

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }
}
