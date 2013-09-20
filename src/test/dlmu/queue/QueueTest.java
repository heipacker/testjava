package test.dlmu.queue;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class QueueTest {
	
	private static class Circle implements Comparable{
		private double r;
		
		public Circle(){
			
		}
		
		public Circle(double r){
			this.setR(r);
		}

		public double getR() {
			return r;
		}

		public void setR(double r) {
			this.r = r;
		}
		
		@Override
		public String toString(){
			return "半径：" + r;
		}

		@Override
		public int compareTo(Object o) {
			if(o instanceof Circle){
				if(this.r <=((Circle)o).r){
					return 1;
				}
			}
			return -1;
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*Queue<String> queue = new LinkedList<String>();
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
		System.out.println("Head of quue is:" + queue.remove());*/
		
		//优先使用Comparator排序
		Queue<Circle> queue = new PriorityQueue<Circle>(16, new Comparator<Circle>(){

			@Override
			public int compare(Circle o1, Circle o2) {
				if(o1.r >= o2.r){
					return 1;
				}else{
					return -1;
				}
			}
			});
		//queue.offer(null);
		queue.offer(new Circle(1.0));
		queue.offer(new Circle(1.1));
		queue.offer(new Circle(1.2));
		queue.offer(new Circle(1.3));
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
