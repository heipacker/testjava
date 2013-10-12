package test.dlmu.file.lock;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Thread_writeFile thf3=new Thread_writeFile();    
        Thread_readFile thf4=new Thread_readFile();    
        thf3.start();    
        thf4.start();
	}

}
