package com.vaadin.contrib.gwtgraphics.client.impl;

import com.google.gwt.dom.client.Element;

/**
 * This class contains some hacks for the SVG implementation of the Safari
 * browser. It seems that Safari 4 works correctly without these hacks.
 * 
 * @author Henri Kerola / IT Mill Ltd
 * 
 */
public class SafariSVGImpl extends SVGImpl {

	@Override
	public void setX(Element element, int x) {
		super.setX(element, x);
		if (element.getTagName().equals("text")) {
			// When a text element is already added to the document's DOM tree,
			// for some reason element's position doesn't update without setting
			// some other attribute.
			setTextFontSize(element, getTextFontSize(element));
		}
	}

	@Override
	public void setY(Element element, int y) {
		super.setY(element, y);
		if (element.getTagName().equals("text")) {
			// When a text element is already added to the document's DOM tree,
			// for some reason element's position doesn't update without setting
			// some other attribute.
			setTextFontSize(element, getTextFontSize(element));
		}
	}

	@Override
	public void setRectangleRoundedCorners(Element element, int radius) {
		super.setRectangleRoundedCorners(element, radius);
		// Set some other attribute to make Safari round the corners
		// immediately.
		setX(element, getX(element));
	}

}
