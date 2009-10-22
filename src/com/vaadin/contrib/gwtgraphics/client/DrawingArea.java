package com.vaadin.contrib.gwtgraphics.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasAllMouseHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.dom.client.MouseWheelEvent;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.contrib.gwtgraphics.client.impl.SVGImpl;

/**
 * The following example shows how a DrawingArea instance is created and added
 * to a GWT application. A rectangle is added to this DrawingArea instance. When
 * the user clicks this rectangle its color changes.
 * 
 * <pre>
 * DrawingArea canvas = new DrawingArea(200, 200);
 * RootPanel.get().add(canvas);
 * 
 * Rectangle rect = new Rectangle(10, 10, 100, 50);
 * canvas.add(rect);
 * rect.setFillColor(&quot;blue&quot;);
 * rect.addClickHandler(new ClickHandler() {
 * 	public void onClick(ClickEvent event) {
 * 		Rectangle rect = (Rectangle) event.getSource();
 * 		if (rect.getFillColor().equals(&quot;blue&quot;)) {
 * 			rect.setFillColor(&quot;red&quot;);
 * 		} else {
 * 			rect.setFillColor(&quot;blue&quot;);
 * 		}
 * 	}
 * });
 * </pre>
 * 
 * @author Henri Kerola / IT Mill Ltd
 * 
 */
public class DrawingArea extends Widget implements VectorObjectContainer,
		HasClickHandlers, HasAllMouseHandlers {

	private static final SVGImpl impl = GWT.create(SVGImpl.class);

	private final Element root;

	private List<VectorObject> childrens = new ArrayList<VectorObject>();

	/**
	 * Creates a DrawingArea of given width and height.
	 * 
	 * @param width
	 *            the width of DrawingArea in pixels
	 * @param height
	 *            the height of DrawingArea in pixels
	 */
	public DrawingArea(int width, int height) {
		DivElement container = Document.get().createDivElement();
		setElement(container);

		root = getImpl().createDrawingArea(container, width, height);
	}

	protected SVGImpl getImpl() {
		return impl;
	}

	/**
	 * Returns a String that indicates what graphics renderer is used. This
	 * String is "VML" for Internet Explorer and "SVG" for other browsers.
	 * 
	 * @return the used graphics renderer
	 */
	public String getRendererString() {
		return getImpl().getRendererString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.itmill.toolkit.incubator.graphics.client.VectorObjectContainer#add
	 * (com.itmill.toolkit.incubator.graphics.client.VectorObject)
	 */
	public VectorObject add(VectorObject vo) {
		getImpl().add(root, vo.getElement());
		vo.setParent(this);
		childrens.add(vo);
		return vo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.itmill.toolkit.incubator.graphics.client.VectorObjectContainer#pop
	 * (com.itmill.toolkit.incubator.graphics.client.VectorObject)
	 */
	public VectorObject pop(VectorObject vo) {
		if (vo.getParent() != this) {
			return null;
		}
		getImpl().pop(root, vo.getElement());
		return vo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.itmill.toolkit.incubator.graphics.client.VectorObjectContainer#remove
	 * (com.itmill.toolkit.incubator.graphics.client.VectorObject)
	 */
	public VectorObject remove(VectorObject vo) {
		if (vo.getParent() != this) {
			return null;
		}
		vo.setParent(null);
		root.removeChild(vo.getElement());
		childrens.remove(vo);
		return vo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.itmill.toolkit.incubator.graphics.client.VectorObjectContainer#clear
	 * ()
	 */
	public void clear() {
		List<VectorObject> childrensCopy = new ArrayList<VectorObject>();
		childrensCopy.addAll(childrens);
		for (VectorObject vo : childrensCopy) {
			this.remove(vo);
		}
	}

	/**
	 * Returns the width of the DrawingArea in pixels.
	 * 
	 * @return the width of the DrawingArea in pixels.
	 */
	public int getWidth() {
		return getImpl().getWidth(root);
	}

	/**
	 * Sets the width of the DrawingArea in pixels.
	 * 
	 * @param width
	 *            the new width in pixels
	 */
	public void setWidth(int width) {
		getImpl().setWidth(root, width);
	}

	/**
	 * Returns the height of the DrawingArea in pixels.
	 * 
	 * @return the height of the DrawingArea in pixels.
	 */
	public int getHeight() {
		return getImpl().getHeight(root);
	}

	/**
	 * Sets the height of the DrawingArea in pixels.
	 * 
	 * @param height
	 *            the new height
	 */
	public void setHeight(int height) {
		getImpl().setHeight(root, height);
	}

	@Override
	public void setHeight(String height) {
		throw new UnsupportedOperationException(
				"Use the setHeight(int height) method.");
	}

	@Override
	public void setWidth(String width) {
		throw new UnsupportedOperationException(
				"Use the setWidth(int width) method.");
	}

	@Override
	public void setStyleName(String style) {
		getElement().setClassName(
				style + " " + style + "-" + getImpl().getStyleSuffix());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.gwt.event.dom.client.HasClickHandlers#addClickHandler(com.
	 * google.gwt.event.dom.client.ClickHandler)
	 */
	public HandlerRegistration addClickHandler(ClickHandler handler) {
		return addDomHandler(handler, ClickEvent.getType());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.gwt.event.dom.client.HasMouseDownHandlers#addMouseDownHandler
	 * (com.google.gwt.event.dom.client.MouseDownHandler)
	 */
	public HandlerRegistration addMouseDownHandler(MouseDownHandler handler) {
		return addDomHandler(handler, MouseDownEvent.getType());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.gwt.event.dom.client.HasMouseUpHandlers#addMouseUpHandler(
	 * com.google.gwt.event.dom.client.MouseUpHandler)
	 */
	public HandlerRegistration addMouseUpHandler(MouseUpHandler handler) {
		return addDomHandler(handler, MouseUpEvent.getType());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.gwt.event.dom.client.HasMouseOutHandlers#addMouseOutHandler
	 * (com.google.gwt.event.dom.client.MouseOutHandler)
	 */
	public HandlerRegistration addMouseOutHandler(MouseOutHandler handler) {
		return addDomHandler(handler, MouseOutEvent.getType());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.gwt.event.dom.client.HasMouseOverHandlers#addMouseOverHandler
	 * (com.google.gwt.event.dom.client.MouseOverHandler)
	 */
	public HandlerRegistration addMouseOverHandler(MouseOverHandler handler) {
		return addDomHandler(handler, MouseOverEvent.getType());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.gwt.event.dom.client.HasMouseMoveHandlers#addMouseMoveHandler
	 * (com.google.gwt.event.dom.client.MouseMoveHandler)
	 */
	public HandlerRegistration addMouseMoveHandler(MouseMoveHandler handler) {
		return addDomHandler(handler, MouseMoveEvent.getType());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.gwt.event.dom.client.HasMouseWheelHandlers#addMouseWheelHandler
	 * (com.google.gwt.event.dom.client.MouseWheelHandler)
	 */
	public HandlerRegistration addMouseWheelHandler(MouseWheelHandler handler) {
		return addDomHandler(handler, MouseWheelEvent.getType());
	}

	@Override
	protected void doAttachChildren() {
		for (VectorObject vo : childrens) {
			vo.onAttach();
		}
	}

	@Override
	protected void doDetachChildren() {
		for (VectorObject vo : childrens) {
			vo.onDetach();
		}
	}
}
