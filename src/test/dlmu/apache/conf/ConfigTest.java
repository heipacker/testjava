package test.dlmu.apache.conf;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.SubsetConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;

public class ConfigTest {
	/** * 配置文件名称 */
	static String DATAEXCHANGE_PROPERTIES_FILE_NAME = "dataexchange.properties";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PropertiesConfiguration pconf = null;
		try {
			pconf = new PropertiesConfiguration(DATAEXCHANGE_PROPERTIES_FILE_NAME);
		} catch (ConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//自动保存
		pconf.setAutoSave(true);
		//重新加载
		pconf.setReloadingStrategy(new FileChangedReloadingStrategy());
		
		Configuration subsetConf = pconf.subset("dataexchange");
		System.out.println(subsetConf.getString("period"));
	}

}
