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
		// ���MBeanServerʵ��
		// MBeanServer mbs =
		// MBeanServerFactory.createMBeanServer();//������jconsole��ʹ��
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();// ����jconsole��ʹ��
		// ����MBean
		ControllerMBean controller = new Controller();
		// ��MBeanע�ᵽMBeanServer��
		mbs.registerMBean(controller, new ObjectName("MyappMBean:name=controller"));
		// �����������������ܹ�ͨ�����������MBean
		/*HtmlAdaptorServer adapter = new HtmlAdaptorServer();
		adapter.setPort(9797);
		mbs.registerMBean(adapter, new ObjectName("MyappMBean:name=htmladapter,port=9797"));
		adapter.start();*/

		// ������Ϊ����ʾ���ֳ���������״̬������һ��ͼ�δ���
		javax.swing.JDialog dialog = new JDialog();
		dialog.setName("jmx test");
		dialog.setVisible(true);
	}
}
