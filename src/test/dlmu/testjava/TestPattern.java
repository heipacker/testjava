package test.dlmu.testjava;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestPattern {
	private static Pattern varPat = Pattern.compile("\\$\\{[^\\}\\$\u0020]+\\}");
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String test = "sdf${stsat${sfs}}sf";
		
		Matcher match = varPat.matcher(test);
		
		if(match.find()){
			System.out.println(test.substring(match.start(), match.end()));
		}
	}

}
