package cn.yhs.learn.dao;

import cn.yhs.learn.domain.Account;
import cn.yhs.learn.domain.AccountUser;
import cn.yhs.learn.domain.User;

import java.util.List;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.learn.dao.AccountDao
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/5/26 11:09
 * @Description: todo
 **/
public interface AccountDao {
    /**
     * 查询所有账号
     *
     * @return
     */
    List<Account> findAll();

    /**
     * 查询账户信息，并附带账户的姓名和地址 -- 方式1 采用继承的方式来实现，花销太大，代码的耦合度也高
     *
     * @return
     */
    List<AccountUser> findAllAccountUser();

    /**
     * 查询账户信息，并附带账户的姓名和地址
     * 方式二：使用resultMap，定义专门的resultMap用于映射1对1查询的结果
     *
     * @return
     */
    List<Account> findAllAccountUser2();


}
