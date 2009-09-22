package com.vaadin.contrib.gwtgraphics.client.impl;

import java.util.List;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.vaadin.contrib.gwtgraphics.client.Group;
import com.vaadin.contrib.gwtgraphics.client.Image;
import com.vaadin.contrib.gwtgraphics.client.Line;
import com.vaadin.contrib.gwtgraphics.client.VectorObject;
import com.vaadin.contrib.gwtgraphics.client.impl.util.NumberUtil;
import com.vaadin.contrib.gwtgraphics.client.impl.util.VMLUtil;
import com.vaadin.contrib.gwtgraphics.client.shape.Circle;
import com.vaadin.contrib.gwtgraphics.client.shape.Ellipse;
import com.vaadin.contrib.gwtgraphics.client.shape.Path;
import com.vaadin.contrib.gwtgraphics.client.shape.Rectangle;
import com.vaadin.contrib.gwtgraphics.client.shape.Text;
import com.vaadin.contrib.gwtgraphics.client.shape.path.ClosePath;
import com.vaadin.contrib.gwtgraphics.client.shape.path.LineTo;
import com.vaadin.contrib.gwtgraphics.client.shape.path.MoveTo;
import com.vaadin.contrib.gwtgraphics.client.shape.path.PathStep;

/**
 * This class contains the VML implementation module of GWT Graphics.
 * 
 * @author Henri Kerola / IT Mill Ltd
 * 
 */
public class VMLImpl extends SVGImpl {

	@Override
	public String getRendererString() {
		return "VML";
	}

	@Override
	public String getStyleSuffix() {
		return "vml";
	}

	@Override
	public Element createDrawingArea(Element container, int width, int height) {
		addNamespaceAndStyle(VMLUtil.VML_NS_PREFIX,
				VMLUtil.VML_ELEMENT_CLASSNAME);

		container.getStyle().setProperty("position", "relative");
		container.getStyle().setProperty("overflow", "hidden");
		container.getStyle().setPropertyPx("width", width);
		container.getStyle().setPropertyPx("height", height);
		disableSelection(container);

		Element container2 = Document.get().createDivElement();
		container2.getStyle().setProperty("position", "absolute");
		container2.getStyle().setProperty("overflow", "hidden");
		container2.getStyle().setPropertyPx("width", width);
		container2.getStyle().setPropertyPx("height", height);
		container.appendChild(container2);

		Element root = VMLUtil.createVMLElement("group");
		setSize(root);
		container2.appendChild(root);
		return root;
	}

	private native void disableSelection(Element element) /*-{
		element.onselectstart = function() { return false };
	}-*/;

	private native void addNamespaceAndStyle(String ns, String classname) /*-{
		if (!$doc.namespaces[ns]) {
		$doc.namespaces.add(ns, "urn:schemas-microsoft-com:vml");
		// IE8's standards mode doesn't support * selector
		$doc.createStyleSheet().cssText = "." + classname + "{behavior:url(#default#VML); position: absolute; display:inline-block; }";
		}
	}-*/;

	@Override
	public Element createElement(Class<? extends VectorObject> type) {
		Element element = null;
		if (type == Rectangle.class) {
			element = VMLUtil.createVMLElement("roundrect");
			element.setAttribute("arcsize", "");
		} else if (type == Circle.class || type == Ellipse.class) {
			element = VMLUtil.createVMLElement("oval");
		} else if (type == Path.class) {
			element = VMLUtil.createVMLElement("shape");
			setSize(element);
		} else if (type == Text.class) {
			element = VMLUtil.createVMLElement("shape");
			setSize(element);

			Element path = VMLUtil.createVMLElement("path");
			path.setPropertyBoolean("textpathok", true);
			path.setPropertyString("v", "m 0,0 l 1,0");
			element.appendChild(path);

			Element textpath = VMLUtil.createVMLElement("textpath");
			textpath.setPropertyBoolean("on", true);
			textpath.getStyle().setProperty("v-text-align", "left");
			element.appendChild(textpath);
		} else if (type == Image.class) {
			element = VMLUtil.createVMLElement("image");
		} else if (type == Line.class) {
			element = VMLUtil.createVMLElement("line");
		} else if (type == Group.class) {
			element = VMLUtil.createVMLElement("group");
			setSize(element);
		}
		return element;
	}

	@Override
	public int getX(Element element) {
		return element.getPropertyInt("_x");
	}

	@Override
	public void setX(Element element, int x) {
		setXY(element, x, true);
	}

	@Override
	public int getY(Element element) {
		return element.getPropertyInt("_y");
	}

	@Override
	public void setY(Element element, int y) {
		setXY(element, y, false);
	}

	@Override
	public String getFillColor(Element element) {
		Element fill = VMLUtil.getOrCreateChildElementWithTagName(element,
				"fill");
		if (!fill.getPropertyBoolean("on")) {
			return null;
		}
		return fill.getPropertyString("color");
	}

	@Override
	public void setFillColor(Element element, String color) {
		Element fill = VMLUtil.getOrCreateChildElementWithTagName(element,
				"fill");
		if (color == null) {
			fill.setPropertyString("color", "black");
			fill.setPropertyBoolean("on", false);
		} else {
			fill.setPropertyString("color", color);
			fill.setPropertyBoolean("on", true);
		}
	}

	@Override
	public double getFillOpacity(Element element) {
		return NumberUtil.parseDoubleValue(VMLUtil
				.getPropertyOfFirstChildElementWithTagName(element, "fill",
						"opacity"), 1);
	}

	@Override
	public void setFillOpacity(Element element, double opacity) {
		VMLUtil.getOrCreateChildElementWithTagName(element, "fill")
				.setPropertyString("opacity", "" + opacity);
	}

	@Override
	public String getStrokeColor(Element element) {
		return VMLUtil.getPropertyOfFirstChildElementWithTagName(element,
				"stroke", "color");

	}

	@Override
	public void setStrokeColor(Element element, String color) {
		Element stroke = VMLUtil.getOrCreateChildElementWithTagName(element,
				"stroke");
		stroke.setPropertyString("color", color);
		stroke.setPropertyBoolean("on", color != null ? true : false);
	}

	@Override
	public int getStrokeWidth(Element element) {
		return element.getPropertyInt("_stroke-width");
	}

	@Override
	public void setStrokeWidth(Element element, int width) {
		Element stroke = VMLUtil.getOrCreateChildElementWithTagName(element,
				"stroke");
		stroke.setPropertyString("weight", width + "px");
		stroke.setPropertyBoolean("on", width > 0 ? true : false);
		// store value for getter
		element.setPropertyInt("_stroke-width", width);
	}

	@Override
	public double getStrokeOpacity(Element element) {
		return NumberUtil.parseDoubleValue(VMLUtil
				.getPropertyOfFirstChildElementWithTagName(element, "stroke",
						"opacity"), 1);
	}

	@Override
	public void setStrokeOpacity(Element element, double opacity) {
		VMLUtil.getOrCreateChildElementWithTagName(element, "stroke")
				.setPropertyString("opacity", "" + opacity);
	}

	@Override
	public int getWidth(Element element) {
		Element el = element;
		if (element.getTagName().equals("group")) {
			// DrawingArea's root element
			el = element.getParentElement();
		}
		return NumberUtil.parseIntValue(el.getStyle().getProperty("width"), 0);
	}

	@Override
	public void setWidth(Element element, int width) {
		Element el = element;
		if (element.getTagName().equals("group")) {
			// DrawingArea's root element
			el = element.getParentElement();
		}
		el.getStyle().setPropertyPx("width", width);
	}

	@Override
	public int getHeight(Element element) {
		Element el = element;
		if (element.getTagName().equals("group")) {
			// DrawingArea's root element
			el = element.getParentElement();
		}
		return NumberUtil.parseIntValue(el.getStyle().getProperty("height"), 0);
	}

	@Override
	public void setHeight(Element element, int height) {
		Element el = element;
		if (element.getTagName().equals("group")) {
			// DrawingArea's root element
			el = element.getParentElement();
		}
		el.getStyle().setPropertyPx("height", height);
	}

	@Override
	public int getCircleRadius(Element element) {
		return getEllipseRadiusX(element);
	}

	@Override
	public void setCircleRadius(Element element, int radius) {
		setEllipseRadiusX(element, radius);
		setEllipseRadiusY(element, radius);
	}

	@Override
	public int getEllipseRadiusX(Element element) {
		return getWidth(element) / 2;
	}

	@Override
	public void setEllipseRadiusX(Element element, int radiusX) {
		setWidth(element, 2 * radiusX);
		setX(element, getX(element));
	}

	@Override
	public int getEllipseRadiusY(Element element) {
		return getHeight(element) / 2;
	}

	@Override
	public void setEllipseRadiusY(Element element, int radiusY) {
		setHeight(element, 2 * radiusY);
		setY(element, getY(element));
	}

	@Override
	public void drawPath(Element element, List<PathStep> steps) {
		StringBuilder path = new StringBuilder();
		int x = -1;
		int y = -1;
		for (PathStep step : steps) {
			if (step.getClass() == ClosePath.class) {
				path.append(" x e");
			} else if (step.getClass() == MoveTo.class) {
				MoveTo moveTo = (MoveTo) step;
				path.append(moveTo.isRelativeCoords() ? " t" : " m").append(
						moveTo.getX()).append(" ").append(moveTo.getY());
			} else if (step.getClass() == LineTo.class) {
				LineTo lineTo = (LineTo) step;
				path.append(lineTo.isRelativeCoords() ? " r" : " l").append(
						lineTo.getX()).append(" ").append(lineTo.getY());
			}

			if (step instanceof MoveTo) {
				MoveTo moveTo = (MoveTo) step;
				x = moveTo.getX() + (moveTo.isRelativeCoords() ? x : 0);
				y = moveTo.getY() + (moveTo.isRelativeCoords() ? y : 0);
			} else {
				// TODO close
			}
		}
		element.setAttribute("path", path.toString());
	}

	private void setSize(Element element) {
		setSize(element, 1, 1);
	}

	private void setSize(Element element, int width, int height) {
		element.getStyle().setPropertyPx("width", width);
		element.getStyle().setPropertyPx("height", height);
		element.setPropertyString("coordorigin", "0 0");
		element.setPropertyString("coordsize", width + " " + height);
	}

	private void setXY(Element element, int xy, boolean x) {
		// Save value for getter
		element.setPropertyInt(x ? "_x" : "_y", xy);

		String tagName = element.getTagName();
		if (tagName.equals("line")) {
			if (x) {
				setLineFromTo(element, xy, null, true);
			} else {
				setLineFromTo(element, null, xy, true);
			}
		} else {
			if (tagName.equals("oval")) {
				xy = xy
						- NumberUtil.parseIntValue(element.getStyle()
								.getProperty(x ? "width" : "height"), 0) / 2;
			}
			element.getStyle().setPropertyPx(x ? "left" : "top", xy);
		}
	}

	private void setLineFromTo(Element element, Integer x, Integer y,
			boolean from) {
		StringBuilder value = new StringBuilder();
		String xAttr = from ? "_x1" : "_x2";
		String yAttr = from ? "_y1" : "_y2";
		if (x != null) {
			value.append(x);
			element.setPropertyInt(xAttr, x);
		} else if (element.getPropertyString(xAttr) != null) {
			value.append(element.getPropertyInt(xAttr));
		} else {
			// x-coordinate not specified
			return;
		}
		value.append(" ");
		if (y != null) {
			value.append(y);
			element.setPropertyInt(yAttr, y);
		} else if (element.getPropertyString(yAttr) != null) {
			value.append(element.getPropertyInt(yAttr));
		} else {
			// y-coordinate not specified
			return;
		}
		element.setPropertyString(from ? "from" : "to", value.toString());
	}

	@Override
	public String getText(Element element) {
		return VMLUtil.getPropertyOfFirstChildElementWithTagName(element,
				"textpath", "string");
	}

	@Override
	public void setText(Element element, String text) {
		VMLUtil.getOrCreateChildElementWithTagName(element, "textpath")
				.setPropertyString("string", text);
	}

	@Override
	public String getTextFontFamily(Element element) {
		return element.getPropertyString("_fontfamily");
	}

	@Override
	public void setTextFontFamily(Element element, String family) {
		element.setPropertyString("_fontfamily", family);
		setTextFont(element);
	}

	@Override
	public int getTextFontSize(Element element) {
		return element.getPropertyInt("_fontsize");
	}

	@Override
	public void setTextFontSize(Element element, int size) {
		element.setPropertyInt("_fontsize", size);
		setTextFont(element);
	}

	private void setTextFont(Element element) {
		VMLUtil.getOrCreateChildElementWithTagName(element, "textpath")
				.getStyle().setProperty(
						"font",
						element.getPropertyInt("_fontsize") + "px "
								+ element.getPropertyString("_fontfamily"));
	}

	@Override
	public String getImageHref(Element element) {
		return element.getPropertyString("src");
	}

	@Override
	public void setImageHref(Element element, String src) {
		element.setPropertyString("src", src);
	}

	@Override
	public int getRectangleRoundedCorners(Element element) {
		return element.getPropertyInt("_arcsize");
	}

	@Override
	public void setRectangleRoundedCorners(Element element, int radius) {
		String arcsize = "";
		if (radius > 0) {
			double l = Math.min(getWidth(element), getHeight(element));
			arcsize = "" + radius / l;
		}
		element.setAttribute("arcsize", arcsize);

		// Save int value for getter
		element.setPropertyInt("_arcsize", radius);
	}

	@Override
	public int getLineX2(Element element) {
		return element.getPropertyInt("_x2");
	}

	@Override
	public void setLineX2(Element element, int x2) {
		setLineFromTo(element, x2, null, false);
	}

	@Override
	public int getLineY2(Element element) {
		return element.getPropertyInt("_y2");
	}

	@Override
	public void setLineY2(Element element, int y2) {
		setLineFromTo(element, null, y2, false);

	}

	@Override
	public void add(Element root, Element element) {
		root.appendChild(element);
	}

	@Override
	public void remove(Element root, Element element) {
		root.removeChild(element);
	}

	@Override
	public void pop(Element root, Element element) {
		root.removeChild(element);
		root.appendChild(element);
	}

	@Override
	public void clear(Element root) {
		while (root.hasChildNodes()) {
			root.removeChild(root.getLastChild());
		}
	}

	@Override
	public void setStyleName(Element element, String name) {
		element.setClassName(VMLUtil.VML_ELEMENT_CLASSNAME + " " + name + "-"
				+ getStyleSuffix());
	}

	@Override
	public void setRotation(Element element, int degree) {
		element.getStyle().setPropertyPx("rotation", degree);
	}

	@Override
	public int getRotation(Element element) {
		return NumberUtil.parseIntValue(element.getStyle().getProperty(
				"rotation"), 0);
	}

}
