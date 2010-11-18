package com.tomecode.soa.dependency.analyzer.tree.node;

import java.util.ArrayList;
import java.util.List;

import com.tomecode.soa.dependency.analyzer.tree.WorkspacesNavigator;
import com.tomecode.soa.workspace.MultiWorkspace;

/**
 * Root node {@link WorkspacesNavigator}
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/ *
 */
public final class WorkspaceRootNode {

	private final List<Object> objects;

	public WorkspaceRootNode() {
		objects = new ArrayList<Object>();
	}

	public final void add(Object object) {
		objects.add(object);
	}

	public final boolean hasChildren() {
		return !objects.isEmpty();
	}

	public final Object[] getChildren() {
		return objects.toArray();
	}

	/**
	 * refresh node - which contain {@link MultiWorkspace}
	 * 
	 * @param oldMultiWorkspace
	 * @param newMultiWorkspace
	 */
	public final void refreshMultiWorkspaceNode(MultiWorkspace oldMultiWorkspace, MultiWorkspace newMultiWorkspace) {
		if (newMultiWorkspace == null) {
			if (!replaceExistsNode(oldMultiWorkspace)) {
				objects.add(oldMultiWorkspace);
			}

		} else {
			for (int i = 0; i <= objects.size() - 1; i++) {
				if (objects.get(i).equals(oldMultiWorkspace)) {
					objects.set(i, newMultiWorkspace);
				}
			}
		}
	}

	public final void refreshMultiWorkspaceNode(MultiWorkspace multiWorkspace) {
		for (int i = 0; i <= objects.size() - 1; i++) {
			if (objects.get(i).equals(multiWorkspace)) {
				objects.set(i, multiWorkspace);
			}

		}
	}

	/**
	 * replace exists node
	 * 
	 * @param multiWorkspace
	 * @return
	 */
	private final boolean replaceExistsNode(MultiWorkspace multiWorkspace) {
		for (int i = 0; i <= objects.size() - 1; i++) {
			if (objects.get(i).equals(multiWorkspace)) {
				objects.set(i, multiWorkspace);
				return true;
			}
		}
		return false;
	}

	/**
	 * remove selected {@link MultiWorkspace}
	 * 
	 * @param selectedMultiWorkspace
	 */
	public final void remove(Object selectedMultiWorkspace) {
		for (int i = 0; i <= objects.size() - 1; i++) {
			if (objects.get(i).equals(selectedMultiWorkspace)) {
				objects.remove(i);
				break;
			}
		}
	}

}
