package com.vaadin.contrib.gwtgraphics.client.impl.util;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;

/**
 * This class contains helpers used by the VMLImpl class.
 * 
 * @author Henri Kerola / IT Mill Ltd
 * 
 */
public abstract class VMLUtil {

	public static final String VML_NS_PREFIX = "vml";

	public static final String VML_ELEMENT_CLASSNAME = "vml-element";

	public static Element createVMLElement(String name) {
		Element element = Document.get().createElement(
				VML_NS_PREFIX + ":" + name);
		element.setClassName(VML_ELEMENT_CLASSNAME);
		return element;
	}

	public static Element getOrCreateChildElementWithTagName(Element element,
			String name) {
		NodeList<Element> els = element.getElementsByTagName(name);
		if (els.getLength() > 0) {
			return els.getItem(0);
		}
		return element.appendChild(createVMLElement(name));
	}

	public static String getPropertyOfFirstChildElementWithTagName(
			Element element, String name, String attr) {
		NodeList<Element> els = element.getElementsByTagName(name);
		if (els.getLength() > 0) {
			return els.getItem(0).getPropertyString(attr);
		}
		return "";
	}
}
