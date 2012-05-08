package uk.ac.ebi.metabolights.cron;

import org.apache.log4j.Logger;

public class RunCronTask
{

    private static Logger logger = Logger.getLogger(RunCronTask.class);

    public static Logger getLogger() {
        return logger;
    }

    public static void setLogger(Logger logger) {
        RunCronTask.logger = logger;
    }

	public void printMe() {
		System.out.println("RunCronTask test ~");
	}


}
