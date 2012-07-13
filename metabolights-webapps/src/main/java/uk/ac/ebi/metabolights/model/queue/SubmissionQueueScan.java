package uk.ac.ebi.metabolights.model.queue;

import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.metabolights.service.AppContext;

import java.text.ParseException;
import java.util.List;
import java.util.TimerTask;

@Transactional
public class SubmissionQueueScan extends TimerTask{

	@Override
	public void run() {
		
		// Check if there is any item in the queue...
		try {
			
			// Load the queue
			List<SubmissionItem> queue = SubmissionQueue.getQueue();
			
			
			// If there is any Submission...
			if (!queue.isEmpty()){
				
				// Get the first Item (Should be sorted by submission time)
				SubmissionItem si = queue.get(0);

				// Process it
				SubmissionQueueProcessor sp = new SubmissionQueueProcessor(si);
				
				// Check first if the process can start, otherwise we will get an exception if can't start
				if (sp.CanProcessStart()){
					try {
						
						sp.start();
						
						
						
					} catch (Exception e) {
	
						// Cannot submit
						AppContext.getEmailService().sendSimpleEmail( "ERROR in the submission process", "There was an error in " + this.getClass() + "\nMethod: run()\n\nERROR:\n" + e.getMessage());
						
					}
				}
			}
			
			
		} catch (ParseException e) {
			
			// Cannot load the queue
			AppContext.getEmailService().sendSimpleEmail("ERROR while retrieving queued items", "There was an error in " + this.getClass() + "\nMethod: run()\n\nERROR:\n" + e.getMessage());
			
		} 
		
		
		
	}

}
