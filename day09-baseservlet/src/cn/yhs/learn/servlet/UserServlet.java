package cn.yhs.learn.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ProjectName: ssm-code-maven
 * @Name: ${PACKAGE_NAME}.${NAME}
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/6/3 13:43
 * @Description: todo
 **/
// 1. 前端请求这个路径是就会走这个类 * 匹配任意路劲，目的是为了获取这个类下面的任意方法
@WebServlet("/User/*")
public class UserServlet extends BaseServlet {
    // 2. 实际上这个类继承了BaseServlet中的service方法。用户根据路径请求时先走service方法，service再调用具体的方法
    // 添加功能
    public void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("UserServlet.add");
    }
    // 删除功能
    public void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("UserServlet.delete");
    }


}
