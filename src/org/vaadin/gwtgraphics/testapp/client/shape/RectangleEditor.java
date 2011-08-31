package org.vaadin.gwtgraphics.testapp.client.shape;

import org.vaadin.gwtgraphics.client.shape.Rectangle;
import org.vaadin.gwtgraphics.testapp.client.CodeView;
import org.vaadin.gwtgraphics.testapp.client.Metadata;
import org.vaadin.gwtgraphics.testapp.client.ShapeEditor;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.user.client.ui.TextBox;

public class RectangleEditor extends ShapeEditor {

	private TextBox width;

	private TextBox height;

	private TextBox roundedCorners;

	public RectangleEditor(Rectangle vo, Metadata metadata, boolean newVo) {
		super(vo, metadata, newVo);

		animatableEditor.setTarget(vo);
		animatableEditor.addProperties(new String[] { "width", "height",
				"roundedcorners" });

		width = addTextBoxRow("Width", 8);
		width.getElement().setId("width");
		height = addTextBoxRow("Height", 8);
		height.getElement().setId("height");
		roundedCorners = addTextBoxRow("Rounded corners", 8);
		roundedCorners.getElement().setId("rounded-corners");

		if (vo != null) {
			width.setText("" + vo.getWidth());
			height.setText("" + vo.getHeight());
			roundedCorners.setText("" + vo.getRoundedCorners());
		}
	}

	@Override
	public void onChange(ChangeEvent event) {
		Object sender = event.getSource();
		super.onChange(event);
		if (vo == null) {
			return;
		}

		Rectangle rect = (Rectangle) vo;
		CodeView code = getCodeView();
		if (sender == width) {
			try {
				rect.setWidth(Integer.parseInt(width.getText()));
				code.addMethodCall(vo, "setWidth", width.getText());
			} catch (NumberFormatException e) {
			}
			width.setText("" + rect.getWidth());
		} else if (sender == height) {
			try {
				rect.setHeight(Integer.parseInt(height.getText()));
				code.addMethodCall(vo, "setHeight", height.getText());
			} catch (NumberFormatException e) {
			}
			height.setText("" + rect.getHeight());
		} else if (sender == roundedCorners) {
			try {
				rect.setRoundedCorners(Integer.parseInt(roundedCorners
						.getText()));
				code.addMethodCall(vo, "setRoundedCorners",
						roundedCorners.getText());
			} catch (NumberFormatException e) {
			}
			roundedCorners.setText("" + rect.getRoundedCorners());
		}
	}
}
