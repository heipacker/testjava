package test.dlmu.testjava;

/**
 * 需要实现Cloneable接口标识当前类可克隆
 * 否则运行时会出错
 * 
 * @author Administrator
 *
 */
public class TestCloneable implements Cloneable{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TestCloneable cl = new TestCloneable();
		try {
			cl.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public TestCloneable clone() throws CloneNotSupportedException{
		
		return (TestCloneable) super.clone();
	}
}
