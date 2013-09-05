package test.dlmu.testjava;

public class ClassEchoTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ClassEchoTest cet = new ClassEchoTest();
		cet.logPriviledgedAction();
	}

	private void logPriviledgedAction() {
		// would be nice if action included a descriptive toString()
		String where = new Throwable().getStackTrace()[1].toString();
		System.out.println("PriviledgedAction as:" + this + " from:" + where);
	}
}
