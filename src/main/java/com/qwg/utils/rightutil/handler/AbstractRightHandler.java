package com.qwg.utils.rightutil.handler;

import java.util.Map;

/**
 * Created by Administrator on 2019/6/4.
 */
public abstract class AbstractRightHandler {
    //自定义权限校验
    public abstract boolean rightHandler(Map<String,Object> map);
}
