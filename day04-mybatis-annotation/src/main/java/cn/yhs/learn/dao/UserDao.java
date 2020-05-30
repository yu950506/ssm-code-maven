package cn.yhs.learn.dao;

import cn.yhs.learn.domain.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.learn.dao.UserDao
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/5/27 17:37
 * @Description: 基于注解的方式配置mybatis
 **/
@CacheNamespace(blocking = true)
public interface UserDao {
    /**
     * 查询所有用户
     *
     * @return 返回用户的集合
     */
    @Select("select * from user;")
/*    @Results(id = "uMap", value = {
            @Result(id = true, column = "id", property = "uid"),
            @Result(column = "username", property = "uusername"),
            @Result(column = "birthday", property = "ubirthday"),
            @Result(column = "sex", property = "usex"),
            @Result(column = "address", property = "uaddress")
    })*/
    List<User> findAllUser();


    @Select("select * from user where id = #{id}")
        /*    @ResultMap("uMap")*/
    User findUserById(Integer id);


    @Select("select * from user where username like #{username}")
        /*    @ResultMap(value = "uMap")*/
    List<User> findUserByUserName(String username);

    @Select("select count(id) from user")
    Integer findUserTotal();

    /**
     * 保存一个用户
     *
     * @param user 传入有个用户对象
     */
    @Insert("insert into user(username,birthday,sex,address) values (#{username},#{birthday},#{sex},#{address})")
    void SaveUser(User user);

    @Update("update user set username = #{username} , birthday = #{birthday} , sex = #{sex} , address = #{address} where id = #{id}")
    void updateUser(User user);

    @Delete("delete from user where id = #{id}")
    void deleteUser(Integer id);

    @Select("select * from user")
    @Results(id = "user1:n", value = {@Result(id = true, property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "sex", column = "sex"),
            @Result(property = "address", column = "address"),
            /*1:N 的时候建议使用懒加载*/
            @Result(property = "accounts", column = "id", many = @Many(select = "cn.yhs.learn.dao.AccountDao.findAllByUid", fetchType = FetchType.LAZY))
    })
    List<User> findAllUserWithUser();


}
