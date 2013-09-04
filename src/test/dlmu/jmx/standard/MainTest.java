package test.dlmu.jmx.standard;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;

public class MainTest {
	private static ObjectName objectName;
	private static MBeanServer mBeanServer;

	public static void main(String[] args) throws Exception {
		init();
		manage();
	}

	private static void init() throws Exception {
		ServerImpl serverImpl = new ServerImpl();
		ServerMonitor serverMonitor = new ServerMonitor(serverImpl);
		mBeanServer = MBeanServerFactory.createMBeanServer();
		objectName = new ObjectName("objectName:id=ServerMonitor1");
		mBeanServer.registerMBean(serverMonitor, objectName);
	}

	private static void manage() throws Exception {
		Long upTime = (Long) mBeanServer.getAttribute(objectName, "UpTime");
		System.out.println(upTime);
	}
}