package cn.yhs.learn.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.learn.domain.AccountUser
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/5/26 13:10
 * @Description: todo
 **/

/**
 * 采用继承的方式实现 1:1
 * 花销太大
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountUser extends Account {
    private String username;
    private String address;

    @Override
    public String toString() {
        return "AccountUser{" + super.toString() +
                "username='" + username + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
