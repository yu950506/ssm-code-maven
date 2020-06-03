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
@WebServlet("/Account/*")
public class AccountServlet extends BaseServlet {

    public void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("AccountServlet.add");
    }

    public void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("AccountServlet.delete");
    }

}
