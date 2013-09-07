package test.dlmu.testenum;
/**
 * 测试Enum支持的操作符
 * @author Administrator
 *
 */
public class TestOperation {
	public static void main(String args[]) {
		double x = Double.parseDouble(args[0]);
		double y = Double.parseDouble(args[1]);
		for (Operation op : Operation.values()){
			System.out.printf("%f %s %f = %f%n", x, op, y, op.eval(x, y));
		}
	}
}
