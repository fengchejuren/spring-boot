package com.qwg.webcontroller;

import com.qwg.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

/**
 * Created by Administrator on 2019/6/10.
 */
@Controller
@RequestMapping("helloweb")
public class HelloController {

    @Autowired
    private UserService userService;

    @RequestMapping("hello")
    public String hello(Model model,String user_id){
        HashMap user = userService.findById(user_id);
        model.addAttribute("name","张三");
        model.addAttribute("user",user);
        return "/helloweb/hello";
    }
}
