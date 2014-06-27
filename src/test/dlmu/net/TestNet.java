package test.dlmu.net;

import java.net.MalformedURLException;
import java.net.URL;

public class TestNet {
	
	private static final String url = "http://127.0.0.1/dataservicebus/services/depthbaseWebService?wsdl";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		getTargetHostName();
	}
	
	private static String getTargetHostName(){
		URL t = null;
		try {
			t = new URL(url);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(t.getHost());
		System.out.println(t.getAuthority());
		System.out.println(t.getPort());
		return t.getHost();
	}
}
