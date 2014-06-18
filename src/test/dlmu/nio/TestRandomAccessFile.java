package test.dlmu.nio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class TestRandomAccessFile {

	public static boolean updateFile(String fName, int start, int len) {
		RandomAccessFile raf = initialFile(fName, "rw");
		try {
			System.out.println("文件总长字节是: " + raf.length());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FileChannel fc = raf.getChannel();
		MappedByteBuffer buffer = map(fc, start, len);// 映射文件中的某一部分数据以读写模式到内存中
		
		for (int i = 0; i < len; i++) {
			byte src = buffer.get(i);
			buffer.put(i, (byte) (src - 31));
			System.out.println("被改为大写的原始字节是:" + src);
		}
		
		buffer.force();
		buffer.clear();
	
		closeFileChannel(fc);
		closeRandomAccessFile(raf);
		
		return true;
	}

	// 测试主方法
	public static void main(String[] args) {
		read_writeFile("test.dat");
		updateFile("test.dat", 3, 5);
		System.out.println(" change OK... ");
	}

	private static int length = 0x8000000; // 128 Mb

    public static void read_writeFile(String fileName) {
    	File file = new File(fileName);
    	boolean exists = file.exists();
    	RandomAccessFile randomFile = null;
    	FileChannel fc = null;
    	MappedByteBuffer buffer = null;
    	try {
			randomFile = new RandomAccessFile(file, "rw");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fc = randomFile.getChannel();
        buffer = map(fc, 0, length);//注意，文件通道的可读可写要建立在文件流本身可读写的基础之上
    	if(!exists){
            for (int i = 0; i < length; i++) { //写128M的内容
            	buffer.put((byte) 'x');
            }
            System.out.println("Finished writing");
    	}
        
        for (int i = length / 2; i < length / 2 + 6; i++) {//读取文件中间6个字节内容
            System.out.print((char) buffer.get(i));
        }
		closeFileChannel(fc);
    }
	
    public static MappedByteBuffer map(FileChannel fc, long position, long size){
    	MappedByteBuffer out = null;
		try {
			out = fc.map(FileChannel.MapMode.READ_WRITE, position, size);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return out;
    }
    
	public static RandomAccessFile initialFile(String fileName, String mode){
		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile(fileName, mode);
			long totalLen = raf.length();
			System.out.println("文件总长字节是: " + totalLen);
		} catch(Exception e){
			e.printStackTrace();
		}
		return raf;
	}
	
	public static void closeFileChannel(FileChannel fc){
		try {
			if(fc!=null){
				fc.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void closeRandomAccessFile(RandomAccessFile raf){
		try {
			if(raf!=null){
				raf.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
