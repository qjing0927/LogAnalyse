# LogAnalyse
Extract yesterday error type logs from project logs and send them in email. Schedule it run everyday.

This project entrance is Schedule.java

Customizing constants are in config.properties, including log path, log basename, log error indicator(ERROR in this sample", schedule time, email sender information and recipients.

This project will exact log files based on logfile pattern "basename.yyyy-mm-dd.^[0-9].log" and "basename.log".
