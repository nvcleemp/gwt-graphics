package org.vaadin.gwtgraphics.testapp.client.shape;

import org.vaadin.gwtgraphics.client.shape.Circle;
import org.vaadin.gwtgraphics.testapp.client.Metadata;
import org.vaadin.gwtgraphics.testapp.client.ShapeEditor;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.user.client.ui.TextBox;

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
