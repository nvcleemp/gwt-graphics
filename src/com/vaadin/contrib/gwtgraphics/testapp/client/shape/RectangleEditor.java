package com.vaadin.contrib.gwtgraphics.testapp.client.shape;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.user.client.ui.TextBox;
import com.vaadin.contrib.gwtgraphics.client.shape.Rectangle;
import com.vaadin.contrib.gwtgraphics.testapp.client.CodeView;
import com.vaadin.contrib.gwtgraphics.testapp.client.Metadata;
import com.vaadin.contrib.gwtgraphics.testapp.client.ShapeEditor;

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
		height = addTextBoxRow("Height", 8);
		roundedCorners = addTextBoxRow("Rounded corners", 8);

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
				code.addMethodCall(vo, "setRoundedCorners", roundedCorners
						.getText());
			} catch (NumberFormatException e) {
			}
			roundedCorners.setText("" + rect.getRoundedCorners());
		}
	}
}