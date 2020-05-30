package cn.yhs.learn.factory;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.learn.factory.AccFactory
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/5/28 19:54
 * @Description: todo
 **/

import cn.yhs.learn.service.impl.AccountServiceImpl;

/**
 *  为了模拟是jar 包中的类，
 */
public class AccFactory {

    /**
     *  该方法可以创建一个对象
     * @return
     */
  public  AccountServiceImpl getAccountServiceImpl(){
      return new AccountServiceImpl();
  }
}
