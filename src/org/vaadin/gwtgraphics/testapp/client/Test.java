package org.vaadin.gwtgraphics.testapp.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

public class Test implements EntryPoint {

	public void onModuleLoad() {
		InteractiveTestApplication test = new InteractiveTestApplication();
		RootPanel.get().add(test);
	}
}
