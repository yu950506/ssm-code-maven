package cn.yhs.learn.service.util;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.learn.service.util.PrintLog
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/6/1 20:29
 * @Description: todo
 **/
public class PrintLog {
    public void beforeLog() {
        System.out.println("PrintLog.beforeLog");
    }

    public void afterLog() {
        System.out.println("PrintLog.afterLog");
    }

    public void afterThrowingLog() {
        System.out.println("PrintLog.afterThrowingLog");
    }

    public void afterReturningLog() {
        System.out.println("PrintLog.afterReturnLog");
    }

    public Object aroundLog(ProceedingJoinPoint proceedingJoinPoint) {
        Object[] args = proceedingJoinPoint.getArgs();
        try {
            System.out.println("PrintLog.aroundLog");
            Object returnVal = proceedingJoinPoint.proceed(args);
            return returnVal;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            throw new RuntimeException(throwable);
        }finally {

        }
    }
}
