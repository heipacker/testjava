package test.dlmu.jmx;

public interface ControllerMBean {
	// 属性
	public void setName(String name);

	public String getName();

	// 操作
	/**
	 * 获取当前信息
	 * 
	 * @return
	 */
	public String status();

	public void start();

	public void stop();
}
