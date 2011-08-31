package org.vaadin.gwtgraphics.testapp.client;

import org.vaadin.gwtgraphics.client.Line;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.user.client.ui.TextBox;

public class LineEditor extends VectorObjectEditor {

	private TextBox x1Coord;

	private TextBox y1Coord;

	private TextBox x2Coord;

	private TextBox y2Coord;

	private TextBox strokeColor;

	private TextBox strokeWidth;

	private TextBox strokeOpacity;

	private AnimatableEditor animatableEditor;

	public LineEditor(Line line, Metadata metadata, boolean newVo) {
		super(line, metadata, newVo);

		animatableEditor = new AnimatableEditor(metadata);
		animatableEditor.setTarget(line);
		animatableEditor.addProperties(new String[] { "x1", "y1", "x2", "y2",
				"strokeopacity", "strokewidth", "rotation" });
		addRow("Animation", animatableEditor);

		x1Coord = addTextBoxRow("X1", 3);
		y1Coord = addTextBoxRow("Y1", 3);
		x2Coord = addTextBoxRow("X2", 3);
		y2Coord = addTextBoxRow("Y2", 3);
		strokeColor = addTextBoxRow("Stroke color", 8);
		strokeWidth = addTextBoxRow("Stroke width", 8);
		strokeOpacity = addTextBoxRow("Stroke opacity", 8);

		if (vo != null) {
			x1Coord.setText("" + line.getX1());
			y1Coord.setText("" + line.getY1());
			x2Coord.setText("" + line.getX2());
			y2Coord.setText("" + line.getY2());
			strokeColor.setText(line.getStrokeColor());
			strokeWidth.setText("" + line.getStrokeWidth());
			strokeOpacity.setText("" + line.getStrokeOpacity());
		}
	}

	@Override
	public void onChange(ChangeEvent event) {
		Object sender = event.getSource();
		super.onChange(event);
		if (vo == null) {
			return;
		}

		Line line = (Line) vo;
		CodeView code = getCodeView();
		if (sender == x1Coord) {
			try {
				line.setX1(Integer.parseInt(x1Coord.getText()));
				code.addMethodCall(line, "setX1", line.getX1());
			} catch (NumberFormatException e) {
			}
			x1Coord.setText("" + line.getX1());
		} else if (sender == y1Coord) {
			try {
				line.setY1(Integer.parseInt(y1Coord.getText()));
				code.addMethodCall(line, "setY1", line.getY1());
			} catch (NumberFormatException e) {
			}
			y1Coord.setText("" + line.getY1());
		} else if (sender == x2Coord) {
			try {
				line.setX2(Integer.parseInt(x2Coord.getText()));
				code.addMethodCall(line, "setX2", line.getX2());
			} catch (NumberFormatException e) {
			}
			x2Coord.setText("" + line.getX2());
		} else if (sender == y2Coord) {
			try {
				line.setY2(Integer.parseInt(y2Coord.getText()));
				code.addMethodCall(line, "setY2", line.getY2());
			} catch (NumberFormatException e) {
			}
			y2Coord.setText("" + line.getY2());
		} else if (sender == strokeColor) {
			line.setStrokeColor(strokeColor.getText());
			code.addMethodCall(line, "setStrokeColor", line.getStrokeColor());
			strokeColor.setText(line.getStrokeColor());
		} else if (sender == strokeWidth) {
			try {
				line.setStrokeWidth(Integer.parseInt(strokeWidth.getText()));
				code.addMethodCall(line, "setStrokeWidth",
						line.getStrokeWidth());
			} catch (NumberFormatException e) {
			}
			strokeWidth.setText("" + line.getStrokeWidth());
		} else if (sender == strokeOpacity) {
			try {
				line.setStrokeOpacity(Double.parseDouble(strokeOpacity
						.getText()));
				code.addMethodCall(vo, "setStrokeOpacity",
						line.getStrokeOpacity());
			} catch (NumberFormatException e) {
			}
			strokeOpacity.setText("" + line.getStrokeOpacity());
		}
	}

}
