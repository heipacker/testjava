package test.dlmu.thread;

public class TestPrintAllThread {
	public static Thread[] getAllThread() {
		ThreadGroup root = Thread.currentThread().getThreadGroup();
		ThreadGroup ttg = root;
		while ((ttg = ttg.getParent()) != null) root = ttg;
		Thread[] tlist = new Thread[(int)(root.activeCount() * 1.2)];
		return java.util.Arrays.copyOf(tlist, root.enumerate(tlist, true));
	}

	public static void main(String[] args) {
	   	
    	Thread javaGeneratThread = new Thread("javaGeneratThread"){
    		
    		public void run(){
    			try {
					Thread.sleep(1000*30);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    	};
    	javaGeneratThread.start();
    	
    	
    	Thread newObjectThread = new Thread("newObjectThread"){
    		
    		public void run(){
    			try {
					Thread.sleep(1000*30);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}

    	};
    	newObjectThread.start();
    	
		Thread[] ts = getAllThread();
		for (Thread t : ts) {
			
			if(t.getThreadGroup().getName().equals("main")){
				System.out.println(t.isDaemon());
				System.out.println(t.getId() + ": " + t.getName());
			}
			
		}

    	try {
			newObjectThread.join();
			javaGeneratThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 
	}
}
