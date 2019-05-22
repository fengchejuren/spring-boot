package com.qwg;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigInteger;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringTestApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Test
	public void testBitRights(){
		BigInteger rights = new BigInteger("0");
		rights = rights.setBit(3);
		//rights = rights.setBit(5);
		System.out.println(rights);
		System.out.println(rights.testBit(2));
		System.out.println(rights.testBit(3));
	}

}
