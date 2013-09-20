package test.dlmu.thread.atomic;

public class AccountThread extends Thread {
	private AtomicAccount account;

	private int delay;

	public AccountThread(AtomicAccount account, int delay) {
		this.account = account;
		this.delay = delay;
	}

	@Override
	public void run() {
		account.withdraw(100, delay);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AtomicAccount acount = new AtomicAccount(100);
		AccountThread thread1 = new AccountThread(acount, 1000);
		AccountThread thread2 = new AccountThread(acount, 0);
		thread1.start();
		thread2.start();
	}

}
