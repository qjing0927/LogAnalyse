package logAnalyse;

import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Schedule implements Runnable {
	private final ScheduledExecutorService service;

	private final Runnable task;
	private int hour;
	private int min;
	private int sec;
	private boolean AM = true;

	public Schedule(final ScheduledExecutorService service, final Runnable task) {
		this.service = service;
		this.task = task;
		setScheduleTime();
	}

	public void reSchedule() {
		Calendar calendar = Calendar.getInstance();
		long now = calendar.getTimeInMillis();
		calendar.set(Calendar.HOUR, hour);
		calendar.set(Calendar.MINUTE, min);
		calendar.set(Calendar.SECOND, sec);
		if (AM)
			calendar.set(Calendar.AM_PM, Calendar.AM);
		else
			calendar.set(Calendar.AM_PM, Calendar.PM);
		calendar.set(Calendar.MILLISECOND, 0);

		if (calendar.getTimeInMillis() < now) {
			// scheduled in the past, go forward one day....
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		}

		// set ourselves up to run at a given time.
		service.schedule(this, calendar.getTimeInMillis() - now, TimeUnit.MILLISECONDS);
	}

	private void setScheduleTime() {
		String[] tmp = Constants.logAnalyseTime.split(":");
		this.hour = Integer.parseInt(tmp[0]);
		this.min = Integer.parseInt(tmp[1]);
		this.sec = Integer.parseInt(tmp[2]);
		if (this.hour > 12) {
			this.hour = this.hour - 12;
			AM = false;
		}
	}

	@Override
	public void run() {
		try {
			task.run();
		} finally {
			this.reSchedule();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		ScheduledExecutorService service = Executors.newScheduledThreadPool(1);

		Runnable task = new Runnable() {

			@Override
			public void run() {
				System.out.println("Log Analyse start ...");
				LogAnalyse la = new LogAnalyse();
				la.ReadLog();
			}

		};

		Schedule daily = new Schedule(service, task);
		daily.reSchedule();
	}
}
