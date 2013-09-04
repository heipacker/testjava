package test.dlmu.testjaas;

import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

import com.sun.security.auth.callback.TextCallbackHandler;

public class JaasAcn {

	public static void main(String[] args) {
		// Obtain a LoginContext, needed for authentication. Tell
		// it to use the LoginModule implementation specified by
		// the entry named "JaasSample" in the JAAS login
		// configuration file and to also use the specified
		// CallbackHandler.
		// com.sun.security.auth.module.Krb5LoginModule required;
		LoginContext lc = null;
		try {
			lc = new LoginContext("JaasSample", new TextCallbackHandler());
		} catch (LoginException le) {
			System.err.println("Cannot create LoginContext. " + le.getMessage());
			System.exit(-1);
		} catch (SecurityException se) {
			System.err.println("Cannot create LoginContext. " + se.getMessage());
			System.exit(-1);
		}

		try {
			lc.login();
		} catch (LoginException le) {
			System.err.println("Authentication failed: ");
			System.err.println("  " + le.getMessage());
			System.exit(-1);
		}

		System.out.println("Authentication succeeded!");
	}
}