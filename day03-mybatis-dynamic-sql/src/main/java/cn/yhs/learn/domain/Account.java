package cn.yhs.learn.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.learn.domain.Account
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/5/26 11:07
 * @Description: 这里数据库里面的字段时大写，是因为mysql在windows中不区分大小写，要是在linux系统中要严格区分
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    private Integer id;
    private Integer uid;
    private Double money;
    private User user; // 实现1对1 1个账户只能属于一个用户
}
