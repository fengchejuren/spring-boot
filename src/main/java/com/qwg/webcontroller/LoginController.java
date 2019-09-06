package com.qwg.webcontroller;

import com.qwg.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2019/8/22.
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping("toLogin")
    public String toLogin(HttpServletRequest request,Model model){
        HttpSession session = request.getSession();
        session.setAttribute("sessionId", session.getId());
        model.addAttribute("port",request.getServerPort());
        model.addAttribute("sessionId",session.getId());
        return "login/toLogin";
    }

    @RequestMapping("login")
    public String login(Model model, String username, String password, HttpServletRequest request){
        if("admin".equals(username) && "123456".equals(password)){
            HttpSession session = request.getSession();
            session.setAttribute("username",username);
            session.setAttribute("sessionId", session.getId());
            model.addAttribute("username",username);
            model.addAttribute("port",request.getServerPort());
            model.addAttribute("sessionId",session.getId());
            return "login/index";
        }else{
            return "login/toLogin";
        }
    }
}
