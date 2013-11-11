package test.dlmu.search.binary;

public interface Search<T extends Comparable<T>> {
	
	public int search(T[] target, T key);
}
