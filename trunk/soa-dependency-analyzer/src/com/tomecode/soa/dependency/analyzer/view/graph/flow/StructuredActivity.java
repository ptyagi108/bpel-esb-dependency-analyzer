package com.tomecode.soa.dependency.analyzer.view.graph.flow;

import java.util.ArrayList;
import java.util.List;

/**
 * A Structured activity is an activity whose execution is determined by some
 * internal structure.
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public class StructuredActivity extends Activity {

	private static final long serialVersionUID = 7831409990716998521L;

	private static int count;

	/**
	 * all activities
	 */
	protected final List<Activity> children;

	public StructuredActivity(Object data) {
		super(data);
		this.children = new ArrayList<Activity>();
	}

	public final void addChild(Activity child) {
		addChild(child, -1);
	}

	public final void addChild(Activity child, int index) {
		if (index >= 0) {
			children.add(index, child);
		} else {
			children.add(child);
		}
		fireStructureChange(CHILDREN, child);
	}

	public final List<Activity> getChildren() {
		return children;
	}

	public final String getNewID() {
		return Integer.toString(count++);
	}

	public final void removeChild(FlowElement child) {
		children.remove(child);
		fireStructureChange(CHILDREN, child);
	}

	public final void clear() {
		children.clear();
		super.clear();
	}

}
