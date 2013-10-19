package test.dlmu.stringmatch;

public class PlainMatch {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		char[] target = new char[]{'a','d','w','r','a','c','w','b','e'};
		char[] pattern = new char[]{'d','w','r'};
		PlainMatch.match(target, pattern);
	}
	/**
	 * 朴素匹配算法O((n-m+1)*m)
	 * @param target
	 * @param pattern
	 */
	private static void match(char[] target, char[] pattern){
		for(int i = 0; i<target.length-pattern.length; ++i){
			int tmpIndex = i;
			for(int j = 0; j<pattern.length; ++j){
				if(target[tmpIndex++]!=pattern[j]){
					break;
				}
				if(j==pattern.length-1){
					System.out.println("find the index:" + i);
				}
			}
		}
		System.out.println("////////////////////////////////////////////////////////////");
		int i = 0;
		while(i<target.length-pattern.length){
			int j = 0;
			int tmpIndex = i++;
			while(j<pattern.length){
				if(target[tmpIndex++]!=pattern[j++]){
					break;
				}
				if(j==pattern.length){
					System.out.println("find the index:" + (i-1));
				}
			}
		}
	}
}
