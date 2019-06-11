package com.qwg.utils.rightutil.handler.doHandler;

import com.qwg.utils.rightutil.handler.AbstractRightHandler;

import java.util.Map;

/**
 * Created by Administrator on 2019/6/4.
 */
public class BizRightHandler extends AbstractRightHandler{

    private static volatile BizRightHandler singleTonHandler;

    private BizRightHandler(){}

    public static BizRightHandler getInstance(){
        if(singleTonHandler == null){
            synchronized (BizRightHandler.class){
                if(singleTonHandler == null){
                    singleTonHandler = new BizRightHandler();
                }
            }
        }
        return singleTonHandler;
    }

    @Override
    public boolean rightHandler(Map<String, Object> map) {
        if(map != null){
            System.out.println("hhhhhh*************"+map.get("name"));
        }
        return false;
    }
}
