package cn.yhs.learn.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.learn.domain.Account
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/5/27 11:00
 * @Description: todo
 **/
// 一个账户对应一个用户  1:1的关系

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    private Integer id;
    private Integer uid;
    private Double money;
    private User user; // 实现1:1的关系
}
