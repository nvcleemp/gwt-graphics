/*
 * Copyright 2011 Henri Kerola
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.vaadin.gwtgraphics.client.shape;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.gwtgraphics.client.Shape;
import org.vaadin.gwtgraphics.client.VectorObject;
import org.vaadin.gwtgraphics.client.shape.path.Arc;
import org.vaadin.gwtgraphics.client.shape.path.ClosePath;
import org.vaadin.gwtgraphics.client.shape.path.CurveTo;
import org.vaadin.gwtgraphics.client.shape.path.LineTo;
import org.vaadin.gwtgraphics.client.shape.path.MoveTo;
import org.vaadin.gwtgraphics.client.shape.path.PathStep;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;

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
 * @author Henri Kerola
 * 
 */
public class Path extends Shape implements Cloneable  {

	/**
	 * Predefined draw types<br/>
	 * <code>AUTO</code>(default): path is automatically being redrawn on change<br/>
	 * <code>MANUAL</code>: user has to explicitly call <code>issueRedraw(true)</code><br/>
	 * <code>DEFERRED</code>: redraw is deffered
	 */
	public enum RedrawType{
		AUTO, MANUAL, DEFERRED
	}

	/**
	 * Defines which drawing type is used when changing path. Default is AUTO.
	 */
	private RedrawType redrawingType = RedrawType.AUTO;

	/**
	 * Defines if deffered redraw was issued.
	 */
	private boolean deferredDrawPending = false;

	protected final List<PathStep> steps;

	/**
	 * Creates a new Path and sets its starting point at the given position.
	 * 
	 * @param x
	 *            the x-coordinate position in pixels
	 * @param y
	 *            the y-coordinate position in pixels
	 */
	public Path(int x, int y) {
		this(10);
		moveTo(x, y);
	}


	/**
	 * Creates an empty path with initial path step capacity. Useful when cloning the path.
	 * Only to be used by cloning and extended classes when number of steps is known.
	 * 
	 * @param capacity inicial capacity of <code>steps</code> list.
	 */
	protected Path(int capacity){
		steps = new ArrayList<PathStep>(capacity);
	}

	/**
	 * Performs deep copy of path.
	 * 
	 * @return a cloned Path
	 */
	public Path clone(){
		int length = steps.size();
		Path p = new Path(length);
		p.setRedrawingType(RedrawType.MANUAL);
		for (PathStep step : steps) {
			if (step.getClass() == ClosePath.class) {
				p.addStep(new ClosePath());
			} else if (step.getClass() == MoveTo.class) {
				MoveTo _step = (MoveTo)step;
				p.addStep(new MoveTo(_step.isRelativeCoords(), _step.getX(), _step.getY()));
			} else if (step.getClass() == LineTo.class) {
				LineTo _step = (LineTo)step;
				p.addStep(new LineTo(_step.isRelativeCoords(), _step.getX(), _step.getY()));
			} else if (step.getClass() == CurveTo.class) {
				CurveTo _step = (CurveTo)step;
				p.addStep(new CurveTo(_step.isRelativeCoords(), _step.getX1(), _step.getY1(), _step.getX2(), _step.getY2(), _step.getX(), _step.getY()));
			} else if (step.getClass() == Arc.class) {
				Arc _step = (Arc)step;
				p.addStep(new Arc(_step.isRelativeCoords(), _step.getRx(), _step.getRy(), _step.getxAxisRotation(), _step.isLargeArc(), _step.isSweep(), _step.getX(), _step.getY()));
			}
		}
		return p;
	}

	@Override
	protected Class<? extends VectorObject> getType() {
		return Path.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vaadin.gwtgraphics.client.Shape#getX()
	 */
	@Override
	public int getX() {
		return ((MoveTo) steps.get(0)).getX();
	}

	public RedrawType getRedrawingType() {
		return redrawingType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vaadin.gwtgraphics.client.Shape#setX(int)
	 */
	@Override
	public void setX(int x) {
		steps.set(0, new MoveTo(false, x, getY()));
		issueRedraw(false);
	}

	public boolean isDeferredDrawPending() {
		return deferredDrawPending;
	}

	public void setRedrawingType(RedrawType redrawingType) {
		this.redrawingType = redrawingType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vaadin.gwtgraphics.client.Shape#getY()
	 */
	@Override
	public int getY() {
		return ((MoveTo) steps.get(0)).getY();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vaadin.gwtgraphics.client.Shape#setY(int)
	 */
	@Override
	public void setY(int y) {
		steps.set(0, new MoveTo(false, getX(), y));
		issueRedraw(false);
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
			issueRedraw(false);
		}
	}

	/**
	 * Inserts a new Step at the given Position. Shifts the element currently at that position (if any) and any subsequent elements to the right (adds one to their indices).
	 * 
	 * @param index
	 * 				index position where to add new Step 
	 * @param step
	 * 				new Step
	 * @throws IllegalArgumentException
	 */
	public void addStep(int index, PathStep step) throws IllegalArgumentException {
		if (index == 0 && !(step instanceof MoveTo || ((MoveTo) step).isRelativeCoords())) {
			throw new IllegalArgumentException("The first step must be an absolute MoveTo step.");
		} else {
			boolean appended = index == steps.size() - 1;
			steps.add(index, step);

			/* 
			 * If new step is being appended (is last element), it can be redrawn immediately.
			 */
			if ( appended ){
				getElement().getAttribute("d").concat(getImpl().getPathStepString(step));
			}else{
				issueRedraw(false);
			}
		}
	}

	/**
	 * Appends a new Step to the end of the Path.
	 *   
	 * @param step
	 * 				new Step
	 * @throws IllegalArgumentException
	 */
	public void addStep(PathStep step) throws IllegalArgumentException {
		if (steps.size() == 0 && !(step instanceof MoveTo || ((MoveTo) step).isRelativeCoords())) {
			throw new IllegalArgumentException("The first step must be an absolute MoveTo step.");
		} else {
			steps.add(step);
			getElement().getAttribute("d").concat(getImpl().getPathStepString(step));
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
		issueRedraw(false);
	}

	/**
	 * Returns the number of PathSteps in this Path.
	 * 
	 * @return the number of PathSteps in this Path.
	 */
	public int getStepCount() {
		return steps.size();
	}

	/**
	 * Returs current PathStep list
	 * @return list of path steps 
	 */
	public List<PathStep> getSteps(){
		return steps;
	}

	/**
	 * Returns the PathStep element at the specified position.
	 * 
	 * @param index
	 *            index of element to return.
	 * @return the PathStep element at the specified position.
	 */
	public PathStep getStep(int index) {
		return steps.get(index);
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
		addStep(new MoveTo(false, x, y));
		issueRedraw(false);
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
		addStep(new MoveTo(true, x, y));
		issueRedraw(false);
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
		addStep(new LineTo(false, x, y));
		issueRedraw(false);
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
		addStep(new LineTo(true, x, y));
		issueRedraw(false);
	}

	/**
	 * Draws a cubic Bï¿½zier curve.
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param x
	 * @param y
	 */
	public void curveTo(int x1, int y1, int x2, int y2, int x, int y) {
		addStep(new CurveTo(false, x1, y1, x2, y2, x, y));
		issueRedraw(false);
	}

	/**
	 * Draws a cubic BŽzier curve.
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param x
	 * @param y
	 */
	public void curveRelativelyTo(int x1, int y1, int x2, int y2, int x, int y) {
		addStep(new CurveTo(true, x1, y1, x2, y2, x, y));
		issueRedraw(false);
	}

	public void arc(int rx, int ry, int xAxisRotation, boolean largeArc,
			boolean sweep, int x, int y) {
		addStep(new Arc(false, rx, ry, xAxisRotation, largeArc, sweep, x, y));
		issueRedraw(false);
	}

	public void arcRelatively(int rx, int ry, int xAxisRotation,
			boolean largeArc, boolean sweep, int x, int y) {
		addStep(new Arc(true, rx, ry, xAxisRotation, largeArc, sweep, x, y));
		issueRedraw(false);
	}

	/**
	 * Close the path.
	 */
	public void close() {
		addStep(new ClosePath());
		issueRedraw(false);
	}

	private void drawPath() {
		getImpl().drawPath(getElement(), steps);
	}

	private void drawPathDeferred() {
		if (!deferredDrawPending) {
			deferredDrawPending = true;
			Scheduler.get().scheduleFinally(new ScheduledCommand() {
				public void execute() {
					deferredDrawPending = false;
					drawPath();
				}
			});
		}
	}

	/**
	 * Issues new redraw request. If {@link #redrawingType} is set <code>DEFERRED</code>,
	 * a new deferred call is issued instead. Note that, if there
	 * is already deferred request pending, a new one will be ignored.
	 * 
	 * @param redrawIfManual if <code>true</code> path will be redraw even
	 * 							if {@link #redrawingType} is set to <code>MANUAL</code>
	 */
	public void issueRedraw(boolean redrawIfManual){
		if ( redrawingType == RedrawType.DEFERRED ){
			drawPathDeferred();
		}else if ( redrawIfManual || redrawingType == RedrawType.AUTO){
			drawPath();
		}
	}
}
