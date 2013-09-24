package test.dlmu.lock;
/**
 * 测试
 * @author Administrator
 *
 */
public class TestLock {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		Lock lock = new Lock();
		lock.lock();
		try{
			System.out.println("");
		} finally{
			lock.unlock();
		}
	}

}
