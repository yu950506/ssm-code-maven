package cn.yhs.learn.util;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.learn.util.Connectionutils
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/6/1 13:47
 * @Description: todo
 **/

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

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
