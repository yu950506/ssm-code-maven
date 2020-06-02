# 转账案例-基于Spring声明式事务的控制（XML配置）

## 1.项目介绍

通过前面几个项目我们知道了在业务层对事务进行控制，我们通过采用动态代理的方式、通过AOP自己定义事务的方式都实现了事务的控制。这里我们通过Spring为我们提供的一组事务控制的接口再来实现对事务的控制。

## 2.项目主要技术

- Spring-JdbcTemplate+Spring自带的数据源实现Dao层
- Spring事务控制接口实现Service层逻辑控制
- 基于注解的方式配置bean.xml和AOP的配置
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

import java.util.List;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.learn.service.impl.AccountServiceImpl
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/6/2 18:48
 * @Description: todo
 **/
public class AccountServiceImpl implements AccoutService {
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
    public void transform(Account source, Account dest, Double money) {
        source.setMoney(source.getMoney() - money);
        accountDao.update(source);
        // 人为制造异常
        int i = 1 / 0;
        dest.setMoney(dest.getMoney() + money);
        accountDao.update(dest);

    }

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }
}
```

### 3.4 bean.xml（配置事务重难点）

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context" xmlns:tx="http://www.springframework.org/schema/tx"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx.xsd http://www.springframework.org/schema/aop https://www.springframework.org/schema/aop/spring-aop.xsd">

    <context:component-scan base-package="cn.yhs.learn"></context:component-scan>
    <context:property-placeholder location="jdbc.properties"></context:property-placeholder>

    <!--使用Spring内置的数据源-->
    <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
        <property name="driverClassName" value="${jdbc.driverClass}"></property>
        <property name="url" value="${jdbc.jdbcUrl}"></property>
        <property name="username" value="${jdbc.user}"></property>
        <property name="password" value="${jdbc.password}"></property>

    </bean>

    <bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
        <property name="dataSource" ref="dataSource"></property>
    </bean>

    <!--配置dao层-->
    <bean id="accountDao" class="cn.yhs.learn.dao.impl.AccountDaoImpl">
        <property name="template" ref="jdbcTemplate"></property>
    </bean>
    <!--配置service层-->
    <bean id="accountService" class="cn.yhs.learn.service.impl.AccountServiceImpl">
        <property name="accountDao" ref="accountDao"></property>
    </bean>

    <!-- ***************  配置事务开始 ******************** -->
    <!--1.配置事务管理器-->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"></property>
    </bean>
    <!--2.配置事务的通知引用事务管理器-->
    <tx:advice transaction-manager="transactionManager" id="txAdvice">
        <!--3.配置事务的属性-->
        <tx:attributes>
            <!--
                read-only:是否是只读事务，默认是false,不只读
                isolation:事务的隔离级别：默认值就是数据库的默认隔离级别
                propagation:指定事务的传播行为
                timeout:指定超时时间，默认值是-1,。永不超时
                no-rollback-for:用于指定一个异常，当产生改异常时，事务不回滚，产生其他异常，事务回滚，没有默认值，任何异常都回滚
                rollback-for:用于指定一个异常，当执行该异常时，事务回滚，产生其他异常，事务不会滚，没有默认值，没有任何回滚
            -->
            <tx:method name="*" read-only="false" propagation="REQUIRED"/>
            <tx:method name="find*" read-only="true" propagation="SUPPORTS"/>
        </tx:attributes>
    </tx:advice>
    <!--4.配置AOP切入点表达式-->
    <aop:config>
        <aop:pointcut id="pt1" expression="execution(* cn.yhs.learn.service.*.*(..))"></aop:pointcut>
        <!--5.配置切入点表达式和事务通知的对应关系-->
        <aop:advisor advice-ref="txAdvice" pointcut-ref="pt1"></aop:advisor>
    </aop:config>
    <!--6.开启spring对事务的支持-->
    <tx:annotation-driven transaction-manager="transactionManager"></tx:annotation-driven>
    <!-- ***************  配置事务结束 ******************** -->

</beans>
```

### 3.5 测试类

```java
import cn.yhs.learn.domain.Account;
import cn.yhs.learn.service.AccoutService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
@ContextConfiguration(locations = "classpath:bean.xml")
public class SpringTest {

    @Autowired
    private AccoutService accoutService;

    @Test
    public void test() {
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
        accoutService.transform(source,dest,money);
    }

}
```

### 4.总结

通过对Spring自带的事务接口的配置，让我们对事务的控制更加简单方便。

