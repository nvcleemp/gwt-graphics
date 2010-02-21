package org.vaadin.gwtgraphics.testapp.client.shape;

import org.vaadin.gwtgraphics.client.shape.Text;
import org.vaadin.gwtgraphics.testapp.client.CodeView;
import org.vaadin.gwtgraphics.testapp.client.Metadata;
import org.vaadin.gwtgraphics.testapp.client.ShapeEditor;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.user.client.ui.TextBox;

public class TextEditor extends ShapeEditor {

	private TextBox textBox;

	private TextBox fontFamily;

	private TextBox fontSize;

	public TextEditor(Text vo, Metadata metadata, boolean newVo) {
		super(vo, metadata, newVo);

		animatableEditor.setTarget(vo);

		textBox = addTextBoxRow("Text", 25);
		fontFamily = addTextBoxRow("Font family", 25);
		fontSize = addTextBoxRow("Font size", 8);

		if (vo != null) {
			textBox.setText(vo.getText());
			fontFamily.setText(vo.getFontFamily());
			fontSize.setText("" + vo.getFontSize());
		}
	}

	@Override
	public void onChange(ChangeEvent event) {
		Object sender = event.getSource();
		super.onChange(event);
		if (vo == null) {
			return;
		}

		Text text = (Text) vo;
		CodeView code = getCodeView();
		if (sender == textBox) {
			text.setText(textBox.getText());
			code.addMethodCall(vo, "setText", text.getText());
			textBox.setText(text.getText());
		} else if (sender == fontFamily) {
			text.setFontFamily(fontFamily.getText());
			code.addMethodCall(vo, "setFontFamily", text.getFontFamily());
			fontFamily.setText(text.getFontFamily());
		} else if (sender == fontSize) {
			try {
				text.setFontSize(Integer.parseInt(fontSize.getText()));
				code.addMethodCall(vo, "setFontSize", text.getFontSize());
			} catch (NumberFormatException e) {
			}
			fontSize.setText("" + text.getFontSize());
		}
	}

}
