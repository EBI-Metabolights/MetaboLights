package uk.ac.ebi.metabolights.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

public interface GreetingServiceAsync {
    void greetServer(String name, AsyncCallback<String> async);
}
