package com.qwg;

import com.qwg.interfaces.UserDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringTestApplicationTests {

	Logger logger = LoggerFactory.getLogger(SpringTestApplicationTests.class);

	@Autowired
	private UserDao userDao;

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

	@Test
	public void testFormat(){
		String day = "2019-04-16 20:06:28";
		System.out.println("************"+day.substring(0,11));
		HashMap map = userDao.findById("f8108659b3e44174ae774f7750c2b6d2");
	}

	@Test
	public void testEnum(){
		Pattern pattern = Pattern.compile("^\\+?[0-9]+$");
		String s = "59.26";
		Matcher matcher = pattern.matcher(s);
		System.out.println(matcher.find());
		System.out.println(pattern.matcher("11aa90").find());
		System.out.println(pattern.matcher("01+a").find());
		System.out.println(pattern.matcher("-01+a").find());
		System.out.println(pattern.matcher("01").find());
		System.out.println(pattern.matcher("+51").find());
	}

	@Test
	public void testHashCode(){
		String s = "000d9d7124544d8a854a620b18b2d5fc";
		logger.info("...........hashCode.........:"+s.hashCode());
		String s2 = "000d9d7124544d8a854a620b18b2d5fc";
		logger.info("..........s2.hashCode.........:"+s2.hashCode());
		String s3 = "ABCDFB123abc";
		logger.info("..........s3.hashCode.........:"+s3.hashCode());
		String s4 = "ABCDEa123abc";
		logger.info("..........s4.hashCode.........:"+s4.hashCode());
	}
}
