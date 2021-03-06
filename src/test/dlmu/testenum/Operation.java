package test.dlmu.testenum;

public enum Operation {
	PLUS, MINUS, TIMES, DIVIDE;

	// Do arithmetic op represented by this constant
	double eval(double x, double y) {
		switch (this) {
			case PLUS:
				return x + y;
			case MINUS:
				return x - y;
			case TIMES:
				return x * y;
			case DIVIDE:
				return x / y;
		}
		throw new AssertionError("Unknown op: " + this);
	}
}