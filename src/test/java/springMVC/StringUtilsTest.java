package springMVC;

import junit.framework.Assert;

import org.junit.Test;

import cn.com.titans.tcsmp.utils.StringUtils;

public class StringUtilsTest {

	@Test
	public void testIsEmptyString() {
		String input = null;
		boolean result = StringUtils.isEmpty(input);
		Assert.assertEquals(true, result);
		input = "";
		result = StringUtils.isEmpty(input);
		Assert.assertEquals(true, result);
		input = "test";
		result = StringUtils.isEmpty(input);
		Assert.assertEquals(false, result);
	}


	@Test
	public void testSplitStringString() {
		String input = "a_bb_cc";
		String[] result = StringUtils.split(input, "_",3);
		String[] expect = {"aa", "bb","cc"};
		for (int i =0;i<result.length;i++) {
			Assert.assertEquals(expect[i] , result[i]);
		}
		
	}

}
