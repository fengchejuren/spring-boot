package com.qwg.utils.rightutil;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/6/4.
 */
public class RightsUtil {

    /**
     * 用户购买的权限
     */
    public static final String RG_BUYRIGHT = "buyRight";
    /**
     * 用户查看的权限
     */
    public static final String RG_SHOWRIGHT = "showRight";
    /**
     * 权限组
     */
    public static Map<String,List<RightDO>> rightGroups = new HashMap<String,List<RightDO>>();

    /**
     * 是否拥有该权限
     * @param rightDO
     * @param rightField
     * @return
     */
    public static boolean testRights(RightDO rightDO, BigInteger rightField) {
        if(rightDO.getRightHandler() != null){
            return rightDO.getRightHandler().rightHandler(null);
        }else{
            return rightField.testBit(rightDO.getBit());
        }
    }

    /**
     * 是否拥有该权限
     * @param rightDO
     * @param rightField
     * @param map
     * @return
     */
    public static boolean testRights(RightDO rightDO, BigInteger rightField, Map<String,Object> map) {
        if(rightDO.getRightHandler() != null){
            return rightDO.getRightHandler().rightHandler(map);
        }else{
            return rightField.testBit(rightDO.getBit());
        }
    }

    /**
     * 设置权限
     * @param rightDO
     * @param right
     * @param rightField
     */
    public static BigInteger setRights(RightDO rightDO,boolean right,BigInteger rightField){
        if(right){
            return rightField.setBit(rightDO.getBit());
        }else {
            return rightField.clearBit(rightDO.getBit());
        }
    }
    /**
     * 设置组权限
     * @param rightDO
     * @param right
     * @param rightField
     */
    public static BigInteger setRights(RightDO rightDO,boolean right,BigInteger rightField,String groupName){
        List<RightDO> rightDOS = rightGroups.get(groupName);
        if(rightDOS != null){
            if(right){
                //组权限,当一个为是的时候,组权限里面的其他成员都为否
                for(RightDO rd: rightDOS){
                    if(rd.equals(rightDO)){
                        rightField = rightField.setBit(rd.getBit());
                    }else{
                        rightField = rightField.clearBit(rd.getBit());
                    }
                }
                return rightField;
            }else {
                return rightField.clearBit(rightDO.getBit());
            }
        }else{
            throw new RuntimeException("没有定义关键词为"+groupName+"的权限组!");
        }
    }

    /**
     * 静态方法校验权限定义的合法性,初始化权限组数据等
     */
    static{
        RightDO[] rightDOS = RightDO.class.getEnumConstants();
        Map map = new HashMap();
        for(RightDO right: rightDOS){
            map.put(right.getBit(),right.getDesc());
        }
        if(map.entrySet().size() < rightDOS.length){
            throw new RuntimeException("定义RightDO权限的enum时bit有重复的地方!");
        }

        List<RightDO> priceRightList = new ArrayList<>();
        priceRightList.add(RightDO.权限_云店查看);
        priceRightList.add(RightDO.权限_VIP价格查看);
        rightGroups.put(RG_SHOWRIGHT,priceRightList);
    }
}
