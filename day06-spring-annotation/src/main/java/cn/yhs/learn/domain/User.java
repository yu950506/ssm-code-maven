package cn.yhs.learn.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.learn.domain.User
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/5/29 10:17
 * @Description: todo
 **/

/**
 * @Component 组件相当于xml配置中的<bean></bean>标签
 *      value属性指定bean的id，如果不指定，默认是当前类名的，并且首字母是小写
 */
/*@Component(value = "user")
@Scope(value = "singleton")*/
@Component
public class User {
    @Value("喻汉生") // 只能注入基本数据类型和String类型
    private String username;
    @Value("18")
    private int age;

    public User() {
        System.out.println("使用无参构造实例了User类");
    }

    /**
     * @PostConstruct 相当与 <bean init-method=""></bean>
     */
    @PostConstruct
    public void init(){
        System.out.println("User.init");
    }

    /**
     * @PreDestory 相当于 <bean destroy=""></bean>
     */
    @PreDestroy
    public void destroy(){
        System.out.println("User.destroy");
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", age=" + age +
                '}';
    }
}
