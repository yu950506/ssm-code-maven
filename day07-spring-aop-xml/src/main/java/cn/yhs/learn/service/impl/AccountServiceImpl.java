package cn.yhs.learn.service.impl;

import cn.yhs.learn.service.AccountService;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.learn.service.impl.AccountServiceImpl
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/5/31 14:12
 * @Description: todo
 **/
public class AccountServiceImpl implements AccountService {
    public void saveAccount() {
        System.out.println("保存用户");
    }

    public Object updateAccount() {
        System.out.println("成功更新用户");
        return null;
    }

    public void deleteAccount(Integer id) {
        System.out.println("成功删除用户");
    }


}
