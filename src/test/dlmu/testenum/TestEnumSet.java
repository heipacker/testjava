package test.dlmu.testenum;

import java.util.EnumSet;

public class TestEnumSet {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		for (Day d : EnumSet.range(Day.MONDAY, Day.FRIDAY))
			System.out.println(d);
	}

	public enum Day {
		SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY
	}
}
