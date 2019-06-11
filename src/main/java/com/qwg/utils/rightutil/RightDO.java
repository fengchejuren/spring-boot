package com.qwg.utils.rightutil;

import com.qwg.utils.rightutil.handler.AbstractRightHandler;
import com.qwg.utils.rightutil.handler.doHandler.BizRightHandler;

/**
 * Created by Administrator on 2019/6/4.
 */
public enum RightDO {
    权限_云店查看(1,"aaa",null),
    权限_VIP购买(2,"aaa",null),
    权限_VIP价格查看(3,"aaa",null),
    权限_云店购买(4,"aaa", BizRightHandler.getInstance());

    private int bit;
    private String desc;
    private AbstractRightHandler rightHandler;

    RightDO(int bit, String desc,AbstractRightHandler handler){
        this.bit = bit;
        this.desc = desc;
        this.rightHandler = handler;
    }

    public int getBit() {
        return bit;
    }

    public String getDesc() {
        return desc;
    }

    public AbstractRightHandler getRightHandler() {
        return rightHandler;
    }


}
