package test.dlmu.junit;

import junit.framework.TestCase;

public class JunitTest extends TestCase {
	
	public static String getNum(int num){
		return "1";
	}

	public void testGetNum(){
		assertEquals("1", getNum(1));
	}

}
