package test.dlmu.testjaas;

import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

public class LoginManager {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LoginContext lc = null;
		try {
			// 此处指定了使用配置文件的“Sample”验证模块，对应的实现类为SampleLoginModule
			lc = new LoginContext("JaasSample", new SampleCallbackHandler("username", "password"));
			//lc = new LoginContext("hadoop-simple", null, null, new HadoopConfiguration());
		} catch (LoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			lc.login();
		} catch (LoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
/*	private static String OS_LOGIN_MODULE_NAME;
	
	static {
		OS_LOGIN_MODULE_NAME = getOSLoginModuleName();
	}
	 Return the OS login module class name 
	private static String getOSLoginModuleName() {
		return "com.sun.security.auth.module.NTLoginModule";
	}
	*//**
	 * A JAAS configuration that defines the login modules that we want to use
	 * for login.
	 *//*
	private static class HadoopConfiguration extends javax.security.auth.login.Configuration {
		private static final String SIMPLE_CONFIG_NAME = "hadoop-simple";

		private static final AppConfigurationEntry OS_SPECIFIC_LOGIN = new AppConfigurationEntry(OS_LOGIN_MODULE_NAME, LoginModuleControlFlag.REQUIRED, new HashMap<String, String>());
		private static final AppConfigurationEntry HADOOP_LOGIN = new AppConfigurationEntry(SampleCallbackHandler.class.getName(), LoginModuleControlFlag.REQUIRED, new HashMap<String, String>());

		private static final AppConfigurationEntry[] SIMPLE_CONF = new AppConfigurationEntry[] { OS_SPECIFIC_LOGIN, HADOOP_LOGIN };

		@Override
		public AppConfigurationEntry[] getAppConfigurationEntry(String appName) {
			if (SIMPLE_CONFIG_NAME.equals(appName)) {
				return SIMPLE_CONF;
			} 
			return null;
		}
	}*/
}
