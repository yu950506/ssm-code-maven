# BaseServlet的抽取

## 1. 介绍

我们在做web项目的时候，往往会因为一个登陆，或者一个注册的功能都会编写一个Servlet，当项目比较大的时候编写就比较费时，做了大量的重复工作，甚至日后代码维护起来也很困难。所以就萌生了写一个来集成HttpServlet，然后一个功能写一个方法，一个类对应一个模块的Servlet。

## 2.HttpServlet类源码探究

```java
// 当我们编写一个类集成HttpServlet时，子类相当于也是有这个service方法，只是我们没有覆写而已，前端发送的任何请求都会走service方法   
protected void service(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException
    {
    // 获取请求方法的类型名称：get 、post、 delete 等
	String method = req.getMethod();
	// if-else if 结构是为了判断方法的类型，根据方法的类型走响应的方法。
	if (method.equals(METHOD_GET)) {
	    long lastModified = getLastModified(req);
	    if (lastModified == -1) {
		// servlet doesn't support if-modified-since, no reason
		// to go through further expensive logic
         // get 类型就走doGet，下面类似 ，就会去运行我们自己写doGet方法。  
		doGet(req, resp);
	    } else {
		long ifModifiedSince = req.getDateHeader(HEADER_IFMODSINCE);
		if (ifModifiedSince < (lastModified / 1000 * 1000)) {
		    // If the servlet mod time is later, call doGet()
                    // Round down to the nearest second for a proper compare
                    // A ifModifiedSince of -1 will always be less
		    maybeSetLastModified(resp, lastModified);
		    doGet(req, resp);
		} else {
		    resp.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
		}
	    }

	} else if (method.equals(METHOD_HEAD)) {
	    long lastModified = getLastModified(req);
	    maybeSetLastModified(resp, lastModified);
	    doHead(req, resp);// 走doHead方法

	} else if (method.equals(METHOD_POST)) {
	    doPost(req, resp);
	    
	} else if (method.equals(METHOD_PUT)) {
	    doPut(req, resp);	
	    
	} else if (method.equals(METHOD_DELETE)) {
	    doDelete(req, resp);
	    
	} else if (method.equals(METHOD_OPTIONS)) {
	    doOptions(req,resp);
	    
	} else if (method.equals(METHOD_TRACE)) {
	    doTrace(req,resp);
	    
	} else {
	    //
	    // Note that this means NO servlet supports whatever
	    // method was requested, anywhere on this server.
	    //

	    String errMsg = lStrings.getString("http.method_not_implemented");
	    Object[] errArgs = new Object[1];
	    errArgs[0] = method;
	    errMsg = MessageFormat.format(errMsg, errArgs);
	    
	    resp.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED, errMsg);
	}
    }
```

## 3. BaseServlet的编写

看了HttpServlet的相关代码后，根据我们学过的反射，就可以入手我们自定义的BaseServlet了

```java
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
```

## 4.模块Servlet的编写

```java
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
    // 添加功能，专心写我们的功能模块
    public void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("UserServlet.add");
    }
    // 删除功能
    public void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("UserServlet.delete");
    }
}

// ***********账户模块************

@WebServlet("/Account/*")
public class AccountServlet extends BaseServlet {

    public void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("AccountServlet.add");
    }

    public void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("AccountServlet.delete");
    }

}
```



