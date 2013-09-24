package test.dlmu.testjava;
/**
 * 在编译代码的时候，会为每个构造方法生成一个对应的方法，方法名叫<init>。
 * 但并不是直接将构造方法体作为<init>方法的内容，它有这样的规则：
 * 如果构造方法中的第一条语句是通过this调用本类的其它构造方法，如类S的第一
 * 个构造方法，其完整的构造方法体就是对应的<init>方法的方法体。编译器不会为其添加一个super调用了。
 * 
 * 如果构造方法中的第一条语句不是通过this调用本类的其它构造方法，会按以下内容与顺序组成<init>方法体：
 * 1、超类<init>方法的调用。如果是显式的调用了超类构造方法，将会使用对应的超类<init>方法，如果没有写，编译器会生成一个超类无参<init>方法的调用；
 * 2、实例变量初始化代码，按实例变量在类中出现的顺序；
 * 3、构造方法中的其它方法体（如果第一句是super(…)调用，则不包含该句）。
 * 
 * 如果构造方法中包含super(…)或this(…)调用，那么它们只能作为该构造方法的第一条语句，
 * 也就是说连try…catch都不可以有。因为必须为第一条语句，所以super(…)和this(…)调用是不会出现在一起的。
 */

/**
 * 对象的构造顺序
 * @author Administrator
 *
 */
public class ClassConstructer {
	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		S s = new S();
		System.out.println(s.getV2());
	}
}

class P {
	private int v1 = 5;
	private int v2 = getV1();

	public P() throws Exception {
		System.out.println("P");
	}

	public int getV1() {
		return v1;
	}

	public int getV2() {
		return v2;
	}
}

class S extends P {
	private int value1 = 4;

	public int getV1() {
		return value1;
	}

	public S() throws Exception {
		this("S()");
	}

	public S(String msg) throws Exception {
		System.out.println(msg);
	}

	public S(int v) throws Exception {
		super();
		System.out.println("abc");
	}
}