package test.dlmu.file.lock;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.Calendar;
/**
 * 读线程
 * @author Administrator
 *
 */
public class Thread_readFile extends Thread {
	public void run() {
		FileLock flin = null;
		FileChannel fcin = null;
		RandomAccessFile fis = null;
		try {
			Calendar calstart = Calendar.getInstance();
			sleep(5000);
			File file = new File("D:/test.txt");

			// 给该文件加锁
			fis = new RandomAccessFile(file, "rw");
			fcin = fis.getChannel();
			while (true) {
				try {
					flin = fcin.tryLock();
					break;
				} catch (Exception e) {
					System.out.println("有其他线程正在操作该文件，当前线程休眠1000毫秒");
					sleep(1000);
				}

			}
			byte[] buf = new byte[1024];
			StringBuffer sb = new StringBuffer();
			while ((fis.read(buf)) != -1) {
				sb.append(new String(buf, "utf-8"));
				buf = new byte[1024];
			}

			System.out.println(sb.toString());

			Calendar calend = Calendar.getInstance();
			System.out.println("读文件共花了" + (calend.getTimeInMillis() - calstart.getTimeInMillis()) + "秒");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			try {
				if(flin!=null){
					flin.release();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				if(fcin!=null){
					fcin.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				if(fis!=null){
					fis.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}