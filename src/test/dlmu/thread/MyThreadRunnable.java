package test.dlmu.thread;

public class MyThreadRunnable implements Runnable{
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args){
		Thread[] thread = new Thread[THREAD_LENGTH];
		for(int i = 0; i<thread.length; ++i){
			thread[i] = new Thread(new MyThreadRunnable(), "thread" + i);
			thread[i].start();
		}
		
		for(int i = 0; i<thread.length; ++i){
			try {
				thread[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private static final int THREAD_LENGTH = 5;
	
	@Override
	public void run(){
		for(int i = 0; i<20; ++i){
			System.out.println(Thread.currentThread().getName() + ":" + i);
		}
	}
}
