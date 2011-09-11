package ngram4web.client;

import ngram4web.shared.NGramService;
import ngram4web.shared.NGramServiceAsync;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class MyEntryPoint implements EntryPoint {

	NGramServiceAsync service = GWT.create(NGramService.class);

	@Override
	public void onModuleLoad() {
		GWT.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {

			@Override
			public void onUncaughtException(Throwable e) {
				Window.alert("uncaught exception: " + e.getLocalizedMessage());
			}
		});
		RootPanel.get().add(new Label("gwt initialized"));

		Button b = new Button("say Hello");
		RootPanel.get().add(b);
		b.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				service.test("christoph", new AsyncCallback<String>() {

					@Override
					public void onSuccess(String result) {
						RootPanel.get().add(new Label("result: " + result));
					}

					@Override
					public void onFailure(Throwable caught) {
						throw new RuntimeException(caught);
					}
				});
			}
		});
	}

}
