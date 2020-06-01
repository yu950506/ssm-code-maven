package cn.yhs.learn.dao.impl;

import cn.yhs.learn.dao.AccountDao;
import cn.yhs.learn.domain.Account;
import cn.yhs.learn.util.ConnectionUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.learn.dao.impl.AccountDaoImpl
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/6/1 21:22
 * @Description: todo
 **/
public class AccountDaoImpl implements AccountDao {
    private QueryRunner queryRunner;
    private ConnectionUtils connectionUtils;

    public void update(Account account) {
        String sql = "update account set money = ? where id = ?";
        try {
            queryRunner.update(connectionUtils.getThreadLocalConnect(), sql, account.getMoney(), account.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void delete(Integer id) {
        String sql = "delete from account where id = ?";
        try {
            queryRunner.update(connectionUtils.getThreadLocalConnect(), sql, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Account findById(Integer id) {
        String sql = "select * from account where id = ?";
        Account account = null;
        try {
            account = queryRunner.query(connectionUtils.getThreadLocalConnect(), sql, new BeanHandler<Account>(Account.class),id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;

    }

    public void setQueryRunner(QueryRunner queryRunner) {
        this.queryRunner = queryRunner;
    }

    public void setConnectionUtils(ConnectionUtils connectionUtils) {
        this.connectionUtils = connectionUtils;
    }
}
