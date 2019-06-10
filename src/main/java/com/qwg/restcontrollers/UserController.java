package com.qwg.restcontrollers;

import com.qwg.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Administrator on 2019/5/21.
 */
@RestController
@RequestMapping(value = "hello")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "add")
    public Object add(){
        HashMap<String,Object> user = new HashMap<>();
        user.put("USER_ID", UUID.randomUUID().toString().replaceAll("-",""));
        user.put("USERNAME","chickenEgg");
        user.put("NAME","鸡蛋蛋");
        userService.add(user);
        return "success";
    }
    @RequestMapping(value = "find")
    public Object find(){
        HashMap user = userService.findById("01eebef939664c81a9154bddc9f45298");
        return user;
    }
}
