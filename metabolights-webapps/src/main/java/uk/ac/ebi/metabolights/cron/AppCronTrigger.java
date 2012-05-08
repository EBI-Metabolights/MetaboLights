package uk.ac.ebi.metabolights.cron;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Map;

/*
    http://www.mkyong.com/java/quartz-scheduler-example/
 */
public class AppCronTrigger
{
    public static void main( String[] args ) throws Exception
    {
        RunCronTask task = new RunCronTask();
    	
    	//specify your sceduler task details
    	JobDetail job = new JobDetail();
    	job.setName("runCronJob");
    	job.setJobClass(RunCronJob.class);
    	
    	Map dataMap = job.getJobDataMap();
    	dataMap.put("runCronTask", task);
    	
    	//configure the scheduler time
    	CronTrigger trigger = new CronTrigger();
    	trigger.setName("runCronJobTesting");
    	trigger.setCronExpression("0/10 * * * * ?");
    	
    	//schedule it
    	Scheduler scheduler = new StdSchedulerFactory().getScheduler();
    	scheduler.start();
    	scheduler.scheduleJob(job, trigger);

    }
}
