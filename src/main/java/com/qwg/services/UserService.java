package com.qwg.services;

import com.qwg.interfaces.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2019/5/21.
 */
@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public HashMap findById(String id){
        return userDao.findById(id);
    }

    public void add(HashMap<String, Object> user) {
        userDao.add(user);
    }
}
