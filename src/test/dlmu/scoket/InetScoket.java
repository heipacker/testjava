package test.dlmu.scoket;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class InetScoket {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Server().start();
		new Client().start();
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.exit(0);
	}

}
class Server extends Thread{
	public void run(){
		BufferedReader in = null;
		try {
			/*InetSocketAddress namenode = new InetSocketAddress(InetAddress.getLocalHost(), 8082);
			System.out.println(namenode.getHostName());
			System.out.println(namenode.toString());*/
			
			ServerSocket serverSocket = new ServerSocket(8082);
			//serverSocket.bind(namenode);
			while(true){
				System.out.println("----------111----------");
				Socket scoket = serverSocket.accept();
				
				in = new BufferedReader(new InputStreamReader(scoket.getInputStream()));
				System.out.println(in.readLine());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
				if(in!=null)
					in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
class Client extends Thread{
	public Client(){
		
	}
	
	public void run(){
		BufferedWriter out = null;
		Socket clientScoket = null;
		try {
			//InetSocketAddress namenode = new InetSocketAddress(InetAddress.getLocalHost(), 8082);
			clientScoket = new Socket(InetAddress.getLocalHost(), 8082);
			//clientScoket.connect(namenode);
			out = new BufferedWriter(new OutputStreamWriter(clientScoket.getOutputStream()));
			while(true){
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("----------1----------");
				out.write("hello world!\nhello world!hello world!hello world!");
				System.out.println("----------2----------");
				out.write("\n");
				out.flush();
				//out.close();
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if(out!=null){
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(clientScoket!=null){
				try {
					clientScoket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
