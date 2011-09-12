package ngram4web.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ngram4web.shared.NGramService;
import ngram4web.shared.NGramServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

public class MainPanel extends Composite implements HasText {

	private static NGramServiceAsync service = GWT.create(NGramService.class);

	private static MainPanelUiBinder uiBinder = GWT
			.create(MainPanelUiBinder.class);

	interface MainPanelUiBinder extends UiBinder<Widget, MainPanel> {
	}

	@UiField
	Button button;
	@UiField
	TextArea samplesBox;
	@UiField
	TextArea outputBox;

	public MainPanel() {
		initWidget(uiBinder.createAndBindUi(this));

		samplesBox.setText(Constants.samples);
	}

	@UiHandler("button")
	void onClick(ClickEvent e) {
		String[] samples = toArray(samplesBox.getText());
		service.generateWords(samples, new AsyncCallback<String[]>() {
			@Override
			public void onSuccess(String[] result) {
				outputBox.setText(mkString(result));
			}

			@Override
			public void onFailure(Throwable caught) {
				throw new RuntimeException(caught);
			}
		});
	}

	private String mkString(String[] arr) {
		if (arr.length == 0)
			return "";
		StringBuilder sb = new StringBuilder(arr[0]);
		for (int i = 1; i < arr.length; ++i) {
			sb.append(", ");
			sb.append(arr[i]);
		}
		return sb.toString();
	}

	private String[] toArray(String input) {
		List<String> tmpList = Arrays.asList(input.split(","));
		List<String> resultList = new ArrayList<String>();
		for (String s : tmpList) {
			String s2 = s.trim();
			if (!s2.isEmpty()) {
				resultList.add(s2);
			}
		}
		return resultList.toArray(new String[] {});
	}

	public void setText(String text) {
		button.setText(text);
	}

	public String getText() {
		return button.getText();
	}

}
