package test.dlmu.jmx.standard;

public class ServerMonitor implements ServerMonitorMBean {
	private final ServerImpl target;

	public ServerMonitor(ServerImpl target) {
		this.target = target;
	}

	public long getUpTime() {
		return System.currentTimeMillis() - target.startTime;
	}

	@Override
	public void setUpTime(long upTime) {
		target.startTime = upTime;
	}
}