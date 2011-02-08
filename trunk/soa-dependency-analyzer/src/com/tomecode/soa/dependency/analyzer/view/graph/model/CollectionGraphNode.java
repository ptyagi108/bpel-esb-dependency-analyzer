package com.tomecode.soa.dependency.analyzer.view.graph.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.core.widgets.IContainer;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * Special {@link GraphNode}
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class CollectionGraphNode extends GraphNode {

	/**
	 * list of datas
	 */
	private final List<Object> datas = new ArrayList<Object>();

	private final List<CollectionGraphNode> parentDatas = new ArrayList<CollectionGraphNode>();

	private Class<?> refClass;

	/**
	 * Constructor
	 * 
	 * @param graphModel
	 * @param text
	 * @param image
	 * @param data
	 */
	public CollectionGraphNode(IContainer graphModel, String text, Image image, Object data) {
		super(graphModel, SWT.NONE, text, image);
		datas.add(data);
		refClass = data.getClass();
	}

	public final boolean containsTitleAndType(Object data) {
		return (getText().equals(data.toString()) && refClass.equals(data.getClass()));
	}

	public final void addData(Object data) {
		if (!datas.contains(data)) {
			datas.add(data);
		}
	}

	public final boolean containsTitleAndData(Object data) {
		if (getText().equals(data.toString())) {
			return datas.contains(data);
		}
		return false;
	}

	/**
	 * @return the datas
	 */
	public final List<Object> getDatas() {
		return datas;
	}

	public final void dispose() {
		parentDatas.clear();
		datas.clear();
		super.dispose();
	}

	public final void addParentData(CollectionGraphNode parentNode) {
		if (!parentDatas.contains(parentNode)) {
			parentDatas.add(parentNode);
		}
	}

}
