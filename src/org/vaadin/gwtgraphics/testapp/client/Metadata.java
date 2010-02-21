package org.vaadin.gwtgraphics.testapp.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.vaadin.gwtgraphics.client.Group;
import org.vaadin.gwtgraphics.client.VectorObject;
import org.vaadin.gwtgraphics.client.VectorObjectContainer;
import org.vaadin.gwtgraphics.client.animation.Animatable;
import org.vaadin.gwtgraphics.client.animation.Animate;

import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

public class Metadata {

	private InteractiveTestApplication application;

	private Map<VectorObject, String> nameMap = new HashMap<VectorObject, String>();

	private Map<Animatable, List<Animate>> animationMap = new HashMap<Animatable, List<Animate>>();

	private int ObjectCount = 0;

	public Metadata(InteractiveTestApplication application) {
		this.application = application;
	}

	public CodeView getCodeView() {
		return application.getCodeView();
	}

	public void addVectorObject(VectorObject vo, String name, String params) {
		application.getCodeView().addVectorObject(vo, name, params);
		name += ObjectCount++;
		nameMap.put(vo, name);
	}

	public void removeVectorObject(VectorObject vo) {
		VectorObjectContainer parent = (VectorObjectContainer) vo.getParent();
		if (parent != null) {
			parent.remove(vo);
		}
		nameMap.remove(vo);
		animationMap.remove(vo);
	}

	public String getName(VectorObject vo) {
		return nameMap.get(vo);
	}

	public void updateVectorObjectListBox(ListBox list, VectorObject vo) {
		list.clear();
		Iterator<Entry<VectorObject, String>> it = nameMap.entrySet()
				.iterator();
		while (it.hasNext()) {
			Entry<VectorObject, String> e = it.next();
			list.addItem(e.getValue());
			if (vo != null && vo.equals(e.getKey())) {
				list.setSelectedIndex(list.getItemCount() - 1);
			}
		}
	}

	public VectorObject getVectorObject(int index) {
		Iterator<Entry<VectorObject, String>> it = nameMap.entrySet()
				.iterator();
		int i = 0;
		while (it.hasNext()) {
			Entry<VectorObject, String> e = it.next();
			if (i++ == index) {
				return e.getKey();
			}
		}
		return null;
	}

	public ListBox getParentListBox(Widget parent) {
		ListBox list = new ListBox();
		Iterator<Entry<VectorObject, String>> it = nameMap.entrySet()
				.iterator();
		list.addItem("DrawingArea");
		while (it.hasNext()) {
			Entry<VectorObject, String> e = it.next();
			if (e.getKey() instanceof Group) {
				list.addItem(e.getValue());
				if (parent != null && parent.equals(e.getKey())) {
					list.setSelectedIndex(list.getItemCount() - 1);
				}
			}
		}

		return list;
	}

	public VectorObjectContainer getParent(int index) {
		if (index == 0) {
			return application.getDrawingArea();
		} else {
			Iterator<Entry<VectorObject, String>> it = nameMap.entrySet()
					.iterator();
			int i = 1;
			while (it.hasNext()) {
				Entry<VectorObject, String> e = it.next();
				if (e.getKey() instanceof Group) {
					if (i++ == index) {
						return (VectorObjectContainer) e.getKey();
					}
				}
			}
		}
		return null;
	}

	public void addAnimation(Animatable target, Animate animate) {
		if (!animationMap.containsKey(target)) {
			List<Animate> l = new ArrayList<Animate>();
			l.add(animate);
			animationMap.put(target, l);
		} else {
			animationMap.get(target).add(animate);
		}
	}

	public List<Animate> getAnimations(Animatable target) {
		if (!animationMap.containsKey(target)) {
			return new ArrayList<Animate>();
		}
		return animationMap.get(target);
	}
}
