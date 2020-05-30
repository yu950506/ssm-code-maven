package cn.yhs.learn.domain;

import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.learn.domain.User
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/5/27 10:58
 * @Description: todo
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    private Integer id;
    private String username;
    private Date birthday;
    private Character sex;
    private String address;
    private List<Account> accountList; // 1:N 的体现形式
}
