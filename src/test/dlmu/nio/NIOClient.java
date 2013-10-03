package test.dlmu.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * NIO�ͻ���
 * 
 */
public class NIOClient {
	// ͨ��������
	private Selector selector;

	/**
	 * ���һ��Socketͨ�������Ը�ͨ����һЩ��ʼ���Ĺ���
	 * 
	 * @param ip
	 *            ���ӵķ�������ip
	 * @param port
	 *            ���ӵķ������Ķ˿ں�
	 * @throws IOException
	 */
	public void initClient(String ip, int port){
		// ���һ��Socketͨ��
		SocketChannel channel = null;
		try {
			channel = SocketChannel.open();
			// ����ͨ��Ϊ������
			channel.configureBlocking(false);
			// ���һ��ͨ��������
			this.selector = Selector.open();
			// �ͻ������ӷ�����,��ʵ����ִ�в�û��ʵ�����ӣ���Ҫ��listen���������е�
			// ��channel.finishConnect();�����������
			channel.connect(new InetSocketAddress(ip, port));
			// ��ͨ���������͸�ͨ���󶨣���Ϊ��ͨ��ע��SelectionKey.OP_CONNECT�¼���
			channel.register(selector, SelectionKey.OP_CONNECT);
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
	 * ������ѯ�ķ�ʽ����selector���Ƿ�����Ҫ������¼�������У�����д���
	 * 
	 * @throws IOException
	 */
	public void listen() {
		// ��ѯ����selector
		while (true) {
			int readyChannels = 0;
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
			// ���selector��ѡ�е���ĵ����
			Iterator ite = this.selector.selectedKeys().iterator();
			while (ite.hasNext()) {
				SelectionKey key = (SelectionKey) ite.next();
				// ɾ����ѡ��key,�Է��ظ�����
				ite.remove();
				// �����¼�����
				if (key.isValid()&&key.isConnectable()) {
					SocketChannel channel = (SocketChannel) key.channel();
					// ����������ӣ����������
					if (channel.isConnectionPending()) {
						try {
							channel.finishConnect();
							// ���óɷ�����
							channel.configureBlocking(false);
							// ��������Ը����˷�����ϢŶ
							channel.write(ByteBuffer.wrap(new String(
									"�����˷�����һ����Ϣ").getBytes()));
							// �ںͷ�������ӳɹ�֮��Ϊ�˿��Խ��յ�����˵���Ϣ����Ҫ��ͨ�����ö���Ȩ�ޡ�
							channel.register(this.selector,SelectionKey.OP_READ);
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
				} else if (key.isReadable()) {// ����˿ɶ����¼�
					read(key);
				}
			}
		}
	}

	/**
	 * �����ȡ����˷�������Ϣ ���¼�
	 * 
	 * @param key
	 * @throws IOException
	 */
	public void read(SelectionKey key) {
		// �ͻ��˿ɶ�ȡ��Ϣ:�õ��¼������Socketͨ��
		SocketChannel channel = (SocketChannel) key.channel();
		// ������ȡ�Ļ�����
		ByteBuffer buffer = ByteBuffer.allocate(22);
		try {
			channel.read(buffer);
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
		if (buffer.limit() == buffer.capacity()) {
			buffer.flip(); // make buffer ready for read
			byte[] data = buffer.array();
			buffer.compact();
			String msg = new String(data).trim();
			System.out.println("�ͻ����յ���Ϣ��" + msg);
			msg = msg.replace("�ͻ���", "�����");
			ByteBuffer outBuffer = ByteBuffer.wrap(msg.getBytes());
			try {
				channel.write(outBuffer);// ����Ϣ���͸�ͻ���
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
	}

	/**
	 * �����ͻ��˲���
	 * 
	 * @throws IOException
	 */
	public static void main(String[] args) {
		NIOClient client = new NIOClient();
		client.initClient("localhost", 8000);
		client.listen();
	}

}