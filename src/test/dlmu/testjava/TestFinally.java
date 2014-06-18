package test.dlmu.testjava;

public class TestFinally {
	public static void main(String... args) {
		System.out.println(getValue1());
		System.out.println(getValue2());
	}

	public static int getValue1() {
		int i1 = 0;
		int i2 = 1;
		try {
			return i1;
		} finally {
			return i2;
		}
	}

	public static int getValue2() {
		int i = 1;
		try {
			return i;
		} finally {
			i++;
		}
	}
}
