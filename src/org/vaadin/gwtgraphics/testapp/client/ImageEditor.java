package org.vaadin.gwtgraphics.testapp.client;

import org.vaadin.gwtgraphics.client.Image;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.user.client.ui.TextBox;

public class ImageEditor extends VectorObjectEditor {

	private TextBox xCoord;

	private TextBox yCoord;

	private TextBox width;

	private TextBox height;

	private TextBox href;

	private AnimatableEditor animatableEditor;

	public ImageEditor(Image vo, Metadata metadata, boolean newVo) {
		super(vo, metadata, newVo);

		animatableEditor = new AnimatableEditor(metadata);
		animatableEditor.setTarget(vo);
		animatableEditor.addProperties(new String[] { "x", "y", "width",
				"height", "rotation" });
		addRow("Animation", animatableEditor);

		xCoord = addTextBoxRow("X", 3);
		xCoord.getElement().setId("x-coord");
		yCoord = addTextBoxRow("Y", 3);
		yCoord.getElement().setId("y-coord");
		width = addTextBoxRow("Width", 8);
		width.getElement().setId("width");
		height = addTextBoxRow("Height", 8);
		height.getElement().setId("height");
		href = addTextBoxRow("Href", 8);
		href.getElement().setId("href");

		if (vo != null) {
			xCoord.setText("" + vo.getX());
			yCoord.setText("" + vo.getY());
			width.setText("" + vo.getWidth());
			height.setText("" + vo.getHeight());
			href.setText(vo.getHref());
		}
	}

	@Override
	public void onChange(ChangeEvent event) {
		Object sender = event.getSource();
		super.onChange(event);
		if (vo == null) {
			return;
		}

		Image image = (Image) vo;
		CodeView code = getCodeView();
		if (sender == xCoord) {
			try {
				image.setX(Integer.parseInt(xCoord.getText()));
				code.addMethodCall(vo, "setX", image.getX());
			} catch (NumberFormatException e) {
			}
			xCoord.setText("" + image.getX());
		} else if (sender == yCoord) {
			try {
				image.setY(Integer.parseInt(yCoord.getText()));
				code.addMethodCall(vo, "setY", image.getY());
			} catch (NumberFormatException e) {
			}
			yCoord.setText("" + image.getY());
		} else if (sender == width) {
			try {
				image.setWidth(Integer.parseInt(width.getText()));
				code.addMethodCall(vo, "setWidth", width.getText());
			} catch (NumberFormatException e) {
			}
			width.setText("" + image.getWidth());
		} else if (sender == height) {
			try {
				image.setHeight(Integer.parseInt(height.getText()));
				code.addMethodCall(vo, "setHeight", height.getText());
			} catch (NumberFormatException e) {
			}
			height.setText("" + image.getHeight());
		} else if (sender == href) {
			image.setHref(href.getText());
			height.setText(image.getHref());
			code.addMethodCall(vo, "setHref", height.getText());
		}
	}
}
