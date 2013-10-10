package test.dlmu.stringmatch;

public class RKMatch {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		char[] target = new char[]{'1','1','2','4','3','5','2','1','2','4','3','5','4','3','5'};
		char[] pattern = new char[]{'3','5'};
		RKMatch.match(target, pattern);
	}
	/**
	 * 时间复杂度O(n) 最坏情况O((n-m+1)*m)
	 * @param target
	 * @param pattern
	 */
	private static void match(char[] target, char[] pattern) {
		long p = prepattern(pattern, pattern.length);
		System.out.println("preprocess(pattern):"+p);
		int targetpLen = target.length-pattern.length+1;
		
		long[] targetp = new long[targetpLen];
		for(int i = 0; i<targetpLen; ++i){
			if(i==0){
				targetp[i] = prepattern(target, pattern.length);
			}else{
				targetp[i] = processTarget(target, targetp[i-1], i+pattern.length-1, pattern.length);
			}
			if(targetp[i]==p){
				System.out.println("find the index:" + (i));
			}
		}
	}
	/**
	 * 预处理
	 * @param pattern
	 * @param len
	 * @return
	 */
	private static long prepattern(char[] pattern, int len){
		long[] p = new long[len];
		for(int i = 0; i<len; ++i){
			if(i==0){
				p[i] = pattern[i]-48;
				continue;
			}
			p[i] = p[i-1]*10+(pattern[i]-48);
		}
		return p[p.length-1];
	}
	
	private static long processTarget(char[] target, long pm, int i, int pl){
		return (long) (10*(pm - Math.pow(10, pl-1)*(target[i-pl]-48)) + target[i]-48);
	}
}
