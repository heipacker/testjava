package test.dlmu.testjava;
/**
 * 如果一个方法或构造器可以接受传递给另一个方法或构 造器的任何参数
 * 那么我们就说第一个方法比第二个方法缺乏精确性[JLS 15.12.2.5]
 * @author Administrator
 *
 */
public class TestOverloading {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TestOverloading test = new TestOverloading();
		test.test(null);
	}

	/*public void test(String str){
		System.out.println(str);
	}*/

	public void test(Object str){
		System.out.println("Object");
	}

	public void test(Test str){
		System.out.println("Test");
	}

	private static class Test{
		
	}
	
}
