package com.vaadin.contrib.gwtgraphics.testapp.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

public class Test implements EntryPoint {

	public void onModuleLoad() {
		InteractiveTestApplication test = new InteractiveTestApplication();
		RootPanel.get().add(test);

		// DrawingArea da = new DrawingArea(400, 400);
		// RootPanel.get().add(da);

		// Path p = new Path(100, 100);
		// p.curveTo(100, 90, 150, 50, 150, 60);
		// da.add(p);

		/*
		 * // Path p = new Path(275, 175); // p.lineRelativelyTo(0, -150); //
		 * p.arcRelatively(150, 150, 10, false, false, -150, 150); // p.close();
		 * // da.add(new Circle(200, 200, 100)); //
		 * da.add(DrawUtil.createSectorDeg(200, 200, 100, 0, 90)); // Sector
		 * sector = new Sector(200, 200, 0, 0, 0);
		 * 
		 * // Sector sector = new Sector(200, 200, 100, 0, 90); //
		 * sector.setFillColor("blue"); // da.add(sector);
		 * 
		 * Circle circle = new Circle(200, 200, 100); da.add(circle);
		 * 
		 * double angles[] = { 90, 45, 45, 90, 80, 10 }; String colors[] = {
		 * "blue", "red", "green", "yellow", "blue", "black" };
		 * 
		 * double currentAngle = 360; int delay = 2000; int i = 0; for (final
		 * double angle : angles) { currentAngle -= angle; Sector sector = new
		 * Sector(200, 200, 100, 0, 0); // Sector sector = new Sector(200, 200,
		 * 100, currentAngle, angle); sector.addMouseOverHandler(new
		 * MouseOverHandler() { public void onMouseOver(MouseOverEvent event) {
		 * Sector sector = (Sector) event.getSource(); new Animate(sector,
		 * "radius", 100, 103, 100).start(); } }); sector.addMouseOutHandler(new
		 * MouseOutHandler() { public void onMouseOut(MouseOutEvent event) {
		 * Sector sector = (Sector) event.getSource(); new Animate(sector,
		 * "radius", 103, 100, 100).start(); // ((Sector)
		 * event.getSource()).setRadius(100); } });
		 * sector.setFillColor(colors[i++]); da.add(sector); new Animate(sector,
		 * "startangle", 0, currentAngle, delay).start(); new Animate(sector,
		 * "angle", 0, angle, delay).start();
		 * 
		 * }
		 */
		// new Animate(sector, "startangle", 90, 180, 2000).start();
		// new Animate(sector, "radius", 0, 100, 2000).start();
		// new Animate(sector, "angle", 0, 90, 2000).start();
		// new Animate(sector, "x", 200, 100, 2000).start();
		// <path d="M275,175 v-150 a150,150 0 0,0 -150,150 z"
		// fill="yellow" stroke="blue" stroke-width="5" />

	}
}
