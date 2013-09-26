package test.dlmu.core;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

/**
 * 获取cpu个数
 * @author Administrator
 *
 */
public class TestCore {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(System.getenv("NUMBER_OF_PROCESSORS"));
		System.out.println("==================env=======================");
		Map<String, String> map = System.getenv();
		Iterator<Entry<String, String>> it1 = map.entrySet().iterator();
		while(it1.hasNext()){
			Entry<String, String> entry = it1.next();
			System.out.println(entry.getKey() + "==" + entry.getValue());
		}
		System.out.println("==================Properties=======================");
		Properties pro = System.getProperties();
		Iterator<Entry<Object, Object>> it = pro.entrySet().iterator();
		while(it.hasNext()){
			Entry<Object, Object> entry = it.next();
			System.out.println(entry.getKey() + "==" + entry.getValue());
		}
		System.out.println(Runtime.getRuntime().availableProcessors());
	}

}
