package cn.yhs.learn.factory;

import cn.yhs.learn.service.impl.AccountServiceImpl;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.learn.factory.StaticAccFactory
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/5/28 20:01
 * @Description: todo
 **/
public class StaticAccFactory {

    public static AccountServiceImpl instance(){
        return new AccountServiceImpl();
    }
}
