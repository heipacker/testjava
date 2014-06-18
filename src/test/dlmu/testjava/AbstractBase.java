package test.dlmu.testjava;

public abstract class AbstractBase {
	public AbstractBase(){
		Class[] intfs = this.getClass().getInterfaces();
		for(int i = 0; i<intfs.length; ++i){
			System.out.println(intfs[i].getName());
		}
		
	}
}
