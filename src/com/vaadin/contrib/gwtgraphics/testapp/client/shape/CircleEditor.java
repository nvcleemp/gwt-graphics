package com.vaadin.contrib.gwtgraphics.testapp.client.shape;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.user.client.ui.TextBox;
import com.vaadin.contrib.gwtgraphics.client.shape.Circle;
import com.vaadin.contrib.gwtgraphics.testapp.client.Metadata;
import com.vaadin.contrib.gwtgraphics.testapp.client.ShapeEditor;

public class CircleEditor extends ShapeEditor {

	private TextBox radius;

	public CircleEditor(Circle c, Metadata metadata, boolean newVo) {
		super(c, metadata, newVo);

		animatableEditor.setTarget(c);
		animatableEditor.addProperties(new String[] { "radius" });

		radius = addTextBoxRow("Radius", 8);
		if (c != null) {
			radius.setText("" + c.getRadius());
		}
	}

	@Override
	public void onChange(ChangeEvent event) {
		Object sender = event.getSource();
		super.onChange(event);
		if (vo == null) {
			return;
		}

		Circle circle = (Circle) vo;
		if (sender == radius) {
			try {
				circle.setRadius(Integer.parseInt(radius.getText()));
				getCodeView().addMethodCall(circle, "setRadius",
						circle.getRadius());
			} catch (NumberFormatException e) {
			}
			radius.setText("" + circle.getRadius());
		}
	}
}
