package cn.yhs.learn.dao;

import cn.yhs.learn.domain.Account;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.learn.dao.AccountDao
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/5/27 20:18
 * @Description: todo
 **/
@CacheNamespace(blocking = true)
public interface AccountDao {
    @Select("select * from account")
    @Results(id = "accMap", value = {
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "uid", property = "uid"),
            @Result(column = "money", property = "money"),
            /*LAZY, EAGER, DEFAULT 1:1 建议使用EAGER*/
            @Result(column = "uid", property = "user", one = @One(select = "cn.yhs.learn.dao.UserDao.findUserById", fetchType = FetchType.EAGER))
    })
    List<Account> findAllAccount();

    @Select("select * from account where uid = #{uid}")
    List<Account> findAllByUid(Integer uid);
}
