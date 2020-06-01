package cn.yhs.learn.dao.impl;

import cn.yhs.learn.dao.AccountDao;
import cn.yhs.learn.domain.Account;
import cn.yhs.learn.util.ConnectionUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.learn.dao.impl.AccountDaoImpl
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/6/1 12:46
 * @Description: todo
 **/
public class AccountDaoImpl implements AccountDao {

    private QueryRunner queryRunner;
    private ConnectionUtils connectionUtils;

    // 基于xml 配置需要生成set方法
    public void setQueryRunner(QueryRunner queryRunner) {
        this.queryRunner = queryRunner;
    }

    public void updateAccount(Account account) {
        String sql = "update account set money = ? where id = ?";
        try {
            // 使用我们自己的连接
            queryRunner.update(connectionUtils.getThreadLocalConnection(),sql, account.getMoney(), account.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询所有用户
     *
     * @return
     */
    public List<Account> findAll() {
        try {
            List<Account> accountList = queryRunner.query(connectionUtils.getThreadLocalConnection(),"select * from account", new BeanListHandler<Account>(Account.class));
            return accountList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Account findById(Integer id) {
        String sql = "select * from account where id = ?";
        try {
            Account account = queryRunner.query(connectionUtils.getThreadLocalConnection(),sql, new BeanHandler<Account>(Account.class), id);
            return account;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteById(Integer id) {
        String sql = "delete from account where id = ?";
        try {
            queryRunner.update(connectionUtils.getThreadLocalConnection(),sql, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setConnectionUtils(ConnectionUtils connectionUtils) {
        this.connectionUtils = connectionUtils;
    }
}
