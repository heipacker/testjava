package test.dlmu.pbtest.server.business;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import test.dlmu.pbtest.api.Calculator;
import test.dlmu.pbtest.ipc.Server;

import com.google.protobuf.BlockingService;

/**
 * 计算服务
 * 主要是创建服务端 等待客户端请求
 * @author Administrator
 * 
 */
public class CalculatorService implements Calculator {
	private Server server = null;
	private final Class<?> protocol = Calculator.class;
	private final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	private final String protoPackage = "test.dlmu.pbtest.proto";
	private final String serverPackage = "test.dlmu.pbtest.server.api";
	private final int port = 8038;

	public CalculatorService() {

	}

	@Override
	public int add(int a, int b) {
		// TODO Auto-generated method stub
		return a + b;
	}

	public int minus(int a, int b) {
		return a - b;
	}

	public void init() {
		createServer();
	}

	/*
	 * return test.dlmu.pbtest.server.api.CalculatorPBServiceImpl
	 */
	public Class<?> getPbServiceImplClass() {
		String className = protocol.getSimpleName();
		String pbServiceImplName = serverPackage + "." + className + "PBServiceImpl";
		Class<?> clazz = null;
		try {
			clazz = Class.forName(pbServiceImplName, true, classLoader);
		} catch (ClassNotFoundException e) {
			System.err.println(e.toString());
		}
		return clazz;
	}

	/*
	 * return test.dlmu.pbtest.proto.Calculator$CalculatorService
	 */
	public Class<?> getProtoClass() {
		String className = protocol.getSimpleName();
		String protoClazzName = protoPackage + "." + className + "$" + className + "Service";
		Class<?> clazz = null;
		try {
			clazz = Class.forName(protoClazzName, true, classLoader);
		} catch (ClassNotFoundException e) {
			System.err.println(e.toString());
		}
		return clazz;
	}

	/**
	 * 创建服务端
	 */
	public void createServer() {
		Class<?> pbServiceImpl = getPbServiceImplClass();
		Constructor<?> constructor = null;
		try {
			constructor = pbServiceImpl.getConstructor(protocol);
			constructor.setAccessible(true);
		} catch (NoSuchMethodException e) {
			System.err.print(e.toString());
		}

		Object service = null; // instance of CalculatorPBServiceImpl
		try {
			service = constructor.newInstance(this);
		} catch (InstantiationException e) {
		} catch (IllegalArgumentException e) {
		} catch (IllegalAccessException e) {
		} catch (InvocationTargetException e) {
		}

		/*
		 * interface: test.dlmu.pbtest.server.CalculatorPB
		 */
		Class<?> pbProtocol = service.getClass().getInterfaces()[0];

		/*
		 * class: test.dlmu.pbtest.proto.Calculator$CalculatorService
		 */
		Class<?> protoClazz = getProtoClass();

		Method method = null;
		try {
			// test.dlmu.pbtest.proto.Calculator$CalculatorService$BlockingInterface
			method = protoClazz.getMethod("newReflectiveBlockingService", pbProtocol.getInterfaces()[0]);
			method.setAccessible(true);
		} catch (NoSuchMethodException e) {
			System.err.print(e.toString());
		}

		try {
			// 需要协议类名及服务实现实例
			createServer(pbProtocol, (BlockingService) method.invoke(null, service)/** 创建代理**/);
		} catch (InvocationTargetException e) {

		} catch (IllegalArgumentException e) {

		} catch (IllegalAccessException e) {

		}

	}

	public void createServer(Class pbProtocol, BlockingService service) {
		server = new Server(pbProtocol, service, port);
		server.start();
	}

	public static void main(String[] args) {
		CalculatorService cs = new CalculatorService();
		cs.init();
	}
}
