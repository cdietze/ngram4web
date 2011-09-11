package ngram4web.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface NGramServiceAsync {

	void generateWords(String[] samples, AsyncCallback<String[]> callback);

}
