# 基于注解的方式使用Mybatis的CRUD
## 1. 演示单表的操作 
+ 查询 `@Select()`
> 1. 查询所有用户 **`@Select("select * from user;")`** 
> 2. 根据id查询单个用户 **`@Select("select * from user where id = #{id}")`**
> 3. 模糊查询 **`@Select("select * from user where username like #{username}")`**
> 4. 查询总记录数 **`@Select("select count(id) from user")`**
+ 插入 
> 1. 查入一个用户 **`@Insert("insert into user(username,birthday,sex,address) values (#{username},#{birthday},#{sex},#{address})")`**
+ 更新 
> 1. 更新用户 **`@Update("update user set username = #{username} , birthday = #{birthday} , sex = #{sex} , address = #{address} where id = #{id}")`**
+ 删除 
> 1. 删除一个用户 **`@Delete("delete from user where id = #{id}")`**
## 2. 数据库字段名称和实体类名称不对应的问题解决
> 1. 使用 `@Results @Result `进行映射
> 2. 
       @Results(id = "uMap", value = {
            @Result(id = true, column = "id", property = "uid"),
            @Result(column = "username", property = "uusername"),
            @Result(column = "birthday", property = "ubirthday"),
            @Result(column = "sex", property = "usex"),
            @Result(column = "address", property = "uaddress")
        })
    
 > 3. `@ResultMap("uMap")` 引用上面的结果集

## 3. 演示多表操作 1:1，可以设定是否延迟加载
> 1. 一个账户属于一个用户

    @Select("select * from account")
    @Results(id = "accMap", value = {
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "uid", property = "uid"),
            @Result(column = "money", property = "money"),
            /*LAZY, EAGER, DEFAULT 1:1 建议使用EAGER*/
            @Result(column = "uid", property = "user", one = @One(select = "cn.yhs.learn.dao.UserDao.findUserById", fetchType = FetchType.EAGER))
    })
    List<Account> findAllAccount();


## 4. 演示多表操作 1:N，可以设定是否延迟加载
> 1. 一个用户可以有多个账户

    @Select("select * from user")
    @Results(id = "user1:n", value = {@Result(id = true, property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "sex", column = "sex"),
            @Result(property = "address", column = "address"),
            /*1:N 的时候建议使用懒加载*/
            @Result(property = "accounts", column = "id", many = @Many(select = "cn.yhs.learn.dao.AccountDao.findAllByUid", fetchType = FetchType.LAZY))
    })
    List<User> findAllUserWithUser();
    
## 5. 配置缓存（一级缓存，二级缓存）
> 1. 一级缓存是sqlSession级别的，默认是开启的

        User user = userDao.findUserById(50);
        System.out.println(user);
      // 只发生了一次查询 select * from user where id = ?
        User user2 = userDao.findUserById(50);
        System.out.println(user2);
        System.out.println(user == user2);// true
> 2. 二级缓存是sqlSessionFactory级别的，需要手动开启
>>   2.1 配置文件使用二级缓存，默认是开启的，这里可以不设置
<!--    <settings>
              <setting name="cacheEnabled" value="true"/>
          </settings>
 -->
>> 2.2 在Dao上加上注解，@CacheNamespace(blocking = true) 开启缓存

    @CacheNamespace(blocking = true)
    public interface UserDao {
>> 2.3 


         SqlSession sqlSession = sqlSessionFactory.openSession();
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        User user1 = mapper.findUserById(50);
        System.out.println(user1);
        sqlSession.close(); // 释放一级缓存
        /**
         * 发送了两次sql语句,开启缓存之后就只有一条sql
         */
        SqlSession sqlSession2 = sqlSessionFactory.openSession();
        UserDao mapper2 = sqlSession2.getMapper(UserDao.class);
        User user2 = mapper2.findUserById(50);
        System.out.println(user2);
        System.out.println(user1 == user2);// false，不一样是因为序列化成文本，再反序列化成一个新的对象
        sqlSession2.close();
 
