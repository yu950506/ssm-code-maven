# 转账案例-基于Spring声明式事务的控制（全注解配置）

## 1.项目介绍

通过前面几个项目我们知道了在业务层对事务进行控制，我们通过采用动态代理的方式、通过AOP自己定义事务的方式都实现了事务的控制。这里我们通过Spring为我们提供的一组事务控制的接口再来实现对事务的控制。

## 2.项目主要技术

- Spring-JdbcTemplate+Spring自带的数据源实现Dao层
- Spring事务控制接口实现Service层逻辑控制
- 基于全注解的方式配置
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
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-jdbc</artifactId>
            <version>5.2.0.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-test</artifactId>
            <version>5.2.0.RELEASE</version>
        </dependency>
    </dependencies>
```

### 3.2 Dao层

```java
package cn.yhs.learn.dao.impl;

import cn.yhs.learn.dao.AccountDao;
import cn.yhs.learn.domain.Account;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.learn.dao.impl.AccountDaoImpl
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/6/2 18:21
 * @Description: todo
 **/
public class AccountDaoImpl implements AccountDao {

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

    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }
}
```

### 3.3 Service层

```java
package cn.yhs.learn.service.impl;

import cn.yhs.learn.dao.AccountDao;
import cn.yhs.learn.domain.Account;
import cn.yhs.learn.service.AccoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.learn.service.impl.AccountServiceImpl
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/6/2 18:48
 * @Description: todo
 **/
@Service(value = "accountService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
/*
@Transactional该注解的属性和 xml 中的属性含义一致。该注解可以出现在接口上，类上和方法上。
                出现接口上，表示该接口的所有实现类都有事务支持。
                出现在类上，表示类中所有方法有事务支持
                出现在方法上，表示方法有事务支持。
                以上三个位置的优先级：方法 > 类 > 接口
 */
public class AccountServiceImpl implements AccoutService {
    @Autowired
    private AccountDao accountDao;

    public Integer findTotal() {
        return accountDao.findTotal();
    }

    public Account findById(Integer id) {
        return accountDao.findById(id);
    }

    public List<Account> findAll() {
        return accountDao.findAll();
    }

    public void update(Account account) {
        accountDao.update(account);
    }

    /**
     * 转账的方法
     *
     * @param source 原账户
     * @param dest   目标账户
     * @param money  转账金额
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED) // 开启事务
    public void transform(Account source, Account dest, Double money) {
        source.setMoney(source.getMoney() - money);
        accountDao.update(source);
        // 人为制造异常
       // int i = 1 / 0;
        dest.setMoney(dest.getMoney() + money);
        accountDao.update(dest);

    }

}
```

### 3.4 配置层（配置事务重难点）

#### 3.4.1 jdbc配置

```java
package cn.yhs.learn.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.learn.config.JdbcConfig
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/6/2 19:59
 * @Description: todo
 **/
@PropertySource(value = "classpath:jdbc.properties")
public class JdbcConfig {
    @Value("${jdbc.driverClass}")
    String driverClassName;
    @Value("${jdbc.jdbcUrl}")
    String url;
    @Value("${jdbc.user}")
    String username;
    @Value("${jdbc.password}")
    String password;

    @Bean(name = "dataSource") // 配饰数据源
    public DataSource getDataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName(driverClassName);
        driverManagerDataSource.setUrl(url);
        driverManagerDataSource.setUsername(username);
        driverManagerDataSource.setPassword(password);
        return driverManagerDataSource;
    }

    @Bean(name = "template")
    public JdbcTemplate getJdbcTemplate(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate;
    }

}
```

#### 3.4.2  事务配置

```java
package cn.yhs.learn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.learn.config.TransactionManager
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/6/2 20:12
 * @Description: todo
 **/
@Component(value = "txManager")
public class TransactionManager {
    // 配置事务管理器
    @Bean(name = "transactionManager")
    public DataSourceTransactionManager getTransactionManager(DataSource dataSource) {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
        return transactionManager;
    }

}

```

#### 3.4.3 Spring配置

```java
package cn.yhs.learn.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.learn.config.SpringConfigration
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/6/2 19:58
 * @Description: todo
 **/
@Configuration
@ComponentScan(basePackages = "cn.yhs.learn")
@Import({JdbcConfig.class, TransactionManager.class})
@EnableTransactionManagement // 开启事务管理
public class SpringConfigration {
}

```

### 3.5 测试类

```java
import cn.yhs.learn.config.SpringConfigration;
import cn.yhs.learn.domain.Account;
import cn.yhs.learn.service.AccoutService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @ProjectName: ssm-code-maven
 * @Name: PACKAGE_NAME.SpingTest
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/6/2 18:53
 * @Description: todo
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfigration.class)
public class SpringTest {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private AccoutService accoutService;

    @Test
    public void test0() {
        String[] names = context.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println("name = " + name);
        }
    }


    @Test
    public void test1() {
        List<Account> accountList = accoutService.findAll();
        for (Account account : accountList) {
            System.out.println(account);
        }
    }

    @Test
    public void test2() {
        Integer total = accoutService.findTotal();
        System.out.println(total);
    }

    @Test
    public void test3() {
        Account account = accoutService.findById(1);
        System.out.println(account);
    }

    @Test
    public void test4() {
        Account source = accoutService.findById(1);
        source.setMoney(source.getMoney() + 100);
        accoutService.update(source);


    }

    // 测试转账
    @Test
    public void test5() {
        Account source = accoutService.findById(1);
        Account dest = accoutService.findById(2);
        Double money = 500.0;
        accoutService.transform(source, dest, money);
    }

}

```

### 4.总结

通过对Spring自带的事务接口的配置，而且又是全注解的方式，让我们对事务的控制更加简单方便。多要理解这些注解的配置含义，结核xml的一起看。

