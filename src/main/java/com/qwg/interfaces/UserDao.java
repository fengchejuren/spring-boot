package com.qwg.interfaces;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;

/**
 * Created by Administrator on 2019/5/21.
 */
@Mapper
public interface UserDao {

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    HashMap findById(String id);

    /**
     * 添加记录
     * @param user
     */
    void add(HashMap<String,Object> user);
}
