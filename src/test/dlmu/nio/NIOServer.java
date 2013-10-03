package test.dlmu.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * NIO�����
 * 
 */
public class NIOServer {
	// ͨ��������
	private Selector selector;

	/**
	 * ���һ��ServerSocketͨ�������Ը�ͨ����һЩ��ʼ���Ĺ���
	 * 
	 * @param port
	 *            �󶨵Ķ˿ں�
	 * @throws IOException
	 */
	public void initServer(int port) {
		// ���һ��ServerSocketͨ��
		ServerSocketChannel serverChannel = null;
		try {
			serverChannel = ServerSocketChannel.open();
			// ����ͨ��Ϊ������
			serverChannel.configureBlocking(false);
			// ����ͨ����Ӧ��ServerSocket�󶨵�port�˿�
			serverChannel.socket().bind(new InetSocketAddress(port));
			// ���һ��ͨ��������
			this.selector = Selector.open();
			// ��ͨ���������͸�ͨ���󶨣���Ϊ��ͨ��ע��SelectionKey.OP_ACCEPT�¼�,ע����¼���
			// �����¼�����ʱ��selector.select()�᷵�أ������¼�û����selector.select()��һֱ����
			serverChannel.register(selector, SelectionKey.OP_ACCEPT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(serverChannel!=null){
				try {
					serverChannel.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			if(this.selector!=null){
				try {
					this.selector.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}

	/**
	 * ������ѯ�ķ�ʽ����selector���Ƿ�����Ҫ������¼�������У�����д���
	 * 
	 * @throws IOException
	 */
	public void listen() {
		System.out.println("����������ɹ���");
		// ��ѯ����selector
		while (true) {
			// ��ע����¼�����ʱ���������أ�����,�÷�����һֱ����
			int readyChannels;
			try {
				readyChannels = selector.select();
				if (readyChannels == 0) {
					continue;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				if(selector!=null){
					try {
						selector.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
			// ���selector��ѡ�е���ĵ������ѡ�е���Ϊע����¼�
			Iterator ite = this.selector.selectedKeys().iterator();
			while (ite.hasNext()) {
				SelectionKey key = (SelectionKey) ite.next();
				ite.remove();// ɾ����ѡ��key,�Է��ظ�����
				// �ͻ������������¼�
				if(key.isValid()){
					if (key.isAcceptable()) {
						ServerSocketChannel server = (ServerSocketChannel) key.channel();
						// ��úͿͻ������ӵ�ͨ��
						SocketChannel channel = null;
						try {
							channel = server.accept();//������ģʽ�¿��ܻ᷵��null
							if(channel!=null){
								// ���óɷ�����
								channel.configureBlocking(false);
								// ��������Ը�ͻ��˷�����ϢŶ
								// channel.write(ByteBuffer.wrap(new
								// String("��ͻ��˷�����һ����Ϣ").getBytes()));
								// �ںͿͻ������ӳɹ�֮��Ϊ�˿��Խ��յ��ͻ��˵���Ϣ����Ҫ��ͨ�����ö���Ȩ�ޡ�
								channel.register(this.selector, SelectionKey.OP_READ);
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							if(channel!=null){
								try {
									channel.close();
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
						}
					} else if (key.isReadable()) {// ����˿ɶ����¼�
						read(key);
					}
				}
			}
		}
	}

	/**
	 * �����ȡ�ͻ��˷�������Ϣ ���¼�
	 * 
	 * @param key
	 * @throws IOException
	 */
	public void read(SelectionKey key) {
		// �������ɶ�ȡ��Ϣ:�õ��¼������Socketͨ��
		SocketChannel channel = (SocketChannel) key.channel();
		// ������ȡ�Ļ�����
		ByteBuffer buffer = ByteBuffer.allocate(22);
		try {
			channel.read(buffer);
			if (buffer.limit() == buffer.capacity()) {
				buffer.flip(); // make buffer ready for read\
				// while(buffer.hasRemaining()){
				// System.out.print((char) buffer.get()); // read 1 byte at a
				// time
				// }
				byte[] data = buffer.array();
				buffer.compact();
				String msg = new String(data).trim();

				System.out.println("������յ���Ϣ��" + msg);
				msg = msg.replace("�����", "�ͻ���");
				ByteBuffer outBuffer = ByteBuffer.wrap(msg.getBytes());
				channel.write(outBuffer);// ����Ϣ���͸�ͻ���
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(channel!=null){
				try {
					channel.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}

	/**
	 * ��������˲���
	 * 
	 * @throws IOException
	 */
	public static void main(String[] args) {
		NIOServer server = new NIOServer();
		server.initServer(8000);
		server.listen();

	}

}