package test.dlmu.sort;
/**
 * 块速排序
 *
 * @param <T>
 */
public class QSort {

	public static void main(String[] args){
		Long[] array = new Long[]{(long) 9,(long)2,(long)7,(long) 12,(long)54,(long) 21,(long) 30,(long)39,(long)24,(long)14,(long) 56,(long)4,(long) 5};
		
		qsort(array, 0, array.length-1);
		for(Long t:array){
			System.out.println(t);
		}
	}
	
	public static <T extends Comparable<T>> void qsort(T[] array, int low, int high){
		if(low<high){
			int middle = partition(array, low, high);
			qsort(array, low, middle-1);
			qsort(array, middle+1, high);
		}
	}
	
	private static <T extends Comparable<T>> int partition(T[] array, int low, int high){
		T middle = array[low];
		while(low<high){
			while(low<high && middle.compareTo(array[high])<0)	--high;
			array[low] = array[high];
			while(low<high&&middle.compareTo(array[low])>0)	++low;
			array[high] = array[low];
		}
		array[low] = middle;
		
		return low;
	}
}
