package test.dlmu.stringmatch;

public class KMPMatch {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		char[] target = new char[]{'0','d','w','w','w','w','w','r','a','c','w','b','e'};
		char[] pattern = new char[]{'0','w','b','e'};
		KMPMatch.match(target, pattern);
	}
	
	private static int[] next(char[] pattern){
		int[] next = new int[pattern.length];
		next[1] = 0; int j = 0;
		for(int i = 1; i<pattern.length-1; ){
			if(j==0 || pattern[i] == pattern[j]){
				++j;++i;
				next[i] = j;
			} else{
				j = next[j];
			}
		}
		return next;
	}
	/**
	 * 
	 * @param target
	 * @param pattern
	 */
	private static void match(char[] target, char[] pattern){
		int[] next = next(pattern);
		for(int i = 1; i<next.length; ++i){
			System.out.print(next[i]);
			System.out.print("	");
		}
		System.out.println("");
		//char[] target = new char[]{'0','d','w','w','w','w','w','r','a','c','w','b','e'};
		//char[] pattern = new char[]{'0','w','b','e'};
		int i = 1; int j = 1;
		while(i<target.length&&j<pattern.length){
			if(j==0 || target[i]==pattern[j]){
				++j; ++i;
				if(j==pattern.length){
					System.out.println("find the index:" + (i-pattern.length+1));
				}
			} else{
				j = next[j];
			}
		}
	}
}
