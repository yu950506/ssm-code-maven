package cn.yhs.learn.dao;

import cn.yhs.learn.domain.Role;

import java.util.List;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.dao.RoleDao
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/5/26 16:25
 * @Description: todo
 **/
public interface RoleDao {
    List<Role> findRoleWithUser();
}
