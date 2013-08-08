package test.rpc.nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;

public class Server {
	private InetSocketAddress address = null;
	
	private InetAddress bindAddress = null;
	
	private int port = 8080;
	
	private ServerSocketChannel acceptChannel = null;
	/**
	 * 队列长度
	 */
	private int backloglength = 20;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	/**
	 * 初始化服务端
	 * @return
	 * @throws IOException 
	 */
	public Selector initSelector() throws IOException{
		address = new InetSocketAddress(bindAddress, port);
		/**
		 * 创建一个ServerSocketChannel, 并将之设置为非阻塞模式
		 */
		acceptChannel = ServerSocketChannel.open();
		
		acceptChannel.configureBlocking(false);
		
		//将Server Socket 绑定到ADDRESS上，并设置监听队列长度
		acceptChannel.socket().bind(address, backloglength);
		Selector selector = Selector.open();
		//向Selector 注册ServerScoketChannel, 注册事件为SelectionKey.OP_ACCEPT
		acceptChannel.register(selector, SelectionKey.OP_ACCEPT);
		
		return selector;
	}
	
	public void run(){
		try {
			Selector selector = initSelector();
			while(true){
				SelectionKey key = null;
				selector.select();
				Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
				while(iter.hasNext()){
					key = iter.next();
					iter.remove();
					if(!key.isValid()){
						continue;
					}
					if(key.isAcceptable()){
						doAccept(key);//新客户端要求连接
					}else if(key.isReadable()){
						receive(key);//接收数据
					}else if(key.isWritable()){
						send(key);//发送数据
					}
					key = null;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	private void send(SelectionKey key) {
		// TODO Auto-generated method stub
		
	}
	private void receive(SelectionKey key) {
		// TODO Auto-generated method stub
		
	}
	private void doAccept(SelectionKey key) {
		// TODO Auto-generated method stub
		
	}
}
