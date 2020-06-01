package cn.yhs.learn.util;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.learn.util.TransactionManager
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/6/1 13:58
 * @Description: todo
 **/


import java.sql.Connection;
import java.sql.SQLException;

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
