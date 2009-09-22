package com.vaadin.contrib.gwtgraphics.client.shape;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.contrib.gwtgraphics.client.Shape;
import com.vaadin.contrib.gwtgraphics.client.VectorObject;
import com.vaadin.contrib.gwtgraphics.client.shape.path.ClosePath;
import com.vaadin.contrib.gwtgraphics.client.shape.path.LineTo;
import com.vaadin.contrib.gwtgraphics.client.shape.path.MoveTo;
import com.vaadin.contrib.gwtgraphics.client.shape.path.PathStep;

/**
 * Path represents a path consisting of pen movement commands. Currently,
 * moveTo, lineTo and close commands are supported. The moveTo and lineTo
 * commands support both relative and absolute coordinates.
 * <p>
 * The code below creates a path drawing a 100 x 100 pixels rectangle at the
 * position (50, 50):
 * </p>
 * 
 * <pre>
 * Path path = new Path(50, 50);
 * path.lineRelativelyTo(100, 0);
 * path.lineRelativelyTo(0, 100);
 * path.lineRelativelyTo(-100, 0);
 * path.close();
 * </pre>
 * 
 * This rectangle is modified as a triangle with the following code:
 * 
 * <pre>
 * path.setStep(2, new LineTo(true, -50, 100));
 * path.removeStep(3);
 * </pre>
 * 
 * @author Henri Kerola / IT Mill Ltd
 * 
 */
public class Path extends Shape {

	private final List<PathStep> steps = new ArrayList<PathStep>();

	/**
	 * Creates a new Path and sets its starting point at the given position.
	 * 
	 * @param x
	 *            the x-coordinate position in pixels
	 * @param y
	 *            the y-coordinate position in pixels
	 */
	public Path(int x, int y) {
		moveTo(x, y);
	}

	@Override
	protected Class<? extends VectorObject> getType() {
		return Path.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.itmill.toolkit.incubator.graphics.client.Shape#getX()
	 */
	@Override
	public int getX() {
		return ((MoveTo) steps.get(0)).getX();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.itmill.toolkit.incubator.graphics.client.Shape#setX(int)
	 */
	@Override
	public void setX(int x) {
		steps.set(0, new MoveTo(false, x, getY()));
		drawPath();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.itmill.toolkit.incubator.graphics.client.Shape#getY()
	 */
	@Override
	public int getY() {
		return ((MoveTo) steps.get(0)).getY();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.itmill.toolkit.incubator.graphics.client.Shape#setY(int)
	 */
	@Override
	public void setY(int y) {
		steps.set(0, new MoveTo(false, getX(), y));
		drawPath();
	}

	/**
	 * Sets PathStep at the specified position.
	 * 
	 * @param index
	 *            the index of the PathStep element to set
	 * @param step
	 *            PathStep to be stored at the specified position
	 * @throws IllegalArgumentException
	 */
	public void setStep(int index, PathStep step)
			throws IllegalArgumentException {
		if (index == 0
				&& !(step instanceof MoveTo || ((MoveTo) step)
						.isRelativeCoords())) {
			throw new IllegalArgumentException(
					"The first step must be an absolute MoveTo step.");
		} else {
			steps.set(index, step);
			drawPath();
		}
	}

	/**
	 * Removes the PathStep element at the specified position. Shifts any
	 * subsequent elements to the left.
	 * 
	 * @param index
	 *            the index of the PathStep element to removed
	 */
	public void removeStep(int index) {
		steps.remove(index);
		drawPath();
	}

	/**
	 * Start a new sub-path at the given absolute point.
	 * 
	 * @param x
	 *            an absolute x-coordinate in pixels
	 * @param y
	 *            an absolute y-coordinate in pixels
	 */
	public void moveTo(int x, int y) {
		steps.add(new MoveTo(false, x, y));
		drawPath();
	}

	/**
	 * Start a new sub-path at the given relative point.
	 * 
	 * @param x
	 *            a relative x-coordinate in pixels
	 * @param y
	 *            a relative y-coordinate in pixels
	 */
	public void moveRelativelyTo(int x, int y) {
		steps.add(new MoveTo(true, x, y));
		drawPath();
	}

	/**
	 * Draw a line from the current point to the given absolute point.
	 * 
	 * @param x
	 *            an absolute x-coordinate in pixels
	 * @param y
	 *            an absolute y-coordinate in pixels
	 */
	public void lineTo(int x, int y) {
		steps.add(new LineTo(false, x, y));
		drawPath();
	}

	/**
	 * Draw a line from the current point to the given relative point.
	 * 
	 * @param x
	 *            a relative x-coordinate in pixels
	 * @param y
	 *            a relative y-coordinate in pixels
	 */
	public void lineRelativelyTo(int x, int y) {
		steps.add(new LineTo(true, x, y));
		drawPath();
	}

	/**
	 * Close the path.
	 */
	public void close() {
		steps.add(new ClosePath());
		drawPath();
	}

	private void drawPath() {
		getImpl().drawPath(getElement(), steps);
	}
}
