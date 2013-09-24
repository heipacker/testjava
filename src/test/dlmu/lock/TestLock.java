package test.dlmu.lock;
/**
 * 测试
 * @author Administrator
 *
 */
public class TestLock extends Thread{
	private TestCount count;
	
	public TestLock(TestCount count){
		this.count = count;
	}
	
	@Override
	public void run(){
		for(int i = 0; i<1000000; ++i){
			try {
				count.add(i);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return ;
			}
		}
	}
	private static int THREAD_COUNT = 10;
	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args){
		TestCount count = new TestCount();
		Thread[] threads = new Thread[THREAD_COUNT];
		for(int i = 0; i < threads.length; ++i){
			threads[i] = new TestLock(count);
			threads[i].start();
		}
		
		for(int i = 0; i < threads.length; ++i){
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		System.out.println("result:" + count.getCount());
	}

}
