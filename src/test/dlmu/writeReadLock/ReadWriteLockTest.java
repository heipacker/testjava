package test.dlmu.writeReadLock;

import java.util.Calendar;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockTest {

	private ReentrantReadWriteLock lock = null;

	private Lock readLock = null;// 读锁

	private Lock writeLock = null;// 写锁

	public int key = 100;
	public int index = 100;

	public Map<Integer, String> dataMap = null; // 共享数据

	public ReadWriteLockTest() {
		lock = new ReentrantReadWriteLock(true);
		readLock = lock.readLock();
		writeLock = lock.writeLock();
		dataMap = new TreeMap<Integer, String>();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ReadWriteLockTest tester = new ReadWriteLockTest();

		// 第一次获取锁 tester.writeLock.loc
		tester.writeLock.lock();

		System.out.println(Thread.currentThread().getName() + " getted writeLock！");

		// 第二次获取锁，应为是可重入
		tester.writeLock.lock();

		System.out.println(Thread.currentThread().getName() + " getted writeLock！");
		tester.readLock.lock();
		System.out.println(Thread.currentThread().getName() + " getted readLock！");
		tester.readLock.lock();
		System.out.println(Thread.currentThread().getName() + " getted readLock！");
		tester.readLock.unlock();
		tester.readLock.unlock();
		tester.writeLock.unlock();
		tester.writeLock.unlock();
		tester.test();
	}

	private void test() {
		// 读线程比写线程多
		for (int i = 0; i < 10; i++) {
			new Thread(new Reader(this)).start();
		}
		for (int i = 0; i < 3; i++) {
			new Thread(new Writer(this)).start();
		}
	}

	public void read() {
		readLock.lock();
		try {
			if (dataMap.isEmpty()) {
				Calendar now = Calendar.getInstance();
				System.out.println(now.getTime().getTime() + " R " + Thread.currentThread().getName()
						+ " get key, but map is empty！ ");
			} else {
				String value = dataMap.get(index);
				Calendar now = Calendar.getInstance();
				System.out.println(now.getTime().getTime() + " R " + Thread.currentThread().getName()
						+ " key = " + index + " value = " + value + " map size = " + dataMap.size());
				if (value != null) {
					index++;
				}
			}
		} finally {
			readLock.unlock();
		}

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void write() {
		writeLock.lock();
		try {
			String value = "Value" + key;

			dataMap.put(new Integer(key), value);
			Calendar now = Calendar.getInstance();
			System.out.println(now.getTime().getTime() + " W " + Thread.currentThread().getName()
					+ " key = " + key + " value = " + value + " map size = " + dataMap.size());
			key++;

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} finally {
			writeLock.unlock();
		}
	}
}

/**
 * 写线程
 * 
 * @author Administrator
 * 
 */
class Reader implements Runnable {

	private ReadWriteLockTest tester = null;

	public Reader(ReadWriteLockTest tester) {
		this.tester = tester;
	}

	@Override
	public void run() {
		Calendar now = Calendar.getInstance();

		System.out.println(now.getTime().getTime() + " R " + Thread.currentThread().getName() + " started！");
		for (int i = 0; i < 10; ++i) {
			tester.read();
		}
	}

}

/**
 * 读线程
 * 
 * @author Administrator
 * 
 */
class Writer implements Runnable {
	private ReadWriteLockTest tester = null;

	public Writer(ReadWriteLockTest tester) {
		this.tester = tester;
	}

	@Override
	public void run() {
		Calendar now = Calendar.getInstance();

		System.out.println(now.getTime().getTime() + " W " + Thread.currentThread().getName() + " started！");
		for (int i = 0; i < 10; ++i) {
			tester.write();
		}
	}
}
