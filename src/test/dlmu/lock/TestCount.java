package test.dlmu.lock;

public class TestCount {
	private long count;
	
	private Lock lock = new Lock();
	public void add(int value) throws InterruptedException{
		lock.lock();
		try{
			this.count +=value;
		} finally{
			lock.unlock();
		}
	}
	
	public long getCount(){
		return this.count;
	}
}
