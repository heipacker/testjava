package test.dlmu.futuretask;

import java.math.BigInteger;

public class ExpensiveFunction implements Computable<String, BigInteger>{

	@Override
	public BigInteger compute(String str) {
		// TODO Auto-generated method stub
		return new BigInteger(str);
	}
}
