package test.dlmu.testjava;

public class TestTypesConvert {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		char i = 10;
		TestTypesConvert.test(i);
	}

	public static void test(long i){
		System.out.println("long");
	}
	
	public static void test(int i){
		System.out.println("int");
	}
	
	public static void test(char i){
		System.out.println("char");
	}
	
	public static void test(short i){
		System.out.println("short");
	}
	
	
}
