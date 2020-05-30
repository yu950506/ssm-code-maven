package cn.yhs.learn.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.learn.domain.User
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/5/26 9:10
 * @Description: 数据库映射实体类，使用lombok 来生成必要的方法
 **/

@Data // 生成 get set toString
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer id;
    private String username;
    private Date birthday;
    private Character sex;
    private String address;
    private List<Account> accounts; // 实现1对多的关系，1个用户可以有多个账户
}
