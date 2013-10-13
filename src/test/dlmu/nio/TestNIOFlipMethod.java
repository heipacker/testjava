package test.dlmu.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 测试Flip方法
 * 
 * @author Administrator
 * 
 */
public class TestNIOFlipMethod {
	private static final int SIZE = 1024;

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		File file = new File("D:/data.txt");
		if(!file.exists() && !file.createNewFile()){
			return;
		}
		FileOutputStream out = new FileOutputStream("D:/data.txt");
		// 获取通道，该通道允许写操作
		FileChannel fc = out.getChannel();
		// 将字节数组包装到缓冲区中
		fc.write(ByteBuffer.wrap("Some text".getBytes()));
		// 关闭通道
		fc.close();
		
		RandomAccessFile randomFile = new RandomAccessFile("data.txt", "rw");
		// 随机读写文件流创建的管道
		fc = randomFile.getChannel();
		// fc.position()计算从文件的开始到当前位置之间的字节数
		System.out.println("此通道的文件位置：" + fc.position());
		// 设置此通道的文件位置,fc.size()此通道的文件的当前大小,该条语句执行后，通道位置处于文件的末尾
		fc.position(fc.size());
		// 在文件末尾写入字节
		fc.write(ByteBuffer.wrap("Some more".getBytes()));
		fc.close();

		FileInputStream in = new FileInputStream("D:/data.txt");
		// 用通道读取文件
		fc = in.getChannel();
		ByteBuffer buffer = ByteBuffer.allocate(SIZE);
		// 将文件内容读到指定的缓冲区中
		fc.read(buffer);
		buffer.flip();// 此行语句一定要有
		while (buffer.hasRemaining()) {
			System.out.print((char) buffer.get());
		}
		fc.close();
		
		out.close();
		randomFile.close();
		in.close();
	}
}