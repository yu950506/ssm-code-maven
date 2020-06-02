# 转账案例-基于AOP切面事务控制（全注解配置）

## 1.项目介绍

一般我们在业务层都会对事务进行控制操作，前面我们讲解了[基于动态代理的方式](./../day07-spring-xml-proxy/README.md)进行事务控制，还讲解了借助S[pring框架的AOP(基于XML配置)](./../day07-spring-xml-aop/README.md)来实现对事务的控制，而这个讲解的是Spring框架基于纯注解的方式来实现事务的控制。

## 2.项目主要技术

- c3p0数据库连接池+Dbutils来实现Dao层业务逻辑
- AOP+线程池(保证dao层都在一个连接中)+事务控制类来实现Service层逻辑控制(重难点)
- 基于全注解的方式配置和AOP的配置(有BUG)
- Spring和Junit的整合

### 3.项目主要代码：

### 3.1 pom.xml

```xml
 <dependencies>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>5.2.0.RELEASE</version>
        </dependency>
        <!--面向切面编程必须加的jar-->
        <!-- https://mvnrepository.com/artifact/org.springframework/spring-aspects -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-aspects</artifactId>
            <version>5.2.0.RELEASE</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/org.springframework/spring-test -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-test</artifactId>
            <version>5.0.8.RELEASE</version>
            <scope>test</scope>
        </dependency>
        <!--操作数据库要用的-->
        <!-- https://mvnrepository.com/artifact/commons-dbutils/commons-dbutils -->
        <dependency>
            <groupId>commons-dbutils</groupId>
            <artifactId>commons-dbutils</artifactId>
            <version>1.6</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/com.mchange/c3p0 -->
        <dependency>
            <groupId>com.mchange</groupId>
            <artifactId>c3p0</artifactId>
            <version>0.9.5.2</version>
        </dependency>
    </dependencies>
```

### 3.2 配置层

#### 3.2.1 数据库配置

```java
package cn.yhs.learn.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import javax.sql.DataSource;
import java.beans.PropertyVetoException;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.learn.config.C3P0JdbcConfig
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/6/2 11:07
 * @Description: todo
 **/
@PropertySource(value = "classpath:c3p0.properties")
//用于加载properties文件中的配置， value属性用于指定文件所在位置，需要在前面加classpath
public class C3P0JdbcConfig {
    @Value("${c3p0.jdbc.driverClass}") // 读取相应properties文件中的key,并将value注入进去
    private String driverClass;
    @Value("${c3p0.jdbc.jdbcUrl}")
    private String jdbcUrl;
    @Value("${c3p0.jdbc.user}")
    private String user;
    @Value("${c3p0.jdbc.password}")
    private String password;

    @Bean(name = "dataSource")//该注解只能写在方法上，表名使用此方法创建一个对象，并且放入Spring容器,不写name属性默认是当前方法名
    public DataSource getC3p0DataSource() {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        try {
            dataSource.setDriverClass(driverClass);
            dataSource.setJdbcUrl(jdbcUrl);
            dataSource.setUser(user);
            dataSource.setPassword(password);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        return dataSource;
    }

    @Bean(name = "queryRunner")
    public QueryRunner getQueryRunner(DataSource dataSource) {
        QueryRunner queryRunner = new QueryRunner(dataSource);
        return queryRunner;
    }

}
```

#### 3.2.2 Spring配置

```java
package cn.yhs.learn.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.learn.config.SpringConfig
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/6/2 11:06
 * @Description: todo
 **/
@Configuration //用于加载配置
@ComponentScan(basePackages = "cn.yhs.learn") // 加了注解的基本包的扫描
@Import(C3P0JdbcConfig.class) // 导入配置文件
@EnableAspectJAutoProxy // 开启切面通知，AOP配置必须加的
public class SpringConfig {
}

```

### 3.3 工具层

#### 3.3.1 事务控制（基于注解的通知执行顺序和XML配置不一值，有BUG,推荐使用环绕通知）

```java
package cn.yhs.learn.util;

import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.learn.util.TransactionManagerUtils
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/6/1 21:36
 * @Description: todo
 **/
@Component // 容器里面的组件，让Spring管理，没有写value,默认是transactionManagerUtils
@Aspect // 表示当前类是一个切面类
public class TransactionManagerUtils {
    @Autowired
    private ConnectionUtils connectionUtils;
    // 配置切入点表达式
    @Pointcut(value = "execution(* cn.yhs.learn.service.*.*(..))")
    private void transform(){};

    /**
     * 开启事务,设置自动提交为false
     */
    @Before("transform()") // 前置通知，值可以自己写表达式，也可以引入切入点表达式
    public void begin() {
        try {
            System.out.println("TransactionManagerUtils.begin");
            connectionUtils.getThreadLocalConnect().setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 提交事务
     */
    @AfterReturning("transform()")// 后置通知，值可以自己写表达式，也可以引入切入点表达式
    public void commit() {
        try {
            System.out.println("TransactionManagerUtils.commit");
            connectionUtils.getThreadLocalConnect().commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 回滚事务
     */
    @AfterThrowing("transform()")//异常通知，值可以自己写表达式，也可以引入切入点表达式
    public void rollback() {
        try {
            System.out.println("TransactionManagerUtils.rollback");
            connectionUtils.getThreadLocalConnect().rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 释放资源
     */
    @After("transform()")//最终通知，值可以自己写表达式，也可以引入切入点表达式
    public void release() {
        try {
            // 关闭连接
            System.out.println("TransactionManagerUtils.release");
            connectionUtils.getThreadLocalConnect().close();
            // 当前连接和线程池中解绑
            connectionUtils.remove();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
/*
/**
 *  todo : Spring基于纯注解配置通知类型是是有BUG的，执行顺序和我们基于XML配置的通知类型的执行顺序是有问题，推荐使用环绕通知，
 * TransactionManagerUtils.begin
 * TransactionManagerUtils.commit
 * TransactionManagerUtils.release
 * TransactionManagerUtils.begin
 * TransactionManagerUtils.commit
 * TransactionManagerUtils.rollback
 *
 */

```

#### 3.3.2 线程池+数据库连接

```java
package cn.yhs.learn.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.learn.util.ConnectionUtils
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/6/1 21:31
 * @Description: todo
 **/

@Component
public class ConnectionUtils {
    private ThreadLocal<Connection> tl = new ThreadLocal<Connection>();
    @Autowired
    private DataSource dataSource;

    //确保当前线程只有一个连接
    public Connection getThreadLocalConnect() {
        Connection connection = tl.get();
        try {
            if (connection == null) {
                connection = dataSource.getConnection();
                tl.set(connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    // 连接和线程解绑
    public void remove() {
        tl.remove();
    }
}
```

### 3.4 业务层代码

```java
package cn.yhs.learn.service.impl;

import cn.yhs.learn.dao.AccountDao;
import cn.yhs.learn.domain.Account;
import cn.yhs.learn.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.learn.service.impl.AccountServiceImpl
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/6/1 21:21
 * @Description: todo
 **/

@Service("accountService")
public class AccountServiceImpl implements AccountService {
    @Autowired // 使用自动注入就不需要set方法
    private AccountDao accountDao;

    public void update(Account account) {
        accountDao.update(account);
    }

    public void delete(Integer id) {
        accountDao.delete(id);
    }

    public Account findById(Integer id) {
        return accountDao.findById(id);
    }

    // 转账的方法
    public void transfer(Account source, Account dest, Double money) {
        source.setMoney(source.getMoney() - money);
        accountDao.update(source);
        // 人为的制造异常
        int i = 1 / 0;
        dest.setMoney(dest.getMoney() + money);
        accountDao.update(dest);
    }

}

```

### 3.5 持久层代码

```java
package cn.yhs.learn.dao.impl;

import cn.yhs.learn.dao.AccountDao;
import cn.yhs.learn.domain.Account;
import cn.yhs.learn.util.ConnectionUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.learn.dao.impl.AccountDaoImpl
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/6/1 21:22
 * @Description: todo
 **/
@Repository("accountDao")
public class AccountDaoImpl implements AccountDao {
    @Autowired
    private QueryRunner queryRunner;
    @Autowired
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
            account = queryRunner.query(connectionUtils.getThreadLocalConnect(), sql, new BeanHandler<Account>(Account.class), id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;

    }

}

```

### 3.6 测试类

```java
import cn.yhs.learn.domain.Account;
import cn.yhs.learn.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @ProjectName: ssm-code-maven
 * @Name: PACKAGE_NAME.SpringTest
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/6/2 11:42
 * @Description: todo
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = cn.yhs.learn.config.SpringConfig.class)
public class SpringTest {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private AccountService accountService;


    @Test
    public void test(){
        String[] names = context.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }

    }

    @Test
    public void test2(){
        Double money = 200.0;
        Account source = accountService.findById(1);
        Account dest = accountService.findById(2);
        accountService.transfer(source,dest,money);

    }


}

```

### 4  总结

全注解配置是很简单，但是要属性每个注解的含义及书写形式。最好对比xml配置的形式类对比学习。

