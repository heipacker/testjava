package test.dlmu.testjava;

import java.io.PrintWriter;
import java.io.StringWriter;
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
		//testLong();
		System.out.println(stringifyException(new RuntimeException()));
		//throw new RuntimeException();
	}
	/**
	 * Exception转换为String
	 * @param e
	 * @return
	 */
	public static String stringifyException(Throwable e){
		StringWriter stm = new StringWriter();
	    PrintWriter wrt = new PrintWriter(stm);
	    e.printStackTrace(wrt);
	    wrt.close();
	    return stm.toString();
	}
	
	public static void testShort(){
		short s = 1;
		s = (short) (s + 1);//error
		short s1 = 1;
		s1 += 1;//ok
		//等价
		short s2 = 1;
		s2++;
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

