package test.dlmu.testjava;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * 测试java反射调用类的静态方法
 * @author hadoop
 *
 */
public class TestJava {
	
	private TestJava(){
		
	}
	
	public static TestJava newInstance(){
		return new TestJava();
	}
	
	public static void main(String arg[]){
		testAtomicInteger();
		//testClassA();
		//testLong();
		//System.out.println(TestJava.class.getSimpleName());
		//System.out.println(classNameBase(TestJava.class.getName()));
		//System.out.println(stringifyException(new RuntimeException()));
		//throw new RuntimeException();
	}
	public static void testAtomicInteger(){
		AtomicInteger a = new AtomicInteger(0);
		AtomicInteger b = new AtomicInteger(0);
		System.out.println(a.equals(b));
	}
    /**
     * get base name
     * @param className
     * @return
     */
	public static String classNameBase(String className) {
      String[] names = className.split("\\.", -1);
      if (names == null || names.length == 0) {
        return className;
      }
      return names[names.length-1];
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
	
	public static void testLong(){
		long period = 365*24*60*60*1000;//错误
		System.out.println(period);
		
		long period1 = 365*24*60*60*1000L;//
		System.out.println(period1);
	}
	
	public static void testClassA(){
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

