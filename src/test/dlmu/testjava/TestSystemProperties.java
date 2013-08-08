package test.dlmu.testjava;

import java.util.Iterator;
import java.util.Properties;

public class TestSystemProperties {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Properties p = System.getProperties();
	  for (Iterator<?> it = p.keySet().iterator(); it.hasNext();) {
	        String key = (String) it.next();
	        String val = p.getProperty(key);
	        System.out.println("Property '" + key + "' is " + val);
	      }
	}

}
