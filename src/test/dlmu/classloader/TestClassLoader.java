package test.dlmu.classloader;

public class TestClassLoader {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("加载自定义类装载器的装载器:" + ClassLoader.class.getClassLoader());
		
		ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
		System.out.println("系统类装载器:" + systemClassLoader);
		ClassLoader extClassLoader = systemClassLoader.getParent();
		System.out.println("系统类装载器的父类加载器——扩展类加载器:" + extClassLoader);
		ClassLoader bootClassLoader = extClassLoader.getParent();
		System.out.println("扩展类加载器的父类加载器——引导类加载器:" + bootClassLoader);
	}

}
