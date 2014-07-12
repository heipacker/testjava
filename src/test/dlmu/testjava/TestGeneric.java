package test.dlmu.testjava;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TestGeneric {
	
	public static class Person{
		
		private String name;
		
		private String glax;
		
	}

	public static class Student extends Person{
		
		private String nianji;
		
	}
	public static class GenericUtil {

	    public static <T> T newObject(String className) {
	        Class clz = null;
			try {
				clz = Class.forName(className);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        try {
				return (T) clz.newInstance();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
	    }
	}

	public static class InvokeRequest {
		//参数
		protected Map<String, ? super Object> params = new HashMap<String, Object>(8);

		public InvokeRequest(){	}

		public Map<String, ? super Object> getParams() {
			return params;
		}

		public void setParams(Map<String, ? super Object> params) {
			this.params = params;
		}
		
		public void putArg(String argName, Object value){
			params.put(argName, value);
		}
		
		public Object getArg(String argName){
			return params.get(argName);
		}
	}
	
	public static void testGeneric(){
		InvokeRequest request = new InvokeRequest();
		Map<String, ? super Object> params = request.getParams();
		params.put("hello", "hello");
		
		params.putAll(new HashMap<String, String>());
		
		Map<String, String> params1 = new HashMap<String, String>();
		params1.put("hello1", "hello1");
		
		Map<String, ? extends Object> params2 = params1;
		request.setParams((Map<String, ? super Object>)params2);
		System.out.println(request.getParams().size());
		Map<String, ? super Object> params3 = (Map<String, ? super Object>) params2;
		
		System.out.println(params3.size());
		
		params.putAll(params1);
		params.putAll(params2);
		
		request.setParams((Map<String, ? super Object>) params2);
		System.out.println(request.getParams().size());
		request.setParams(params);
		System.out.println(request.getParams().size());
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		display(get());
		List<? super Number> intList = new ArrayList<Number>();
		intList.add(new Integer(1)); //complier error
		intList.add(3.14); //compiler error
		testGeneric();
		//Person p = GenericUtil.newObject("com.kevin.hibernate01.Person");
	}

	public static void display(List<? extends Person> list){
		System.out.println(list.size());
		Iterator<? extends Person> it = list.iterator();
		while(it.hasNext()){
			Object p = it.next();
			
			System.out.println(p);
		}
	}
	
	public static List<? extends Person> get(){
		List<Person> personList = new LinkedList<Person>();
		
		personList.add(new Person());
		personList.add(new Student());
		
		return personList;
	}
}
