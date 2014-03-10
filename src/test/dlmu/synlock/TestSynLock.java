package test.dlmu.synlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class TestSynLock {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	ReadWriteLock lock = new ReentrantReadWriteLock();
	
	Lock readLock = lock.readLock();
	
	public void test(){
		readLock.lock();
		try{
			System.out.println();
		} finally{
			readLock.unlock();
		}
	}

}
