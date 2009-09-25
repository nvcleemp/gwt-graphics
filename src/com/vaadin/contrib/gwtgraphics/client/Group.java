package com.vaadin.contrib.gwtgraphics.client;

import java.util.ArrayList;
import java.util.List;

/**
 * Group is a container, which can contain one or more VectorObjects.
 * 
 * @author Henri Kerola / IT Mill Ltd
 * 
 */
public class Group extends VectorObject implements VectorObjectContainer {

	private List<VectorObject> childrens = new ArrayList<VectorObject>();

	/**
	 * Creates an empty Group.
	 */
	public Group() {
	}

	@Override
	protected Class<? extends VectorObject> getType() {
		return Group.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.itmill.toolkit.incubator.graphics.client.VectorObjectContainer#add
	 * (com.itmill.toolkit.incubator.graphics.client.VectorObject)
	 */
	public VectorObject add(VectorObject vo) {
		childrens.add(vo);
		getImpl().add(getElement(), vo.getElement());
		vo.setParent(this);
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
		getElement().removeChild(vo.getElement());
		childrens.remove(vo);
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
		// getImpl().pop(getElement(), vo.getElement());
		remove(vo);
		add(vo);
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
}
