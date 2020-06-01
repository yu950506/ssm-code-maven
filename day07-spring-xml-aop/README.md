# 转账案例-基于AOP切面事务控制（XML配置）

## 1.项目介绍

一般我们在业务层都会对事务进行控制操作，前面我们讲解了基于动态代理的方式进行事务控制，这里我们借助Spring框架的AOP来实现对事务的控制，让我们更加专心也业务层的代码。

## 2.项目主要技术

- c3p0数据库连接池+Dbutils来实现Dao层业务逻辑
- AOP+线程池(保证dao层都在一个连接中)+事务控制类来实现Service层逻辑控制(重难点)
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
       <!--spring和junit整合的jar-->
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

### 3.2 线程池

```java
public class ConnectionUtils {
    private ThreadLocal<Connection> tl = new ThreadLocal<Connection>();
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

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
```

### 3.3 事务控制

```java
public class TransactionManagerUtils {
    private ConnectionUtils connectionUtils;

    public void setConnectionUtils(ConnectionUtils connectionUtils) {
        this.connectionUtils = connectionUtils;
    }

    /**
     * 开启事务,设置自动提交为false
     */
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
```

### 3.4 业务层代码

```java
public class AccountServiceImpl implements AccountService {
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

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }
}
```

### 3.5 持久层代码

```java
public class AccountDaoImpl implements AccountDao {
    private QueryRunner queryRunner;
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
            account = queryRunner.query(connectionUtils.getThreadLocalConnect(), sql, new BeanHandler<Account>(Account.class),id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;

    }

    public void setQueryRunner(QueryRunner queryRunner) {
        this.queryRunner = queryRunner;
    }

    public void setConnectionUtils(ConnectionUtils connectionUtils) {
        this.connectionUtils = connectionUtils;
    }
}
```

### 3.6 bean.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd http://www.springframework.org/schema/aop https://www.springframework.org/schema/aop/spring-aop.xsd">
    <context:component-scan base-package="cn.yhs.learn"></context:component-scan>
    <!--引入properties文件-->
    <context:property-placeholder location="c3p0.properties"></context:property-placeholder>
    <!--配置数据源 dataSource-->
    <!--使用配套的setXXX来创建对象-->
    <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
        <property name="driverClass" value="${c3p0.jdbc.driverClass}"></property>
        <property name="jdbcUrl" value="${c3p0.jdbc.jdbcUrl}"></property>
        <property name="user" value="${c3p0.jdbc.user}"></property>
        <property name="password" value="${c3p0.jdbc.password}"></property>
    </bean>

    <!--配置 queryRunner-->
    <bean id="queryRunner" class="org.apache.commons.dbutils.QueryRunner">
        <!--使用构造器的方式创建对象 -->
        <constructor-arg name="ds" ref="dataSource"></constructor-arg>
    </bean>

    <!--配置工具层-->
    <bean id="connectionUtils" class="cn.yhs.learn.util.ConnectionUtils">
        <property name="dataSource" ref="dataSource"></property>
    </bean>
    <bean id="transactionManager" class="cn.yhs.learn.util.TransactionManagerUtils">
        <property name="connectionUtils" ref="connectionUtils"></property>
    </bean>


    <!--配置Dao层-->
    <bean id="accountDao" class="cn.yhs.learn.dao.impl.AccountDaoImpl">
        <property name="queryRunner" ref="queryRunner"></property>
        <property name="connectionUtils" ref="connectionUtils"></property>
    </bean>

    <!--配置service层-->
    <bean id="userService" class="cn.yhs.learn.service.impl.AccountServiceImpl">
        <property name="accountDao" ref="accountDao"></property>
    </bean>

    <!--配置切面层，对业务层进行事务控制-->
    <aop:config>
        <!--进行事务控制-->
        <aop:aspect id="transManager" ref="transactionManager">
            <!--通用化切面表达式-->
            <aop:pointcut id="transform" expression="execution(* cn.yhs.learn.service.*.*(..))"></aop:pointcut>
            <!--开启事务-->
            <aop:before method="begin" pointcut-ref="transform"></aop:before>
            <!--有异常就回滚-->
            <aop:after-throwing method="rollback" pointcut-ref="transform"></aop:after-throwing>
            <!--提交事务-->
            <aop:after-returning method="commit" pointcut-ref="transform"></aop:after-returning>
            <!--释放资源-->
            <aop:after method="release" pointcut-ref="transform"></aop:after>
        </aop:aspect>
    </aop:config>

</beans>
```

### 3.7 测试类

```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:bean.xml")
public class SpringTest {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private AccountService accountService;

    @Test
    public void testAllDefineNames() {
        String[] names = context.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }
    }

    @Test
    public void testFind() {
        Account account1 = accountService.findById(1);
        Account account2 = accountService.findById(2);
        System.out.println(account1);
        System.out.println(account2);

    }

    /**
     * 测试转账
     */
    @Test
    public void testTransfer() {
        Account account1 = accountService.findById(1);
        Account account2 = accountService.findById(2);
        Double money = 200.0;
        accountService.transfer(account1, account2, money);


    }


}
```



