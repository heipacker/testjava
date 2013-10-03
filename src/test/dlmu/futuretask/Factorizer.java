package test.dlmu.futuretask;

import java.io.IOException;
import java.math.BigInteger;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
/**
 * 线程安全的Servlet
 * @author Administrator
 *
 */
public class Factorizer implements Servlet {
	private final Computable<BigInteger, BigInteger[]> c = new Computable<BigInteger, BigInteger[]>(){

		@Override
		public BigInteger[] compute(BigInteger key) throws InterruptedException {
			// TODO Auto-generated method stub
			return Util.facotr(key);
		}
	};
	
	private final Computable<BigInteger, BigInteger[]> cache = new Memorizer4<BigInteger, BigInteger[]>(c);
	
	@Override
	public void service(ServletRequest request, ServletResponse response)
			throws ServletException, IOException {
		BigInteger i = extractFromRequest(request);
		try {
			encodeIntoResponse(response, cache.compute(i));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void encodeIntoResponse(ServletResponse response,
			BigInteger[] compute) {
		// TODO Auto-generated method stub
		
	}

	private BigInteger extractFromRequest(ServletRequest req) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ServletConfig getServletConfig() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getServletInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void init(ServletConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
}
