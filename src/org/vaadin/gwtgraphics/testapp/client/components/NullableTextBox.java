package org.vaadin.gwtgraphics.testapp.client.components;

import com.google.gwt.user.client.ui.TextBox;

public class NullableTextBox extends TextBox {

	private static final String NULL_STRING = "NULL";

	@Override
	public String getText() {
		String value = super.getText();
		if (value.equals(NULL_STRING)) {
			return null;
		}
		return value;
	}

	@Override
	public void setText(String text) {
		if (text == null) {
			super.setText(NULL_STRING);
		} else {
			super.setText(text);
		}
	}

}
