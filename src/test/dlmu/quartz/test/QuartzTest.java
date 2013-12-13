package test.dlmu.quartz.test;

import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import test.dlmu.quartz.test.examples.example1.HelloJob;

public class QuartzTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			// Grab the Scheduler instance from the Factory
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			// and start it off
			scheduler.start();
			// define the job and tie it to our HelloJob class
			JobDetail job = newJob(HelloJob.class).withIdentity("job1", "group1").build();
			// Trigger the job to run now, and then repeat every 40 seconds
			Trigger trigger = newTrigger().withIdentity("trigger1", "group1").startNow()
					.withSchedule(simpleSchedule().withIntervalInSeconds(4).repeatForever()).build();
			// Tell quartz to schedule the job using our trigger
			scheduler.scheduleJob(job, trigger);
			try {
				Thread.sleep(1000 * 60);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			scheduler.shutdown();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

}
