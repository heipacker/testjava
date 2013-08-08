import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;


public class CalculatorHandler implements InvocationHandler {
	private Object objOriginal;
	
	public CalculatorHandler(Object obj){
		this.objOriginal = obj;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Object result = method.invoke(this.objOriginal, args);
		return result;
	}

}
