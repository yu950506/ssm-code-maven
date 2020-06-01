package cn.yhs.learn.factory;

import cn.yhs.learn.dao.AccountDao;
import cn.yhs.learn.domain.Account;
import cn.yhs.learn.service.AccountService;
import cn.yhs.learn.util.TransactionManager;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.learn.factory.BeanFactory
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/6/1 15:33
 * @Description: todo
 **/
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
