package test.dlmu.collection.set;

import java.util.SortedSet;
import java.util.TreeSet;

public class TestSort {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SortedSet<String> s = new TreeSet<String>();
		s.add(new String("Red"));
		s.add(new String("White"));
		s.add(new String("Blue"));
		System.out.println(s.first());
	}

}
