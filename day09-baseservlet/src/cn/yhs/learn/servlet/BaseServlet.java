package cn.yhs.learn.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.learn.servlet.BaseServlet
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/6/3 13:41
 * @Description: 对Servlet的抽取操作
 **/
public class BaseServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 所有请求最终都会走service方法,不管你是post还是get.再让service方法根据这个方法名称进行调用
        // 1.需要获取方法名称，这个可以从路劲上进行获取
        String uri = req.getRequestURI();
        // 2.获取方法名称
        String methodName = uri.substring(uri.lastIndexOf("/") + 1);
        // 3.利用反射进行方法调用
        try {
            // this指代当前对象，比如UserServlet继承它，this就代表UserServlet
            Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            // 走我们自己定义的方法
            method.invoke(this, req, resp);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
