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
package org.vaadin.gwtgraphics.client.shape.path;

/**
 * This class represents Path's moveTo step. The pen is lifted and moved to a
 * new location.
 * 
 * @author Henri Kerola
 * 
 */
public class MoveTo extends ClosePath {

	protected boolean relativeCoords;

	protected int x;

	protected int y;

	/**
	 * Instantiates a new MoveTo step with given properties.
	 * 
	 * @param relativeCoords
	 *            true if given coordinates are relative
	 * @param x
	 *            the x-coordinate in pixels
	 * @param y
	 *            the y-coordinate in pixels
	 */
	public MoveTo(boolean relativeCoords, int x, int y) {
		this.relativeCoords = relativeCoords;
		this.x = x;
		this.y = y;
	}

	/**
	 * Returns true if coordinates relative. False means that coordinates are
	 * absolute.
	 * 
	 * @return true of coordinates are relative
	 */
	public boolean isRelativeCoords() {
		return relativeCoords;
	}

	/**
	 * Returns the x-coordinate in pixels.
	 * 
	 * @return the x-coordinate in pixels
	 */
	public int getX() {
		return x;
	}

	/**
	 * Returns the y-coordinate in pixels.
	 * 
	 * @return the y-coordinate in pixels
	 */
	public int getY() {
		return y;
	}

	@Override
	public String getSVGString() {
		return isRelativeCoords() ? "m" : "M" + getX() + " " + getY();
	}

	/**
	 * Sets if path step is relative. <code>True</code> means that coordinates
	 * are relative and <code>False</code> that are relative.
	 * 
	 * @param relativeCoords
	 *            coordinates are relative
	 */
	public void setRelativeCoords(boolean relativeCoords) {
		this.relativeCoords = relativeCoords;
	}

	/**
	 * Sets x component of step coordinate
	 * 
	 * @param x
	 *            x coordinate
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Sets y component of step coordinate
	 * 
	 * @param y
	 *            y coordinate
	 */
	public void setY(int y) {
		this.y = y;
	}

	public void set(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
