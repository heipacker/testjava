package test.dlmu.testjava;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
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
		testStringSize();
		//testAppBaseObject();
		//testNaN();
		//testSplit();
		//testLength();
		//testArray();
		//testVargs();
		//testTypeCast();
		//testAtomicInteger();
		//testClassA();
		//testLong();
		//System.out.println(TestJava.class.getSimpleName());
		//System.out.println(classNameBase(TestJava.class.getName()));
		//System.out.println(stringifyException(new RuntimeException()));
		//throw new RuntimeException();
	}
	
	public static void testStringSize(){
		
		System.out.println(Charset.defaultCharset().name());
		String tt = new String("英");
		char ttt = '英';
		
		try {
			System.out.println(tt.getBytes("utf-8").length);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(ttt);
		
		try {
			System.out.println(new String(charToByte(ttt),"utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static byte[] charToByte(char c) {
        byte[] b = new byte[3];
        b[0] = (byte) ((c & 0xFF0000) >> 16);
        System.out.println(b[0]);
        b[1] = (byte) ((c & 0xFF00) >> 8);
        System.out.println(b[1]);
        b[2] = (byte) (c & 0xFF);
        System.out.println(b[2]);
        
        return b;   
    } 
	
	public static void testAppBaseObject(){
		System.out.println(AppBaseObject.class.getName());
		AppBaseObject appBaseObject = new AppBaseObject();
		System.out.println(appBaseObject.getName());
	}
	
	public static void testNaN(){
		double i = Double.NaN;
		System.out.println(i);
	}
	
	public static void testProtected(){
		System.out.println(new TestProtected().t);//protected成员同一个包也不能访问
	}
	
	public static void testSplit(){
		String paramsString = "(name=portName,optype=between,value=[1,2][3,5][7,8][9,10][11,15]),(name=portName,optype=between,value=[1,2][3,5][7,8][9,10][11,15])";
		String tempArray[] = paramsString.split("\\),\\(");
		System.out.println(tempArray.length);
	}
	
	public static void testLength(){
		System.out.println("云".getBytes().length);
		System.out.println("云南省".getBytes().length);
		System.out.println("云南省楚雄".getBytes().length);
		System.out.println("云南省楚雄州地方海事局".getBytes().length);
	}
	
	public static void testArray(){
		int[] arrays = {1, 2};
		testArray__(arrays);
		for(int i:arrays){
			System.out.println(i);
		}
	}
	
	static void testArray__(int[] arrays){
		arrays[0] = 2;
	}
	
	static void testVargs___(int... vargs){
		
		if(vargs==null){
			System.out.println("--------null-------");
		}
		System.out.println(vargs.getClass());
		System.out.println(vargs.length);
	}
	
	public static void testVargs(){
		testVargs___();
		testVargs___(1);
		testVargs___(1,2,3);
	}
	
	public static void testTypeCast(){
		Object[] a = new Object[1];
		try {
			a[0] = Base1Impl.class.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Base1[] b = (Base1[]) a;
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

