package uk.ac.ebi.metabolights.utils;

import org.apache.commons.fileupload.ProgressListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UploadProgressListener implements ProgressListener {

	private double percentDone;
	private Date lastUpdate;
	private static List<ProgressListener> uploads = new ArrayList<>();

	private static final Logger logger = LoggerFactory.getLogger(UploadProgressListener.class);


	public static List<ProgressListener> getUploads() {
		return uploads;
	}

	public UploadProgressListener(HttpServletRequest request) {
		final HttpSession session = request.getSession();
		logger.info("Setting progress listener attribute on session: "+session.getId());
		session.setAttribute("progress", this);
		uploads.add(this);
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.commons.fileupload.ProgressListener#update(long, long, int)
	 */
	@Override
	public void update(long bytesRead, long contentLength, int pItems) {
		percentDone = (100 * bytesRead) / contentLength;
		lastUpdate = new Date();

		if (percentDone == 100) {
			uploads.remove(this);
		}
	}

	/**
	 * Get the percent done
	 * @return the percent done
	 */
	public double getPercentDone() {
		return percentDone;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}
}