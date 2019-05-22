package com.qwg.interfaces;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;

/**
 * Created by Administrator on 2019/5/21.
 */
@Mapper
public interface UserDao {
    HashMap findById(String id);

    void add(HashMap<String,Object> user);
}
