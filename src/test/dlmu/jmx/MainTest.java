package test.dlmu.jmx;

import java.lang.management.ManagementFactory;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import javax.swing.JDialog;

public class MainTest {
	/**
	 * @param args
	 * @throws NullPointerException
	 * @throws MalformedObjectNameException
	 * @throws NotCompliantMBeanException
	 * @throws MBeanRegistrationException
	 * @throws InstanceAlreadyExistsException
	 */
	public static void main(String[] args) throws InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException, MalformedObjectNameException, NullPointerException {
		// 获得MBeanServer实例
		// MBeanServer mbs =
		// MBeanServerFactory.createMBeanServer();//不能在jconsole中使用
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();// 可在jconsole中使用
		// 创建MBean
		ControllerMBean controller = new Controller();
		// 将MBean注册到MBeanServer中
		mbs.registerMBean(controller, new ObjectName("MyappMBean:name=controller"));
		// 创建适配器，用于能够通过浏览器访问MBean
		/*HtmlAdaptorServer adapter = new HtmlAdaptorServer();
		adapter.setPort(9797);
		mbs.registerMBean(adapter, new ObjectName("MyappMBean:name=htmladapter,port=9797"));
		adapter.start();*/

		// 由于是为了演示保持程序处于运行状态，创建一个图形窗口
		javax.swing.JDialog dialog = new JDialog();
		dialog.setName("jmx test");
		dialog.setVisible(true);
	}
}
