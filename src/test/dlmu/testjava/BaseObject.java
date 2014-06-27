package test.dlmu.testjava;

import java.lang.reflect.Field;

public class BaseObject {
	
	private String test;

	public BaseObject(){
		test = "111111111111";
		Field field = null;
		try {
			field = this.getClass().getDeclaredField("name");
			field.setAccessible(true);
			System.out.println(field.get(this));
			field.set(this, "heipacker");
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			System.out.println(field.get(this));
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
