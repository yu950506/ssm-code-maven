package cn.yhs.learn.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.learn.domain.User
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/5/27 17:35
 * @Description: todo
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    private Integer id;
    private String username;
    private Date birthday;
    private Character sex;
    private String address;
    /**
     * 1 对 多 ：一个用户可以有多个账户
     */
    private List<Account> accounts;
    /**
     * 故意调整和数据库字段不对应的样子
     */
 /*   private Integer uid;
    private String uusername;
    private Date ubirthday;
    private Character usex;
    private String uaddress;*/
}
