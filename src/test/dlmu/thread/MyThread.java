package test.dlmu.thread;

public class MyThread extends Thread{
	
	public MyThread(String name){
		super(name);//初始化线程名称
	}
	
	@Override
	public void run(){
		for(int i = 0; i<20; ++i){
			System.out.println(this.getName() + ":" + i);
		}
	}
	
	private static final int THREAD_LENGTH = 5;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Thread[] thread = new Thread[THREAD_LENGTH];
		
		for(int i = 0; i<thread.length; ++i){
			thread[i] = new MyThread("thread" + i);
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

}
