package test.dlmu.testjava;

public class SplitTest {
	
	public static void main(String[] args){
		String rng = "12-1-2";
		String[] parts = rng.split("-", 3);
		for(int i = 0; i<parts.length; ++i){
			System.out.println(parts[i]);
		}
	}
}
