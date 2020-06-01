package cn.yhs.learn.service.impl;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.learn.service.impl.AccountServiceImpl
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/6/1 20:28
 * @Description: todo
 **/
public class AccountServiceImpl {
    public void save() {
        System.out.println("保存方法");
    }

    public void delete(int i) {
        System.out.println("删除方法" + i);
    }

    public Object find() {
        System.out.println("查询方法");
        return null;
    }

    public void err() {
        int i = 1 / 0;
    }
}
