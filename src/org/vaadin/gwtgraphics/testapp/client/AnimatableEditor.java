package org.vaadin.gwtgraphics.testapp.client;

import org.vaadin.gwtgraphics.client.animation.Animatable;
import org.vaadin.gwtgraphics.client.animation.Animate;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class AnimatableEditor extends VerticalPanel implements ClickHandler {

	protected Metadata metadata;

	private Animatable target;

	private ListBox propertyList;

	private TextBox startValue;

	private TextBox endValue;

	private TextBox durationValue;

	private Button addButton;

	public AnimatableEditor(Metadata metadata) {
		this.metadata = metadata;
		HorizontalPanel panel = new HorizontalPanel();
		panel.setVerticalAlignment(ALIGN_MIDDLE);

		propertyList = new ListBox();
		propertyList.getElement().setId("animation-property-list");
		panel.add(propertyList);
		panel.add(new Label(": "));

		startValue = new TextBox();
		startValue.getElement().setId("animation-start-value");
		startValue.setTitle("Start value");
		startValue.setVisibleLength(3);
		panel.add(startValue);
		panel.add(new Label(" -> "));

		endValue = new TextBox();
		endValue.getElement().setId("animation-end-value");
		endValue.setTitle("End value");
		endValue.setVisibleLength(3);
		panel.add(endValue);
		panel.add(new Label(" in "));

		durationValue = new TextBox();
		durationValue.getElement().setId("animation-duration");
		durationValue.setTitle("Duration");
		durationValue.setVisibleLength(5);
		panel.add(durationValue);
		panel.add(new Label(" ms"));

		addButton = new Button("Add");
		addButton.getElement().setId("animation-add");
		addButton.addClickHandler(this);
		panel.add(addButton);

		add(panel);
	}

	public void setTarget(Animatable target) {
		this.target = target;
		for (Animate animate : metadata.getAnimations(target)) {
			addAnimation(animate);
		}
	}

	public void addProperties(String[] properties) {
		for (String property : properties) {
			propertyList.addItem(property);
		}
	}

	public void onClick(ClickEvent event) {
		if (addButton == event.getSource()) {
			try {
				String property = propertyList.getValue(propertyList
						.getSelectedIndex());
				double start = Double.parseDouble(startValue.getText());
				double end = Double.parseDouble(endValue.getText());
				int duration = Integer.parseInt(durationValue.getText());
				final Animate animate = new Animate(target, property, start,
						end, duration);
				metadata.addAnimation(target, animate);
				addAnimation(animate);

			} catch (NumberFormatException e) {
			}

		}
	}

	private void addAnimation(final Animate animate) {
		HorizontalPanel panel = new HorizontalPanel();
		panel.setVerticalAlignment(ALIGN_MIDDLE);
		panel.add(new Label(animate.getProperty() + ": "
				+ animate.getStartValue() + " -> " + animate.getEndValue()
				+ " in " + animate.getDuration() + " ms"));
		Button startButton = new Button("Start");
		startButton.getElement().setId("animation-start-button");
		panel.add(startButton);
		startButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				animate.start();
			}
		});

		Button stopButton = new Button("Stop");
		stopButton.getElement().setId("animation-stop-button");
		panel.add(stopButton);
		stopButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				animate.stop();
			}
		});
		add(panel);
	}
}
