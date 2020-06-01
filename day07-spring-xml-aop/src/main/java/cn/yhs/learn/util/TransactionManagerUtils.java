package cn.yhs.learn.util;

import java.sql.SQLException;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.learn.util.TransactionManagerUtils
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/6/1 21:36
 * @Description: todo
 **/
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
