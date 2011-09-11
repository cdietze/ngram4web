package ngram4web.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface NGramServiceAsync {

	void test(String text, AsyncCallback<String> callback);

}
