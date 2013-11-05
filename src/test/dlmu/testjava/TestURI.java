package test.dlmu.testjava;

import java.net.URI;

public class TestURI {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//test file
		System.out.println("test file-----------------------------");
		testURI("file:///");
		System.out.println("test http-----------------------------");
		testURI("http://www.baidu.com:8080/user/test.jsp");
	}
	public static void testURI(String type){
		URI filesystemURI = URI.create(type);
		String authority = filesystemURI.getAuthority();
		if (authority == null) {
			System.out.println(type + " authority is null!");
		}else{
			System.out.println(authority);
		}
		
		String scheme = filesystemURI.getScheme();
		if (scheme == null) {
			System.out.println(type + " scheme is null!");
		}else{
			System.out.println("scheme:" + scheme);
		}
		
		String host = filesystemURI.getHost();
		if (host == null) {
			System.out.println(type + " host is null!");
		}else{
			System.out.println("host:" + host);
		}
			System.out.println("port:" + filesystemURI.getPort());
	}
}
