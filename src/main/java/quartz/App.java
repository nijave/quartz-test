package quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Sample Quartz scheduled job
 */
public class App {
    // Get a logging instance
    final static Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws SchedulerException {
        // Create a new job with the SampleJob class
        JobDetail job = JobBuilder.newJob(SampleJob.class).withIdentity("mySampleJob", "sampleGroup").build();

        // Get the current time
        Date now = new Date();
        // Log the current time
        logger.info("Current time is: " + now);

        // Get one minute in the future
        Date future = new Date(now.getTime() + 60 * 1000);
        // Log the scheduled times
        logger.info("Will schedule job for: " + future);

        // Trigger the job to run on the next round minute
        Trigger trigger = TriggerBuilder.newTrigger()
                // Create an identity for the trigger
                .withIdentity("mySampleTrigger", "sampleGroup")
                // Specify a start date
                .startAt(future)
                // Build the trigger
                .build();

        // Get a scheduler instance
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        // Start the scheduler
        scheduler.start();
        // Add the job and trigger to the scheduler
        scheduler.scheduleJob(job, trigger);
    }
}
