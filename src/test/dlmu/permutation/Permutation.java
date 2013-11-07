package test.dlmu.permutation;
/**
 * 全排序
 * @author Administrator
 *
 */
public class Permutation {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		char str[] = new char[]{'a','b','c'};  
		permutation(str, 0);  
	}

	private static void permutation(char[] str, int begin){
		if(begin==str.length){
			System.out.println(str);
		}else{
			for(int i = begin; i<str.length; ++i){
				swap(str, i, begin);
				permutation(str, begin + 1);
				swap(str, i, begin);
			}
		}
	}
	
	private static void swap(char[] str, int i, int j){
		char t = str[i];
		str[i] = str[j];
		str[j] = t;
	}
}
