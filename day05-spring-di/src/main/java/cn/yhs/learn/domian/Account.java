package cn.yhs.learn.domian;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.learn.domian.Account
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/5/28 21:42
 * @Description: todo
 **/
public class Account {
    private String name;
    private int id;

    public void setName(String name) {
        System.out.println("Account.setName\\\\\\");
        this.name = name;
    }

    public void setId(int id) {
        System.out.println("Account.setId//////");
        this.id = id;
    }

    @Override
    public String toString() {
        return "Account{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
