package loganalyse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.InputStreamReader;

import javax.inject.Inject;

import loganalyse.email.api.EmailService;

public class LogAnalyse {

	private EmailService service;

	@Inject
	public LogAnalyse(EmailService ser) {
		this.service = ser;
	}

	public void ReadLog() {

		File[] logFiles = getLogFiles();
		StringBuilder emailContent = new StringBuilder();
		boolean errorExist = false;

		if (logFiles.length > 0) {
			for (File logFile : logFiles) {
				try {
					emailContent.append("Errors in log file: " + logFile.getName());
					emailContent.append("\r\n");
					FileInputStream fstream = new FileInputStream(logFile);
					BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
					String strLine;
					/* read log line by line */
					while ((strLine = br.readLine()) != null) {
						/* parse strLine to obtain what you want */
						if (strLine.contains(Constants.logErrorIndicator)
								&& strLine.contains(Utilities.getYesterdayDate())) {
							// send email, append strLine to a new String
							emailContent.append(strLine);
							emailContent.append("\r\n");

							if (!errorExist) {
								errorExist = true;
							}
						}
					}
					fstream.close();
					emailContent.append("\r\n");
				} catch (Exception e) {
					System.err.println("Error: " + e.getMessage());
				}
			}
			if (errorExist) {
				service.sendEmail(emailContent.toString());
			}
		}

	}

	// log prefix basename.yesterdaydate.
	private File[] getLogFiles() {
		File dir = new File(Constants.logPath);
		return dir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String filename) {
				return filename.startsWith(Constants.logBasename + "." + Utilities.getYesterdayDate())
						|| filename.equals(Constants.logBasename + ".log");
			}
		});

	}

}
