package loganalyse;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Constants {

	static String logPath;
	static String logBasename;
	static String logErrorIndicator;
	static String logAnalyseTime;
	public static String emailAccount;
	public static String emailPassword;
	public static String emailRecipients;

	static {
		Properties prop = new Properties();

		try {
			prop.load(new FileReader("/home/ec2-user/logAnalyse/config.properties"));
			logPath = prop.getProperty("log.path");
			logBasename = prop.getProperty("log.basename");
			logErrorIndicator = prop.getProperty("log.error.indicator");
			logAnalyseTime = prop.getProperty("log.analyse.time");
			emailAccount = prop.getProperty("email.account");
			emailPassword = prop.getProperty("email.password");
			emailRecipients = prop.getProperty("email.recipients");

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
