import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;


public class DynamicProxyExample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CalculatorProtocol server = new Server();
		
		InvocationHandler handler = new CalculatorHandler(server);
		CalculatorProtocol client = (CalculatorProtocol) Proxy.newProxyInstance(server.getClass().getClassLoader(), server.getClass().getInterfaces(), handler);
		//创建一个CLIENT
		int r = client.add(5, 3);
		System.out.println("result:"+r);
	}

}
