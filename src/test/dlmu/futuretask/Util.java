package test.dlmu.futuretask;

import java.math.BigInteger;

public class Util {
	
	public static RuntimeException launderThrowable(Throwable cause) {
		if(cause instanceof RuntimeException){
			return (RuntimeException) cause;
		}else if(cause instanceof Error){
			throw (Error) cause;
		}else{
			throw new IllegalStateException("Not unchecked", cause);
		}
	}

	public static BigInteger[] facotr(BigInteger key) {
		// TODO Auto-generated method stub
		return new BigInteger[]{key};
	}
}
