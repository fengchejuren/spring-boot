package com.qwg.webcontroller;

import com.qwg.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2019/6/10.
 */
@Controller
@RequestMapping("file")
public class FileController {

    @Autowired
    private UserService userService;

    @RequestMapping("toUpload")
    public String toUpload(Model model,String user_id){
        HashMap user = userService.findById(user_id);
        model.addAttribute("name","张三");
        model.addAttribute("user",user);
        return "fileupload/upload";
    }

    @PostMapping("largeFileUpload")
    public String largeFileUpload(Model model,Map<String,Object> map){
        model.addAttribute("name","张三");
        return "fileupload/upload";
    }
}
