package test.dlmu.collection.list;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class TestList {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		list.add("11111");
		list.add("22222");
		list.add("33333");
		list.add("44444");
		System.out.println("-------------foreach--------------------------------------------------");
		//cannot be used to add, remove, or modify elements.
		for (String thing : list) {
			System.out.println(thing);
		}
		System.out.println("-------------ListIterator---------------------------------------------");
		//可修改
		for (ListIterator<String> it = list.listIterator(); it.hasNext(); ) {
			String thing = it.next();
			System.out.println(thing);
			//it.remove();
			it.set("-------");
			System.out.println(list.size());
		}
		System.out.println("-------------Iterator-------------------------------------------------");
		//不可修改
		for (Iterator<String> it = list.iterator(); it.hasNext(); ) {
			String thing = it.next();
			System.out.println(thing);
			it.remove();
			System.out.println(list.size());
		}
		
	}

}
