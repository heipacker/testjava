package test.dlmu.collection.set;

import java.util.HashSet;
import java.util.Set;

public class FindDups2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		args = new String[]{"33333333","2222222","1111111","1111111"};
		Set<String> uniques = new HashSet<String>();
		Set<String> dups = new HashSet<String>();

		for (String a : args)
			if (!uniques.add(a))
				dups.add(a);

		// Destructive set-difference
		uniques.removeAll(dups);

		System.out.println("Unique words:    " + uniques);
		System.out.println("Duplicate words: " + dups);
	}

}
