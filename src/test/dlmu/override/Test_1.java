package test.dlmu.override;

public class Test_1 {
	
	public void print(Object obj){
		System.out.println("this is Object!");
	}
	
	public void print(String obj){
		System.out.println("this is String!");
	}
	
	public static void main(String[] args){
		Test_1 test = new Test_1();
		test.print(null);
	}
}
