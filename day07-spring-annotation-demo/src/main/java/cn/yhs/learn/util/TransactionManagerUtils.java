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
