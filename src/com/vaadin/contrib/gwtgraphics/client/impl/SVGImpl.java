package com.vaadin.contrib.gwtgraphics.client.impl;

import java.util.List;

import com.google.gwt.dom.client.Element;
import com.vaadin.contrib.gwtgraphics.client.Group;
import com.vaadin.contrib.gwtgraphics.client.Image;
import com.vaadin.contrib.gwtgraphics.client.Line;
import com.vaadin.contrib.gwtgraphics.client.VectorObject;
import com.vaadin.contrib.gwtgraphics.client.impl.util.NumberUtil;
import com.vaadin.contrib.gwtgraphics.client.impl.util.SVGBBox;
import com.vaadin.contrib.gwtgraphics.client.impl.util.SVGUtil;
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
 * This class contains the SVG implementation module of GWT Graphics.
 * 
 * @author Henri Kerola / IT Mill Ltd
 * 
 */
public class SVGImpl {

	public String getRendererString() {
		return "SVG";
	}

	public String getStyleSuffix() {
		return "svg";
	}

	public Element createDrawingArea(Element container, int width, int height) {
		Element root = SVGUtil.createSVGElementNS("svg");
		container.appendChild(root);
		setWidth(root, width);
		setHeight(root, height);

		Element defs = SVGUtil.createSVGElementNS("defs");
		root.appendChild(defs);

		return root;
	}

	public Element createElement(Class<? extends VectorObject> type) {
		Element element = null;
		if (type == Rectangle.class) {
			element = SVGUtil.createSVGElementNS("rect");
		} else if (type == Circle.class) {
			element = SVGUtil.createSVGElementNS("circle");
		} else if (type == Ellipse.class) {
			element = SVGUtil.createSVGElementNS("ellipse");
		} else if (type == Path.class) {
			element = SVGUtil.createSVGElementNS("path");
		} else if (type == Text.class) {
			element = SVGUtil.createSVGElementNS("text");
		} else if (type == Image.class) {
			element = SVGUtil.createSVGElementNS("image");
			// Let aspect ration behave like VML's image does
			element.setAttribute("preserveAspectRatio", "none");
		} else if (type == Line.class) {
			element = SVGUtil.createSVGElementNS("line");
		} else if (type == Group.class) {
			element = SVGUtil.createSVGElementNS("g");
		}

		return element;
	}

	public int getX(Element element) {
		return NumberUtil.parseIntValue(element,
				getPosAttribute(element, true), 0);
	}

	public void setX(Element element, int x) {
		SVGUtil.setAttributeNS(element, getPosAttribute(element, true), x);
	}

	public int getY(Element element) {
		return NumberUtil.parseIntValue(element,
				getPosAttribute(element, false), 0);
	}

	public void setY(Element element, int y) {
		SVGUtil.setAttributeNS(element, getPosAttribute(element, false), y);

	}

	private String getPosAttribute(Element element, boolean x) {
		String tagName = element.getTagName();
		String attr = "";
		if (tagName.equals("rect") || tagName.equals("text")
				|| tagName.equals("image")) {
			attr = x ? "x" : "y";
		} else if (tagName.equals("circle") || tagName.equals("ellipse")) {
			attr = x ? "cx" : "cy";
		} else if (tagName.equals("path")) {

		} else if (tagName.equals("line")) {
			attr = x ? "x1" : "y1";
		}
		return attr;
	}

	public String getFillColor(Element element) {
		String fill = element.getAttribute("fill");
		return fill.equals("none") ? null : fill;
	}

	public void setFillColor(Element element, String color) {
		if (color == null) {
			color = "none";
		}
		SVGUtil.setAttributeNS(element, "fill", color);
	}

	public double getFillOpacity(Element element) {
		return NumberUtil.parseDoubleValue(
				element.getAttribute("fill-opacity"), 1);
	}

	public void setFillOpacity(Element element, double opacity) {
		SVGUtil.setAttributeNS(element, "fill-opacity", "" + opacity);
	}

	public String getStrokeColor(Element element) {
		String stroke = element.getAttribute("stroke");
		return stroke.equals("none") ? null : stroke;
	}

	public void setStrokeColor(Element element, String color) {
		SVGUtil.setAttributeNS(element, "stroke", color);
	}

	public int getStrokeWidth(Element element) {
		return NumberUtil.parseIntValue(element, "stroke-width", 0);
	}

	public void setStrokeWidth(Element element, int width) {
		SVGUtil.setAttributeNS(element, "stroke-width", width);
	}

	public double getStrokeOpacity(Element element) {
		return NumberUtil.parseDoubleValue(element
				.getAttribute("stroke-opacity"), 1);
	}

	public void setStrokeOpacity(Element element, double opacity) {
		SVGUtil.setAttributeNS(element, "stroke-opacity", "" + opacity);
	}

	public int getWidth(Element element) {
		return NumberUtil.parseIntValue(element, "width", 0);
	}

	public void setWidth(Element element, int width) {
		SVGUtil.setAttributeNS(element, "width", width);
		if (element.getTagName().equalsIgnoreCase("svg")) {
			element.getParentElement().getStyle().setPropertyPx("width", width);
		}
	}

	public int getHeight(Element element) {
		return NumberUtil.parseIntValue(element, "height", 0);
	}

	public void setHeight(Element element, int height) {
		SVGUtil.setAttributeNS(element, "height", height);
		if (element.getTagName().equalsIgnoreCase("svg")) {
			element.getParentElement().getStyle().setPropertyPx("height",
					height);
		}
	}

	public int getCircleRadius(Element element) {
		return NumberUtil.parseIntValue(element, "r", 0);
	}

	public void setCircleRadius(Element element, int radius) {
		SVGUtil.setAttributeNS(element, "r", radius);
	}

	public int getEllipseRadiusX(Element element) {
		return NumberUtil.parseIntValue(element, "rx", 0);
	}

	public void setEllipseRadiusX(Element element, int radiusX) {
		SVGUtil.setAttributeNS(element, "rx", radiusX);
	}

	public int getEllipseRadiusY(Element element) {
		return NumberUtil.parseIntValue(element, "ry", 0);
	}

	public void setEllipseRadiusY(Element element, int radiusY) {
		SVGUtil.setAttributeNS(element, "ry", radiusY);
	}

	public void drawPath(Element element, List<PathStep> steps) {
		StringBuilder path = new StringBuilder();
		for (PathStep step : steps) {
			if (step.getClass() == ClosePath.class) {
				path.append(" z");
			} else if (step.getClass() == MoveTo.class) {
				MoveTo moveTo = (MoveTo) step;
				path.append(moveTo.isRelativeCoords() ? " m" : " M").append(
						moveTo.getX()).append(" ").append(moveTo.getY());
			} else if (step.getClass() == LineTo.class) {
				LineTo lineTo = (LineTo) step;
				path.append(lineTo.isRelativeCoords() ? " l" : " L").append(
						lineTo.getX()).append(" ").append(lineTo.getY());
			}
		}

		SVGUtil.setAttributeNS(element, "d", path.toString());
	}

	public String getText(Element element) {
		return element.getInnerText();
	}

	public void setText(Element element, String text) {
		element.setInnerText(text);
	}

	public String getTextFontFamily(Element element) {
		return element.getAttribute("font-family");
	}

	public void setTextFontFamily(Element element, String family) {
		SVGUtil.setAttributeNS(element, "font-family", family);
	}

	public int getTextFontSize(Element element) {
		return NumberUtil.parseIntValue(element, "font-size", 0);
	}

	public void setTextFontSize(Element element, int size) {
		SVGUtil.setAttributeNS(element, "font-size", size);
	}

	public String getImageHref(Element element) {
		return element.getAttribute("href");
	}

	public void setImageHref(Element element, String src) {
		SVGUtil.setAttributeNS(SVGUtil.XLINK_NS, element, "href", src);
	}

	public int getRectangleRoundedCorners(Element element) {
		return NumberUtil.parseIntValue(element, "rx", 0);
	}

	public void setRectangleRoundedCorners(Element element, int radius) {
		SVGUtil.setAttributeNS(element, "rx", radius);
		SVGUtil.setAttributeNS(element, "ry", radius);
	}

	public int getLineX2(Element element) {
		return NumberUtil.parseIntValue(element, "x2", 0);
	}

	public void setLineX2(Element element, int x2) {
		SVGUtil.setAttributeNS(element, "x2", x2);

	}

	public int getLineY2(Element element) {
		return NumberUtil.parseIntValue(element, "y2", 0);
	}

	public void setLineY2(Element element, int y2) {
		SVGUtil.setAttributeNS(element, "y2", y2);

	}

	public void add(Element root, Element element) {
		root.appendChild(element);
	}

	public void remove(Element root, Element element) {
		root.removeChild(element);
	}

	public void pop(Element root, Element element) {
		root.appendChild(element);
	}

	public void clear(Element root) {
		while (root.hasChildNodes()) {
			root.removeChild(root.getLastChild());
		}
	}

	public void setStyleName(Element element, String name) {
		SVGUtil.setClassName(element, name + "-" + getStyleSuffix());
	}

	public void setRotation(Element element, int degree) {
		element.setPropertyInt("_rotation", degree);
		if (degree == 0) {
			SVGUtil.setAttributeNS(element, "transform", "");
			return;
		}
		SVGBBox box = SVGUtil.getBBBox(element);
		int x = box.getX() + box.getWidth() / 2;
		int y = box.getY() + box.getHeight() / 2;
		SVGUtil.setAttributeNS(element, "transform", "rotate(" + degree + " "
				+ x + " " + y + ")");
	}

	public int getRotation(Element element) {
		return element.getPropertyInt("_rotation");
	}
}
