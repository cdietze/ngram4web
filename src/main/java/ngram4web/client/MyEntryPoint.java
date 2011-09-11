package ngram4web.client;

import java.util.Arrays;
import java.util.List;

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

	private static String[] samples = { "Anna", "Frieda", "Frida", "Martha",
			"Marta", "Erna", "Emma", "Marie", "Gertrud", "Margarethe",
			"Margarete", "Maria", "Elisabeth", "Berta", "Bertha", "Elsa",
			"Helene", "Luise", "Louise", "Johanna", "Hedwig", "Klara", "Clara",
			"Minna", "Else", "Paula", "Ella", "Ida", "Auguste", "Olga",
			"Wilhelmine", "Dora", "Alma", "Käthe", "Käte", "Herta", "Hertha",
			"Elise", "Margaretha", "Margareta", "Meta", "Metha", "Charlotte",
			"Katharina", "Catharina", "Katarina", "Agnes", "Emilie",
			"Dorothea", "Anni", "Anny", "Emmi", "Emmy", "Alice", "Elli",
			"Elly", "Gretchen", "Matilde", "Mathilde", "Caroline", "Karoline",
			"Henriette", "Henny", "Henni", "Lina", "Elfriede", "Sophie",
			"Sofie", "Alwine", "Grete", "Grethe", "Amanda", "Rosa",
			"Franziska", "Hermine", "Christine", "Magdalene", "Lucy", "Lucie",
			"Antonie", "Johanne", "Hildegard", "Lilly", "Lilli", "Lily",
			"Irma", "Adele", "Anita", "Anne", "Pauline", "Magdalena",
			"Marianne", "Friederike" };

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
				service.generateWords(samples, new AsyncCallback<String[]>() {

					@Override
					public void onSuccess(String[] result) {
						List<String> list = Arrays.asList(result);
						RootPanel.get().add(new Label("result: " + list));
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
