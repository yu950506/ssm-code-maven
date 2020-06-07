package cn.yhs.learn.controller;

import cn.yhs.learn.domain.User;
import cn.yhs.learn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.learn.controller.UserController
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/6/7 9:57
 * @Description: todo
 **/
@Controller("userController")
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 保存注册的功能
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/save")
    public String save(User user) {
        userService.save(user);
        return "success";
    }

    @RequestMapping("/findById")
    public String findById(Integer id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "success";
    }


}
