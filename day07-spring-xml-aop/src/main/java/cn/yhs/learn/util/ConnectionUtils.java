package cn.yhs.learn.util;

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
