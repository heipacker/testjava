package test.dlmu.wildcard;

import java.util.ArrayList;
import java.util.List;
/**
 * extends 可用于返回类型限定，不能用于参数类型限定。
 * super 可用于参数类型限定，不能用于返回类型限定。
 * 带有super超类型限定的通配符可以向泛型对易用写入，带有extends子类型限定的通配符可以向泛型对象读取。
 * @author Administrator
 *
 */
public class TestWildCard {
	static class Food{}
	static class Fruit extends Food{}
	static class Apple extends Fruit{}
	static class RedApple extends Apple{}
	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}
	
	static void testExtendsKey(){
		List<? extends Fruit> flist = new ArrayList<Apple>();
		// complie error:
		// flist.add(new Apple());
		// flist.add(new Fruit());
		// flist.add(new Object());
		flist.add(null); // only work for null
		
		Fruit fruit = flist.get(0);
		Apple apple = (Apple)flist.get(0);
		
		flist.contains(new Fruit());
		flist.contains(new Apple());
	}

	static void testSuperKey(){
		List<? super Fruit> flist = new ArrayList<Food>();
		flist.add(new Fruit());
		flist.add(new Apple());
		flist.add(new RedApple());

		// compile error:
		//List<? super Fruit> flist = new ArrayList<Apple>();
		
		// compile error:
		//Fruit item = flist.get(0);
	}
}
