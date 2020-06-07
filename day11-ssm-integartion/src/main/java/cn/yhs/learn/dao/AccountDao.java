package cn.yhs.learn.dao;

import cn.yhs.learn.domain.Account;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.learn.dao.AccountDao
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/6/7 10:14
 * @Description: todo
 **/
@Repository("accountDao")
public interface AccountDao {

    @Select("select * from account where id = #{id}")
    Account findById(Integer id);

    @Update("update account set money = #{money} where id = #{id}")
    void update(Account account);


}
