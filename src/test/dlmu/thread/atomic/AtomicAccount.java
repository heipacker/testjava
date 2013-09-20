package test.dlmu.thread.atomic;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 账户信息
 * @author Administrator
 *
 */
public class AtomicAccount {
	private AtomicLong balance;
	
	public long get(){
		return balance.get();
	}
	
	public AtomicAccount(long money){
		this.balance = new AtomicLong(money);
		
		System.out.println("Total money:" + this.balance);
	}
	/**
	 * 存款
	 * @param money
	 */
	public void deposit(long money){
		balance.addAndGet(money);
	}
	/**
	 * 撤回
	 * @param money
	 * @param delay
	 */
	public void withdraw(long money, int delay){
		long oldValue = balance.get();
		if(oldValue >= money){
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(balance.compareAndSet(oldValue, oldValue - money)){
				System.out.println(Thread.currentThread().getName() + " withdraw " + money + " successful！ " + balance);
			}else{
				System.out.println(Thread.currentThread().getName() + " thread concurrent, withdraw failed!" + balance);
			}
		}else{
			System.out.println(Thread.currentThread().getName() + " balace is not enough, withdraw failed! " + balance);
		}
	}
}
