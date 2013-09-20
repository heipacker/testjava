package test.dlmu.queue;

import java.util.LinkedList;
import java.util.Queue;

public class QueueTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Queue<String> queue = new LinkedList<String>();
		
		queue.offer("One");
		queue.offer("Two");
		queue.offer("Three");
		queue.offer("Four");
		//获取并移除此队列的头 当队列为空时 返回null
		System.out.println("Head of quue is:" + queue.poll());
		//获取并不移除此队列的头 当队列为空时 返回null
		System.out.println("Head of quue is:" + queue.peek());
		System.out.println("Head of quue is:" + queue.remove());
		//获取但是不移除此队列的头 当队列为空时 抛出异常
		System.out.println("Head of quue is:" + queue.element());
		//获取并移除此队列的头 当队列为空时抛出异常
		System.out.println("Head of quue is:" + queue.remove());
		System.out.println("Head of quue is:" + queue.remove());
	}

}
