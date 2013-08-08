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

