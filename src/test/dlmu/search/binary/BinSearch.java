package test.dlmu.search.binary;

public class BinSearch<T extends Comparable<T>> implements Search<T>{
	
	@Override
	public int search(T[] target, T key) {
		int low = 0;
		int high = target.length - 1;
		int mid = (low + high) / 2;
		while (low <= high) {
			if (key.compareTo(target[mid]) > 0) {
				low = mid + 1;
			} else if (key.compareTo(target[mid]) < 0) {
				high = mid - 1;
			} else {
				return mid;
			}
			mid = (low + high)/2;
		}
		return -1;
	}
	
	public static void main(String[] args){
		Search<Integer> search = new BinSearch<Integer>();
		
		System.out.println(search.search(new Integer[]{1,3,4,11}, 11));
	}
}
