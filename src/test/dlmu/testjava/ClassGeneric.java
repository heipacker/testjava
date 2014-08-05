package test.dlmu.testjava;

public class ClassGeneric<T> {

	public ClassGeneric(Class<T> interfac, Class<? extends T> defaultImpl){
		
	}
	
	public static interface A{
		
	}
	
	public static class B implements A{
		
	}
	
	public static void main(String[] args){
		ClassGeneric<A> generic = new ClassGeneric<A>(A.class, B.class);
	}
}
