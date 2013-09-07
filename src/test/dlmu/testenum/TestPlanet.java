package test.dlmu.testenum;
/**
 * Planet测试
 * @author Administrator
 *
 */
public class TestPlanet {
	public static void main(String[] args) {
		double earthWeight = Double.parseDouble(args[0]);
		/** 质量  */
		double mass = earthWeight / Planet.EARTH.surfaceGravity();
		for (Planet p : Planet.values())
			System.out.printf("Your weight on %s is %f%n", p, p.surfaceWeight(mass));
	}
}
