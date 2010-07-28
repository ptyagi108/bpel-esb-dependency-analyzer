package com.tomecode.soa.dependency.analyzer.gui.tree.node;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.TreeNode;

/**
 * 
 * Abstract class for all elements in tree
 * 
 * @author Tomas Frastia
 * 
 * @param <E>
 */
public abstract class BasicNode<E extends TreeNode> implements TreeNode, Serializable {

	private static final long serialVersionUID = 7912979253082585806L;

	protected List<E> childs;

	public BasicNode() {
		childs = new ArrayList<E>();
	}

	@Override
	public final Enumeration<?> children() {
		return null;
	}

	@Override
	public final boolean getAllowsChildren() {
		return !childs.isEmpty();
	}

	@Override
	public TreeNode getChildAt(int childIndex) {
		return childs.get(childIndex);
	}

	@Override
	public int getChildCount() {
		return childs.size();
	}

	@Override
	public int getIndex(TreeNode node) {
		return childs.indexOf(node);
	}

	@Override
	public TreeNode getParent() {
		return null;
	}

	@Override
	public boolean isLeaf() {
		return childs.isEmpty();
	}

}
