package com.tomecode.soa.dependency.analyzer.gui.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.zest.layouts.algorithms.DirectedGraphLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.GridLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.HorizontalLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.HorizontalTreeLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.RadialLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.SpringLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.TreeLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.VerticalLayoutAlgorithm;

import com.tomecode.soa.dependency.analyzer.view.VisualGraphView;

/**
 * Action for change graph layout algorithm
 * 
 * 
 * @author Tomas Frastia
 * 
 */
public final class GraphLayoutAction extends Action {

	private LayoutActionType layoutActionType;

	public GraphLayoutAction(LayoutActionType type) {
		this.layoutActionType = type;
		setText(type.toString());
		setChecked(false);
	}

	public final void run() {
		IWorkbenchPage workbenchPage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		VisualGraphView visualGraphView = (VisualGraphView) workbenchPage.findView(VisualGraphView.ID);

		if (layoutActionType == LayoutActionType.SRING_LAYOUT) {
			visualGraphView.changeLayout(new SpringLayoutAlgorithm(1), layoutActionType);
		} else if (layoutActionType == LayoutActionType.TREE_LAYOUT) {
			visualGraphView.changeLayout(new TreeLayoutAlgorithm(1), layoutActionType);
		} else if (layoutActionType == LayoutActionType.VERTICAL_LAYOUT) {
			visualGraphView.changeLayout(new VerticalLayoutAlgorithm(1), layoutActionType);
		} else if (layoutActionType == LayoutActionType.RADIAL_LAYOUT) {
			visualGraphView.changeLayout(new RadialLayoutAlgorithm(1), layoutActionType);
		} else if (layoutActionType == LayoutActionType.HORIZONTAL_LAYOUT) {
			visualGraphView.changeLayout(new HorizontalLayoutAlgorithm(1), layoutActionType);
		} else if (layoutActionType == LayoutActionType.HORIZONTAL_TREE_LAYOUT) {
			visualGraphView.changeLayout(new HorizontalTreeLayoutAlgorithm(1), layoutActionType);
		} else if (layoutActionType == LayoutActionType.GRID_LAYOUT) {
			visualGraphView.changeLayout(new GridLayoutAlgorithm(1), layoutActionType);
		} else if (layoutActionType == LayoutActionType.DIRECTED_LAYOUT) {
			visualGraphView.changeLayout(new DirectedGraphLayoutAlgorithm(1), layoutActionType);
		}
		setChecked(true);
	}

	/**
	 * current {@link LayoutActionType} 
	 * 
	 * @return
	 */
	public final LayoutActionType getType() {
		return layoutActionType;
	}

	/**
	 * Graph layout types
	 * 
	 * @author Tomas Frastia
	 * 
	 */
	public enum LayoutActionType {
		SRING_LAYOUT("Spring Layout"), TREE_LAYOUT("Tree Layout"), VERTICAL_LAYOUT("Vertical Layout"),

		RADIAL_LAYOUT("Radial Layout"),

		HORIZONTAL_TREE_LAYOUT("Horizontal Tree Layout"),

		HORIZONTAL_LAYOUT("Horizontal Layout"),

		GRID_LAYOUT("Grid Layout"), DIRECTED_LAYOUT("Directed Layout");

		private final String title;

		private LayoutActionType(String title) {
			this.title = title;
		}

		public final String toString() {
			return title;
		}
	}

}
