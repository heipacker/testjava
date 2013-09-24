package test.dlmu.semaphore;

public class TestSemaphore extends Thread{
	static class TestTake extends Thread{
		private Semaphore semaphore;
		
		public TestTake(Semaphore semaphore){
			this.semaphore = semaphore;
		}
		
		@Override
		public void run() {
			// do something
			semaphore.take();
		}
	}
	
	static class TestRelease extends Thread{
		private Semaphore semaphore;
		
		public TestRelease(Semaphore semaphore){
			this.semaphore = semaphore;
		}
		
		@Override
		public void run() {
			// do something
			try {
				semaphore.release();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		Semaphore semaphore = new Semaphore();
		
		TestTake take = new TestTake(semaphore);
		TestRelease release = new TestRelease(semaphore);
		
		take.start();
		release.start();
		
		take.join();
		release.join();
	}

}
