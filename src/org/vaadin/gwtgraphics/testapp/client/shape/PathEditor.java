package org.vaadin.gwtgraphics.testapp.client.shape;

import org.vaadin.gwtgraphics.client.Shape;
import org.vaadin.gwtgraphics.client.shape.Path;
import org.vaadin.gwtgraphics.testapp.client.CodeView;
import org.vaadin.gwtgraphics.testapp.client.Metadata;
import org.vaadin.gwtgraphics.testapp.client.ShapeEditor;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;

public class PathEditor extends ShapeEditor {

	ClosePathEditor close;

	MoveToEditor moveTo;

	LineToEditor lineTo;

	CurveToEditor curveTo;

	public PathEditor(Shape vo, Metadata metadata, boolean newVo) {
		super(vo, metadata, newVo);

		animatableEditor.setTarget(vo);

		moveTo = new MoveToEditor();
		addRow("Move", moveTo);

		lineTo = new LineToEditor();
		addRow("Line", lineTo);

		curveTo = new CurveToEditor();
		addRow("Curve", curveTo);

		close = new ClosePathEditor();
		addRow("Close", close);
	}

	abstract class PathStepEditor extends HorizontalPanel {

	}

	class ClosePathEditor extends PathStepEditor implements ClickHandler {

		protected Button closeButton;

		public ClosePathEditor() {
			closeButton = new Button("Close path", this);
			add(closeButton);
		}

		public void onClick(ClickEvent event) {
			if (event.getSource() == closeButton) {
				((Path) vo).close();
				getCodeView().addMethodCall(vo, "close");
			}
		}
	}

	class MoveToEditor extends ClosePathEditor {

		private TextBox xCoord;

		private TextBox yCoord;

		private Button addButton;

		protected CheckBox relCoords;

		public MoveToEditor() {
			remove(closeButton);

			xCoord = new TextBox();
			xCoord.setVisibleLength(4);
			xCoord.setTitle("x-coordinate");
			add(xCoord);

			yCoord = new TextBox();
			yCoord.setVisibleLength(4);
			xCoord.setTitle("y-coordinate");
			add(yCoord);

			relCoords = new CheckBox("rel");
			add(relCoords);

			addButton = new Button("Add", this);
			add(addButton);
		}

		@Override
		public void onClick(ClickEvent event) {
			if (event.getSource() == addButton) {
				Integer x = null;
				Integer y = null;
				try {
					x = Integer.parseInt(xCoord.getText());
					y = Integer.parseInt(yCoord.getText());
				} catch (NumberFormatException e) {
				}
				if (x != null && y != null) {
					xCoord.setText("" + x);
					yCoord.setText("" + y);
					addStep(x, y);
				}
			}
		}

		protected void addStep(int x, int y) {
			CodeView code = getCodeView();
			if (relCoords.getValue()) {
				((Path) vo).moveRelativelyTo(x, y);
				code.addMethodCall(vo, "moveRelativelyTo", x + ", " + y, false);
			} else {
				((Path) vo).moveTo(x, y);
				code.addMethodCall(vo, "moveTo", x + ", " + y, false);
			}
		}

	}

	class LineToEditor extends MoveToEditor {
		@Override
		protected void addStep(int x, int y) {
			CodeView code = getCodeView();
			if (relCoords.getValue()) {
				((Path) vo).lineRelativelyTo(x, y);
				code.addMethodCall(vo, "lineRelativelyTo", x + ", " + y, false);
			} else {
				((Path) vo).lineTo(x, y);
				code.addMethodCall(vo, "lineTo", x + ", " + y, false);
			}
		}
	}

	class CurveToEditor extends LineToEditor {

		private TextBox x1Coord;

		private TextBox y1Coord;

		private TextBox x2Coord;

		private TextBox y2Coord;

		public CurveToEditor() {
			x1Coord = new TextBox();
			x1Coord.setVisibleLength(4);
			x1Coord.setTitle("x1-coordinate");
			insert(x1Coord, 2);

			y1Coord = new TextBox();
			y1Coord.setVisibleLength(4);
			y1Coord.setTitle("y1-coordinate");
			insert(y1Coord, 3);

			x2Coord = new TextBox();
			x2Coord.setVisibleLength(4);
			x2Coord.setTitle("x2-coordinate");
			insert(x2Coord, 4);

			y2Coord = new TextBox();
			y2Coord.setVisibleLength(4);
			y2Coord.setTitle("y2-coordinate");
			insert(y2Coord, 5);
		}

		@Override
		protected void addStep(int x, int y) {
			Integer x1 = null;
			Integer y1 = null;
			Integer x2 = null;
			Integer y2 = null;
			try {
				x1 = Integer.parseInt(x1Coord.getText());
				y1 = Integer.parseInt(y1Coord.getText());
				x2 = Integer.parseInt(x2Coord.getText());
				y2 = Integer.parseInt(y2Coord.getText());
			} catch (NumberFormatException e) {
			}
			if (x1 != null && y1 != null && x2 != null && y2 != null) {
				CodeView code = getCodeView();
				x1Coord.setText("" + x1);
				y1Coord.setText("" + y1);
				x2Coord.setText("" + x2);
				y2Coord.setText("" + y2);
				if (relCoords.getValue()) {
					((Path) vo).curveRelativelyTo(x1, y1, x2, y2, x, y);
					code.addMethodCall(vo, "curveRelativelyTo", x1 + ", " + y1
							+ ", " + x2 + ", " + y2 + ", " + x + ", " + y,
							false);
				} else {
					((Path) vo).curveTo(x1, y1, x2, y2, x, y);
					code.addMethodCall(vo, "curveTo", x1 + ", " + y1 + ", "
							+ x2 + ", " + y2 + ", " + x + ", " + y, false);
				}
			}
		}
	}

}
