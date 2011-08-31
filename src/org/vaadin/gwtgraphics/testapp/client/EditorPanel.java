package org.vaadin.gwtgraphics.testapp.client;

import org.vaadin.gwtgraphics.client.Group;
import org.vaadin.gwtgraphics.client.Image;
import org.vaadin.gwtgraphics.client.Line;
import org.vaadin.gwtgraphics.client.VectorObject;
import org.vaadin.gwtgraphics.client.shape.Circle;
import org.vaadin.gwtgraphics.client.shape.Ellipse;
import org.vaadin.gwtgraphics.client.shape.Path;
import org.vaadin.gwtgraphics.client.shape.Rectangle;
import org.vaadin.gwtgraphics.client.shape.Text;
import org.vaadin.gwtgraphics.testapp.client.shape.CircleEditor;
import org.vaadin.gwtgraphics.testapp.client.shape.EllipseEditor;
import org.vaadin.gwtgraphics.testapp.client.shape.PathEditor;
import org.vaadin.gwtgraphics.testapp.client.shape.RectangleEditor;
import org.vaadin.gwtgraphics.testapp.client.shape.TextEditor;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class EditorPanel extends VerticalPanel implements ChangeHandler,
		ClickHandler {

	private Metadata metadata;

	private ListBox action;

	private ListBox type;

	private ListBox vectorObjectList;

	private Button add;

	private Button remove;

	private VectorObjectEditor voEditor;

	public EditorPanel(Metadata metadata) {
		this.metadata = metadata;
		action = new ListBox();
		action.getElement().setId("action-select");
		action.addItem("Add new");
		action.addItem("Edit selected");
		action.addChangeHandler(this);
		add(action);

		type = new ListBox();
		type.getElement().setId("type-select");
		type.addItem("-", "");
		type.addItem("Rectangle");
		type.addItem("Circle");
		type.addItem("Ellipse");
		type.addItem("Line");
		type.addItem("Image");
		type.addItem("Text");
		type.addItem("Path");
		type.addItem("Group");
		type.addChangeHandler(this);
		add(type);

		vectorObjectList = new ListBox();
		vectorObjectList.getElement().setId("vo-list");
		metadata.updateVectorObjectListBox(vectorObjectList, null);
		vectorObjectList.addChangeHandler(this);
		add(vectorObjectList);

		add = new Button("Add");
		add.getElement().setId("add-button");
		add.addClickHandler(this);

		remove = new Button("Remove");
		remove.getElement().setId("remove-button");
		remove.addClickHandler(this);
	}

	private void setVectorObject(VectorObject vo, boolean newVo) {
		if (this.voEditor != null) {
			remove(voEditor);
			if (voEditor.getVectorObject().getParent() == null) {
				metadata.removeVectorObject(voEditor.getVectorObject());
			}
		}
		remove(add);
		remove(remove);

		metadata.updateVectorObjectListBox(vectorObjectList, vo);
		metadata.getCodeView().refresh(vo);

		if (vo == null) {
			type.setSelectedIndex(0); // -
			type.setEnabled(true);
			action.setSelectedIndex(0); // add new
			return;
		}
		type.setEnabled(false);
		action.setSelectedIndex(1); // edit selected

		this.voEditor = null;
		if (vo instanceof Rectangle) {
			type.setSelectedIndex(1);
			RectangleEditor editor = new RectangleEditor((Rectangle) vo,
					metadata, newVo);
			this.voEditor = editor;
		} else if (vo instanceof Circle) {
			type.setSelectedIndex(2);
			CircleEditor editor = new CircleEditor((Circle) vo, metadata, newVo);
			this.voEditor = editor;
		} else if (vo instanceof Ellipse) {
			type.setSelectedIndex(3);
			EllipseEditor editor = new EllipseEditor((Ellipse) vo, metadata,
					newVo);
			this.voEditor = editor;
		} else if (vo instanceof Line) {
			type.setSelectedIndex(4);
			LineEditor editor = new LineEditor((Line) vo, metadata, newVo);
			this.voEditor = editor;
		} else if (vo instanceof Image) {
			type.setSelectedIndex(5);
			ImageEditor editor = new ImageEditor((Image) vo, metadata, newVo);
			this.voEditor = editor;
		} else if (vo instanceof Text) {
			type.setSelectedIndex(6);
			voEditor = new TextEditor((Text) vo, metadata, newVo);
		} else if (vo instanceof Path) {
			type.setSelectedIndex(7);
			voEditor = new PathEditor((Path) vo, metadata, newVo);
		} else if (vo instanceof Group) {
			type.setSelectedIndex(8);
			voEditor = new GroupEditor((Group) vo, metadata, newVo);
		}

		if (voEditor != null) {
			add(voEditor);
			if (newVo) {
				add(add);
			} else {
				add(remove);
			}
		}
	}

	public void onChange(ChangeEvent event) {
		Object sender = event.getSource();
		if (sender == action) {
			if (action.getSelectedIndex() == 0) {
				// add new
				setVectorObject(null, true);
			}
		} else if (sender == type) {
			VectorObject vo = null;
			switch (type.getSelectedIndex()) {
			case 0:
				// -
				setVectorObject(null, true);
				break;
			case 1:
				vo = new Rectangle(10, 10, 100, 100);
				metadata.addVectorObject(vo, "rect", "10, 10, 100, 100");
				break;
			case 2:
				vo = new Circle(110, 110, 100);
				metadata.addVectorObject(vo, "circle", "110, 110, 100");
				break;
			case 3:
				vo = new Ellipse(110, 110, 100, 100);
				metadata.addVectorObject(vo, "ellipse", "110, 110, 100, 100");
				break;
			case 4:
				vo = new Line(10, 10, 110, 110);
				metadata.addVectorObject(vo, "line", "10, 10, 110, 110");
				break;
			case 5:
				vo = new Image(10, 10, 110, 110, "");
				metadata.addVectorObject(vo, "image", "10, 10, 110, 110, \"\"");
				break;
			case 6:
				vo = new Text(10, 20, "Hello world!");
				metadata.addVectorObject(vo, "text", "10, 20, \"Hello world!\"");
				break;
			case 7:
				vo = new Path(10, 10);
				metadata.addVectorObject(vo, "path", "10, 10");
				break;
			case 8:
				vo = new Group();
				metadata.addVectorObject(vo, "group", "");
				break;
			default:
				break;
			}

			if (vo != null) {
				vo.addClickHandler(this);
				setVectorObject(vo, true);
			}
		} else if (sender == vectorObjectList) {
			setVectorObject(metadata.getVectorObject(vectorObjectList
					.getSelectedIndex()), false);
		}
	}

	public void onClick(ClickEvent event) {
		Object sender = event.getSource();
		if (sender == add) {
			VectorObject vo = voEditor.getVectorObject();
			voEditor.getVectorObjectContainer().add(vo);
			setVectorObject(vo, false);
		} else if (sender == remove) {
			metadata.removeVectorObject(voEditor.getVectorObject());
			setVectorObject(null, true);
		} else if (sender instanceof VectorObject) {
			setVectorObject((VectorObject) sender, false);
		} else {
			setVectorObject(null, true);
		}
	}
}
