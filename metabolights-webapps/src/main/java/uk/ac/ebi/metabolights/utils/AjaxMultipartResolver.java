package uk.ac.ebi.metabolights.utils;

import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;

public class AjaxMultipartResolver extends CommonsMultipartResolver {

    private static final Logger logger = LoggerFactory.getLogger(AjaxMultipartResolver.class);


	private static ThreadLocal<UploadProgressListener> progressListener = new ThreadLocal<UploadProgressListener>();

    @Override
    public void cleanupMultipart(MultipartHttpServletRequest request) {
        super.cleanupMultipart(request);
        logger.debug("Cleanup multipart invoked");

        if (progressListener.get() != null){
            UploadProgressListener.getUploads().remove(progressListener.get());
        }
    }

    @Override
    protected FileUpload newFileUpload(FileItemFactory fileItemFactory) {
        logger.debug("New file upload invoked");

        FileUpload fileUpload = super.newFileUpload(fileItemFactory);
        fileUpload.setProgressListener(progressListener.get());
        return fileUpload;
    }
   
    @Override
    public MultipartHttpServletRequest resolveMultipart(HttpServletRequest request) throws MultipartException {

        logger.debug("resolvedMultipart invoked");

        progressListener.set(new UploadProgressListener(request));
        return super.resolveMultipart(request);
    } 
}