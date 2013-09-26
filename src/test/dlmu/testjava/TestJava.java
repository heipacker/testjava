package test.dlmu.testjava;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
/**
 * 测试java反射调用类的静态方法
 * @author hadoop
 *
 */
public class TestJava {
	
	public static void main(String arg[]){
		//testClassA();
		testLong();
	}
	
	private static void testLong(){
		long period = 365*24*60*60*1000;//错误
		System.out.println(period);
		
		long period1 = 365*24*60*60*1000L;//
		System.out.println(period1);
	}
	
	private static void testClassA(){
		ClassA classa = new ClassA();
		try {
			Method method = classa.getClass().getDeclaredMethod("test", (Class<?>[])null);
			System.out.println(method.invoke(null/**此处可以使用null**/, (Object[])null));
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("-------------");
	}
	public static class ClassA{
		public static String test(){
			return "test";
		}
	}
}

