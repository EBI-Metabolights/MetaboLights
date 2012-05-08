package uk.ac.ebi.metabolights.cron;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Map;

public class RunCronJob implements Job
{
	public void execute(JobExecutionContext context)
	throws JobExecutionException {
		
		Map dataMap = context.getJobDetail().getJobDataMap();
        RunCronTask task = (RunCronTask) dataMap.get("runCronTask");
		task.printMe();
	}
	
}
