package test.dlmu.testjaas.authentication;

import java.security.AccessControlContext;
import java.security.AccessController;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

public class LoginManager {
	//把所有文件放在C盘下
	//文件的结构如下：
	//./
	//	TestJaas.jar
	//	jaas.config
	//	jaas.policy
	//java -classpath TestJaas.jar -Djava.security.manager \
	//-Djava.security.policy=jaas.policy \
	//-Djava.security.auth.login.config==jaas.config test.dlmu.testjaas.LoginManager
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Obtain a LoginContext, needed for authentication.
		// Tell it to use the LoginModule implementation
		// specified by the entry named "Sample" in the
		// JAAS login configuration file and to also use the
		// specified CallbackHandler.
		LoginContext lcx = null;
		try {
			Subject subject = new Subject();
			lcx = new LoginContext("Sample", subject, new SampleCallbackHandler("userName","password"));
		} catch (LoginException le) {
			System.err.println("Cannot create LoginContext. " + le.getMessage());
			System.exit(-1);
		} catch (SecurityException se) {
			System.err.println("Cannot create LoginContext. " + se.getMessage());
			System.exit(-1);
		}

		// the user has 3 attempts to authenticate successfully
		int i;
		for (i = 0; i < 3; i++) {
			try {
				// attempt authentication
				lcx.login();
				// if we return with no exception, authentication succeeded
				break;
			} catch (LoginException le) {
				System.err.println("Authentication failed:");
				System.err.println("  " + le.getMessage());
				try {
					Thread.sleep(3000);
				} catch (Exception e) {
					// ignore
				}
			}
		}

		// did they fail three times?
		if (i == 3) {
			System.out.println("Sorry");
			System.exit(-1);
		}
		//放回当前线程的AccessControlContext
		AccessControlContext acc = AccessController.getContext();
		//一个AccessControlContext包含很多个Subject 此方法返回最近使用的那个
		Subject sub = Subject.getSubject(acc);
		if(sub!=null){
			System.out.println("is not null");
		}
		
		Subject subject = lcx.getSubject();
		if(subject!=null){
			System.out.println("is not null");
			System.out.println(subject.getPrincipals().toString());
			System.out.println("is read only:"+subject.isReadOnly());
		}
		System.out.println("Authentication succeeded!");
	}
}
