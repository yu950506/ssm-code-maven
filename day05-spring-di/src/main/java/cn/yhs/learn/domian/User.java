package cn.yhs.learn.domian;

import java.util.Date;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.learn.domian.User
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/5/28 21:33
 * @Description: todo
 **/
public class User {
    private String username;
    private Date birthday;
    private int age;

    public User() {
        System.out.println("无参构造执行了。。。");
    }

    /**
     *   演示通过构造函数进行依赖注入
     * @param username
     * @param birthday
     * @param age
     */


    public User(String username, Date birthday, int age) {
        System.out.println("有参构造执行了。。。。");
        this.username = username;
        this.birthday = birthday;
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", birthday=" + birthday +
                ", age=" + age +
                '}';
    }
}
