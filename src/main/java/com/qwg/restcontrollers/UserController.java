package com.qwg.restcontrollers;

import com.qwg.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2019/5/21.
 */
@RestController
@RequestMapping(value = "hello")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping(value = "add")
    public Object add(){
        HashMap<String,Object> user = new HashMap<>();
        user.put("USER_ID", UUID.randomUUID().toString().replaceAll("-",""));
        user.put("USERNAME","chickenEgg");
        user.put("NAME","鸡蛋蛋");
        userService.add(user);
        logger.info("redis中aaa的值是:"+redisTemplate.opsForValue().get("aaa"));
        redisTemplate.opsForValue().set("aaa","123",5000, TimeUnit.MILLISECONDS);
        logger.info("redis中aaa的值是:"+redisTemplate.opsForValue().get("aaa"));
        return "success";
    }
    @RequestMapping(value = "find")
    public Object find(){
        HashMap user = userService.findById("01eebef939664c81a9154bddc9f45298");
        return user;
    }
}
