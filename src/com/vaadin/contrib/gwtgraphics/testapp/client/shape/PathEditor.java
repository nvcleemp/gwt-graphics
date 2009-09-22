package com.vaadin.contrib.gwtgraphics.testapp.client.shape;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.vaadin.contrib.gwtgraphics.client.Shape;
import com.vaadin.contrib.gwtgraphics.client.shape.Path;
import com.vaadin.contrib.gwtgraphics.testapp.client.CodeView;
import com.vaadin.contrib.gwtgraphics.testapp.client.Metadata;
import com.vaadin.contrib.gwtgraphics.testapp.client.ShapeEditor;

public class PathEditor extends ShapeEditor {

	ClosePathEditor close;

	MoveToEditor moveTo;

	LineToEditor lineTo;

	public PathEditor(Shape vo, Metadata metadata, boolean newVo) {
		super(vo, metadata, newVo);

		animatableEditor.setTarget(vo);

		moveTo = new MoveToEditor();
		addRow("Move", moveTo);

		lineTo = new LineToEditor();
		addRow("Line", lineTo);

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

}
