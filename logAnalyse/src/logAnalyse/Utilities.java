package logAnalyse;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Utilities {

	public static String getYesterdayDate() {
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(cal.getTime());
	}

}
