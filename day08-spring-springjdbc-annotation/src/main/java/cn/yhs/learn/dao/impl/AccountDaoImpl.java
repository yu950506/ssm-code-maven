package cn.yhs.learn.dao.impl;

import cn.yhs.learn.dao.AccountDao;
import cn.yhs.learn.domain.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.learn.dao.impl.AccountDaoImpl
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/6/2 18:21
 * @Description: todo
 **/
@Repository(value = "accountDao")
public class AccountDaoImpl implements AccountDao {

    @Autowired
    private JdbcTemplate template; // 使用注解的方式配置JdbcTemplate

    public Integer findTotal() {
        String sql = "select count(*) from account";
        Integer total = template.queryForObject(sql, Integer.class);// 查询的结果集是一行一列
        return total;
    }

    public Account findById(Integer id) {
        String sql = "select * from account where id = ?";
        Account account = template.queryForObject(sql, new BeanPropertyRowMapper<Account>(Account.class), id);
        return account;
    }

    public List<Account> findAll() {
        String sql = "select * from account";
        List<Account> accountList = template.query(sql, new BeanPropertyRowMapper<Account>(Account.class));
        return accountList;
    }

    public void update(Account account) {
        String sql = "update account set money = ? where id = ?";
        template.update(sql, account.getMoney(), account.getId());
    }

}
