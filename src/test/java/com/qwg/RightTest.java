package com.qwg;

import com.qwg.utils.rightutil.RightsUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import static com.qwg.utils.rightutil.RightDO.*;

/**
 * Created by Administrator on 2019/6/4.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class RightTest {

    @Test
    public void testRight(){
        BigInteger rightField = new BigInteger("2");
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("name",Thread.currentThread().getName());
        System.out.println(RightsUtil.testRights(权限_云店购买,rightField,map));
    }

    @Test
    public void testSetRight(){
        BigInteger rightField = new BigInteger("0");
        rightField = RightsUtil.setRights(权限_云店查看, true, rightField,RightsUtil.RG_SHOWRIGHT);
        rightField = RightsUtil.setRights(权限_VIP价格查看, true, rightField,RightsUtil.RG_SHOWRIGHT);
        rightField = RightsUtil.setRights(权限_VIP购买, true, rightField);
        System.out.println("权限_云店查看:"+RightsUtil.testRights(权限_云店查看,rightField));
        System.out.println("权限_VIP价格查看:"+RightsUtil.testRights(权限_VIP价格查看,rightField));
        System.out.println("权限_云店购买:"+RightsUtil.testRights(权限_云店购买,rightField));
        System.out.println("权限_VIP购买:"+RightsUtil.testRights(权限_VIP购买,rightField));

    }
    @Test
    public void testOneRight(){
        BigInteger rightField = new BigInteger( "0");
        BigInteger newRight = rightField.setBit(4);
        System.out.println(newRight.testBit(4));
    }

    @Test
    public void testString(){
        String s = "aa.jpg";
        System.out.println("====================="+s.split("\\.").length);

        String phone ="13556598569";
        System.out.println("***************"+phone.substring(3));
    }
}
