# 转账案例-基于动态代理的事务控制（XML配置）

## 1.项目介绍

一般我们在业务层都会对事务进行控制操作，这里我们讲解基于动态代理的方式进行事务控制。

## 2.项目主要技术

- c3p0数据库连接池+Dbutils来实现Dao层业务逻辑
- 动态代理+线程池(保证dao层都在一个连接中)+事务控制类来实现Service层逻辑控制(重难点)
- 基于注解的方式配置bean.xml

### 3.项目主要代码：

### 3.1 pom.xml

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-context</artifactId>
        <version>5.0.8.RELEASE</version>
    </dependency>

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

### 3.2 线程池

```java
/**
 * 获取连接的工具类
 */
public class ConnectionUtils {
    // 让当前连接与线程绑定，确保只有一个连接
    private ThreadLocal<Connection> tl = new ThreadLocal<Connection>();

    private DataSource dataSource;

    // 让spring进行配置注入
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    // 获取当前线程上的连接

    public Connection getThreadLocalConnection() {
        // 从线程池中获取连接
        try {
            Connection connection = tl.get();
            // 如果线程池中没有连接，就从连接池中获取一个，并存到线程池中去
            if (connection == null) {
                connection = dataSource.getConnection();
                tl.set(connection);
            }
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 连接与线程解绑
     */
    public void removeConnection() {
        tl.remove();
    }

}
```

### 3.3 事务控制

```java
/**
 * 基于事务管理控制，确保只有一个连接
 */
public class TransactionManager {
    private ConnectionUtils connectionUtils;

    public void setConnectionUtils(ConnectionUtils connectionUtils) {
        this.connectionUtils = connectionUtils;
    }

    public void beginTransaction() {
        try {
            connectionUtils.getThreadLocalConnection().setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void commit() {
        try {
            connectionUtils.getThreadLocalConnection().commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void rollback() {
        try {
            connectionUtils.getThreadLocalConnection().rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void release() {
        try {
            connectionUtils.getThreadLocalConnection().close();// 将AutoCommit = true
            connectionUtils.removeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Connection getConnection(){
        return connectionUtils.getThreadLocalConnection();
    }
}
```

### 3.4 动态代理类

```java
public class BeanFactory {
    // 代理对象，也就是目标对象
    private AccountService accountService;
    private TransactionManager transactionManager;

    // 返回代理对象
    public AccountService getAccountService() {

        return (AccountService) Proxy.newProxyInstance(accountService.getClass().getClassLoader(),
                accountService.getClass().getInterfaces(),
                new InvocationHandler() {
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                           // 对service层的每一个方法进行事务控制
                        try {
                            // 每一个方法都应该加上
                            //1. 开启事务
                            System.out.println("开启事务");
                            transactionManager.beginTransaction();
                            //2. 执行造作
                            System.out.println("方法名称"+method.getName());
                            Object returnVal = method.invoke(accountService, args);
                            //3. 提交事务
                            transactionManager.commit();
                            //4. 返回结果,没有就不返回
                            return returnVal;
                        } catch (Exception e) {
                            //5. 回滚事务
                            transactionManager.rollback();
                            e.printStackTrace();
                           return null;
                        } finally {
                            //6. 释放资源连接
                            transactionManager.release();
                        }
                    }
                }
        );

    }

    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    public void setTransactionManager(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }
}
```



### 3.5 业务层代码

```java
/**
 * 事务控制都是在service层进行控制
 */
public class AccountServiceImpl2 implements AccountService {

    private AccountDao accountDao;

    public void updateAccount(Account account) {
        accountDao.updateAccount(account);
    }

    public List<Account> findAll() {
        List<Account> accountList = accountDao.findAll();
        return accountList;
    }

    public Account findById(Integer id) {
        Account account = accountDao.findById(id);
        return account;
    }

    /**
     * 业务层的代码
     *
     * @param source 源账户
     * @param dest   目标账户
     * @param money  转账金额
     */
    public void transfer(Account source, Account dest, Double money) {
        System.out.println("AccountServiceImpl2.transfer");
        source.setMoney(source.getMoney() - money);
        accountDao.updateAccount(source);
        int a = 1 / 0;//java.lang.ArithmeticException: / by zero
        dest.setMoney(dest.getMoney() + money);
        accountDao.updateAccount(dest);

    }

    public void deleteById(Integer id) {
        accountDao.deleteById(id);
    }


    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }
}
```

### 3.6 持久层代码

```java
public class AccountDaoImpl implements AccountDao {

    private QueryRunner queryRunner;
    private ConnectionUtils connectionUtils;

    // 基于xml 配置需要生成set方法
    public void setQueryRunner(QueryRunner queryRunner) {
        this.queryRunner = queryRunner;
    }

    public void updateAccount(Account account) {
        String sql = "update account set money = ? where id = ?";
        try {
            // 使用我们自己的连接
            queryRunner.update(connectionUtils.getThreadLocalConnection(),sql, account.getMoney(), account.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询所有用户
     *
     * @return
     */
    public List<Account> findAll() {
        try {
            List<Account> accountList = queryRunner.query(connectionUtils.getThreadLocalConnection(),"select * from account", new BeanListHandler<Account>(Account.class));
            return accountList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Account findById(Integer id) {
        String sql = "select * from account where id = ?";
        try {
            Account account = queryRunner.query(connectionUtils.getThreadLocalConnection(),sql, new BeanHandler<Account>(Account.class), id);
            return account;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteById(Integer id) {
        String sql = "delete from account where id = ?";
        try {
            queryRunner.update(connectionUtils.getThreadLocalConnection(),sql, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setConnectionUtils(ConnectionUtils connectionUtils) {
        this.connectionUtils = connectionUtils;
    }
}
```

### 3.7 bean.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">
    <!--基包扫描-->
    <context:component-scan base-package="cn.yhs.learn"></context:component-scan>
    <!--使用${}读取外部配置文件要加的-->
    <context:property-placeholder location="c3p0.properties"></context:property-placeholder>
    <!--基于纯代码的方法的事务-->
    <bean class="cn.yhs.learn.service.impl.AccountServiceImpl" id="accountService">
        <property name="accountDao" ref="accountDao"></property>
        <property name="transactionManager" ref="transactionManager"></property>
    </bean>

    <!--基于动态代理的事务控制-->
    <bean class="cn.yhs.learn.service.impl2.AccountServiceImpl2" id="accountService2">
        <property name="accountDao" ref="accountDao"></property>
    </bean>

    <bean class="cn.yhs.learn.factory.BeanFactory" id="beanFactory">
        <property name="accountService" ref="accountService2"></property>
        <property name="transactionManager" ref="transactionManager"></property>
    </bean>

    <!--动态生成代理对象，这里使用的是使用方法来创建对象-->
    <bean id="proxyService" factory-bean="beanFactory" factory-method="getAccountService">
    </bean>

<!--配置dao层-->
    <bean class="cn.yhs.learn.dao.impl.AccountDaoImpl" id="accountDao">
        <property name="queryRunner" ref="queryRunner"></property>
        <property name="connectionUtils" ref="connectionUtils"></property>
    </bean>

    <bean class="org.apache.commons.dbutils.QueryRunner" id="queryRunner">
        <constructor-arg name="ds" ref="dataSource"></constructor-arg>
    </bean>

    <!--配置数据源 多例的-->
    <bean class="com.mchange.v2.c3p0.ComboPooledDataSource" id="dataSource" scope="prototype">
        <property name="driverClass" value="${c3p0.jdbc.driverClass}"></property>
        <property name="jdbcUrl" value="${c3p0.jdbc.jdbcUrl}"></property>
        <property name="user" value="${c3p0.jdbc.user}"></property>
        <property name="password" value="${c3p0.jdbc.password}"></property>

    </bean>

    <bean class="cn.yhs.learn.util.ConnectionUtils" id="connectionUtils">
        <property name="dataSource" ref="dataSource"></property>
    </bean>

    <bean class="cn.yhs.learn.util.TransactionManager" id="transactionManager">
        <property name="connectionUtils" ref="connectionUtils"></property>
    </bean>


    <bean class="cn.yhs.learn.factory.BeanFactory">
        <property name="transactionManager" ref="transactionManager"></property>
        <property name="accountService" ref="accountService2"></property>
    </bean>


</beans>
```

### 3.8 测试类

```java
public class SpringTest {
    // 查询所有
    @Test
    public void testName() {
        ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
   /*     ConnectionUtils connectionUtils = (ConnectionUtils) context.getBean("connectionUtils");
        Connection connect = connectionUtils.getThreadLocalConnection();
        try {
            System.out.println(connect.getAutoCommit());
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
/*        ConnectionUtils connectionUtils = (ConnectionUtils) context.getBean("connectionUtils");
        Connection connect = connectionUtils.getThreadLocalConnection();
        System.out.println(connect);
        TransactionManager transactionManager = (TransactionManager) context.getBean("transactionManager");
        try {
            Connection connection = transactionManager.getConnection();
            System.out.println(connection + "=>" + connection.getAutoCommit());
            transactionManager.beginTransaction();
            Connection connection2 = transactionManager.getConnection();
            System.out.println(connection2 + "=>" + connection2.getAutoCommit());

        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        TransactionManager transactionManager = (TransactionManager) context.getBean("transactionManager");
        transactionManager.beginTransaction();
        // transactionManager.release();
        transactionManager.beginTransaction();
        try {
            System.out.println(transactionManager.getConnection().getAutoCommit());
            transactionManager.release();
            System.out.println(transactionManager.getConnection().getAutoCommit());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    // 查询所有
    @Test
    public void test() {
        ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
        AccountService accountService = (AccountService) context.getBean("accountService");
        List<Account> accountList = accountService.findAll();
        for (Account account : accountList) {
            System.out.println(account);
        }
    }

    // 查找一个
    @Test
    public void test2() {
        ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
        AccountService accountService = (AccountService) context.getBean("accountService");
        Account account = accountService.findById(1);
        System.out.println(account);
    }

    // 更新
    @Test
    public void test3() {
        ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
        AccountService accountService = (AccountService) context.getBean("accountService");
        Account account = accountService.findById(1);
        System.out.println(account);
        account.setMoney(5000.0);
        accountService.updateAccount(account);
    }


    // 转账

    @Test
    public void test4() {
        ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
        AccountService accountService = (AccountService) context.getBean("proxyService");// 使用代理类加上事务控制
        //  AccountService accountService = (AccountService) context.getBean("accountService2");
        Account source = accountService.findById(1);
        Account dest = accountService.findById(2);
        // 现在有异常
        accountService.transfer(source, dest, 200.0);

    }


    // 删除成功，说明还是有事务的，没有事务控制，是删除不成功的
    @Test
    public void test5() {
        ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
        AccountService accountService = (AccountService) context.getBean("accountService");
        accountService.deleteById(1);
    }
}
```



