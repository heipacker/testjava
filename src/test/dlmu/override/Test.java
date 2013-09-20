package test.dlmu.override;
class A {
	public void printValue() {
		System.out.print("A");
	}
}

class S extends A {
	public void printValue() {
		System.out.print("S");
	}
}

public class Test {
	public static void main(String[] args) {
		S s = new S();
		s.printValue();
		A as = (A) s;
		as.printValue();
		as = new A();
		as.printValue();
	}
}

interface interface3 {
	public static final int a = 1;

	public void print();
}

interface interface4 {
	public void undo(int i);
}

interface interface2 {
	public static final int a = 1;

	public void print();
}

interface interface1 extends interface2, interface3 {
	void undo(int i);

	void process();
}