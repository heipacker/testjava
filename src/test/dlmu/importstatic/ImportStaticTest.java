package test.dlmu.importstatic;

import static test.dlmu.importstatic.ImportStatic.DLMU;
import static test.dlmu.importstatic.ImportStatic.getDlmu;

/**
 * 
 * @author hadoop
 *
 */
public class ImportStaticTest {
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args){
		System.out.println(ImportStatic.getDlmu());
		System.out.println(ImportStatic.DLMU);
		//如果引入了import static 就不需要写ImportStatic 就想使用本类的方法一样
		System.out.println(getDlmu());
		System.out.println(DLMU);
	}
}
