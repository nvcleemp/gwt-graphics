package org.vaadin.gwtgraphics.testapp.client;

import org.vaadin.gwtgraphics.client.Shape;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.user.client.ui.TextBox;

public class ShapeEditor extends VectorObjectEditor {

	private TextBox xCoord;

	private TextBox yCoord;

	private TextBox fillColor;

	private TextBox fillOpacity;

	private TextBox strokeColor;

	private TextBox strokeWidth;

	private TextBox strokeOpacity;

	protected AnimatableEditor animatableEditor;

	public ShapeEditor(Shape vo, Metadata metadata, boolean newVo) {
		super(vo, metadata, newVo);
		xCoord = addTextBoxRow("X", 3);
		yCoord = addTextBoxRow("Y", 3);
		fillColor = addTextBoxRow("Fill color", 8);
		fillOpacity = addTextBoxRow("Fill opacity", 8);
		strokeColor = addTextBoxRow("Stroke color", 8);
		strokeWidth = addTextBoxRow("Stroke width", 8);
		strokeOpacity = addTextBoxRow("Stroke opacity", 8);

		animatableEditor = new AnimatableEditor(metadata);
		animatableEditor.addProperties(new String[] { "x", "y", "fillopacity",
				"strokeopacity", "strokewidth", "rotation" });
		addRow("Animation", animatableEditor);

		if (vo != null) {
			xCoord.setText("" + vo.getX());
			yCoord.setText("" + vo.getY());
			fillColor.setText(vo.getFillColor());
			fillOpacity.setText("" + vo.getFillOpacity());
			strokeColor.setText(vo.getStrokeColor());
			strokeWidth.setText("" + vo.getStrokeWidth());
			strokeOpacity.setText("" + vo.getStrokeOpacity());
		}
	}

	@Override
	public void onChange(ChangeEvent event) {
		Object sender = event.getSource();
		super.onChange(event);
		if (vo == null) {
			return;
		}

		Shape shape = (Shape) vo;
		CodeView code = getCodeView();
		if (sender == xCoord) {
			try {
				shape.setX(Integer.parseInt(xCoord.getText()));
				metadata.getCodeView().addMethodCall(vo, "setX", shape.getX());
			} catch (NumberFormatException e) {
			}
			xCoord.setText("" + shape.getX());
		} else if (sender == yCoord) {
			try {
				shape.setY(Integer.parseInt(yCoord.getText()));
				metadata.getCodeView().addMethodCall(vo, "setY", shape.getY());
			} catch (NumberFormatException e) {
			}
			yCoord.setText("" + shape.getY());
		} else if (sender == fillColor) {
			shape.setFillColor(fillColor.getText());
			code.addMethodCall(vo, "setFillColor", shape.getFillColor());
			fillColor.setText(shape.getFillColor());
		} else if (sender == fillOpacity) {
			try {
				shape.setFillOpacity(Double.parseDouble(fillOpacity.getText()));
				code.addMethodCall(vo, "setFillOpacity", shape.getFillOpacity());
			} catch (NumberFormatException e) {
			}
			fillOpacity.setText("" + shape.getFillOpacity());
		} else if (sender == strokeColor) {
			shape.setStrokeColor(strokeColor.getText());
			code.addMethodCall(vo, "setStrokeColor", shape.getStrokeColor());
			strokeColor.setText(shape.getStrokeColor());
		} else if (sender == strokeWidth) {
			try {
				shape.setStrokeWidth(Integer.parseInt(strokeWidth.getText()));
				code.addMethodCall(vo, "setStrokeWidth", shape.getStrokeWidth());
			} catch (NumberFormatException e) {
			}
			strokeWidth.setText("" + shape.getStrokeWidth());
		} else if (sender == strokeOpacity) {
			try {
				shape.setStrokeOpacity(Double.parseDouble(strokeOpacity
						.getText()));
				code.addMethodCall(vo, "setStrokeOpacity",
						shape.getStrokeOpacity());
			} catch (NumberFormatException e) {
			}
			strokeOpacity.setText("" + shape.getStrokeOpacity());
		}
	}
}
